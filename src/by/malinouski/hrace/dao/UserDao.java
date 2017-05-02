/**
 * 
 */
package by.malinouski.hrace.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.connection.ConnectionPool;
import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.logic.entity.Bet;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.logic.entity.User.Role;

/**
 * @author makarymalinouski
 *
 */
public class UserDao {
	private static Logger logger = LogManager.getLogger(UserDao.class);
	private static final String CHECK_PASS = 
			"SELECT id, role, UNHEX(hash) as hash, UNHEX(salt) as salt, balance, deleted "
			+ "FROM users WHERE login = ? ";
	private static final String INSERT_USER = 
			"INSERT INTO users(login, hash, salt, balance) VALUES(?, HEX(?), HEX(?), ?)";
	private static final String LAST_ID = "SELECT LAST_INSERT_ID()";
	private static final String DELETE_USER = 
			"UPDATE users SET deleted = ? WHERE id = ?";
	private static final String UPDATE_CALL = 
			"CALL updateBalance (?, ?, ?)"; // amount, users_id, new_balance
	private static final String INCR_USER_BALANCE = 
			"UPDATE users SET balance = balance + ? WHERE id = ?";
	private static final String DECR_USER_BALANCE = 
			"UPDATE users SET balance = balance - ? WHERE id = ?";
	private static final String UPDATE_HASH = 
			"UPDATE users SET hash = hex(?), salt = hex(?) WHERE id = ?";
	private static final String ID_COL = "id";
	private static final String ROLE_COL = "role";
	private static final String BALANCE_COL = "balance";
	private static final String DELETED_COL = "deleted";
	private static final String SALT_COL = "salt";
	private static final String HASH_COL = "hash";
	
	
	/**
	 * Finds user without checking the password correctness,
	 * and sets the empty fields with info from db
	 * @param user - user to be found
	 * @return true user found, or false if not found
	 * @throws DaoException
	 */
	public boolean findUser(User user) throws DaoException {

		try (Connection conn = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = conn.prepareStatement(CHECK_PASS)) {
			
			statement.setString(1, user.getLogin());
			
			ResultSet res = statement.executeQuery();
			boolean hasRow = res.next();
			logger.debug("hasRow: " + hasRow);

			if (hasRow && !res.getBoolean(DELETED_COL)) {
				
				long id = res.getLong(ID_COL);
				String roleStr = res.getString(ROLE_COL);
				byte[] hash = res.getBytes(HASH_COL);
				byte[] salt = res.getBytes(SALT_COL);
				BigDecimal balance = res.getBigDecimal(BALANCE_COL);
				
				User.Role role = Role.valueOf(roleStr.toUpperCase());
				
				user.setUserId(id);
				user.setRole(role);
				user.setHash(hash);
				user.setSalt(salt);
				user.setBalance(balance);
				return true;
			}
		} catch (SQLException e) { 
			throw new DaoException(e.getMessage());
		}
		return false;
	}
	
	/**
	 * adds new user with specified params
	 * @param login
	 * @param password
	 * @return true if succeeded, else false
	 * @throws DaoException
	 */
	public boolean addUser(User user) throws DaoException {
		BigDecimal balance = BigDecimal.valueOf(NumericConsts.USER_INIT_BALANCE);

		try (Connection conn = ConnectionPool.getInstance().getConnection();
				PreparedStatement insertStatement = conn.prepareStatement(INSERT_USER);
				PreparedStatement lastIdStatement = conn.prepareStatement(LAST_ID)) {
			insertStatement.setString(1, user.getLogin());
			insertStatement.setBytes(2, user.getHash());
			insertStatement.setBytes(3, user.getSalt());
			insertStatement.setBigDecimal(4, balance);

			int rowsAffected = insertStatement.executeUpdate();
			logger.debug(rowsAffected);
			boolean succeeded = rowsAffected > 0;
			
			if (succeeded) {
				ResultSet idRes = lastIdStatement.executeQuery();
				if (succeeded = idRes.next()) {
					long id = Long.valueOf(idRes.getString(1));
					
					user.setRole(Role.USER);
					user.setUserId(id);
					user.setBalance(balance);
				}
			}
			return succeeded;
		} catch (SQLException e) { 
			throw new DaoException(e);
		}
	}

	public void deleteUser(User user) throws DaoException {

		try (Connection conn = ConnectionPool.getInstance().getConnection();
				PreparedStatement deleteUser = conn.prepareStatement(DELETE_USER)) {
			deleteUser.setBoolean(1, true);
			deleteUser.setLong(2, user.getUserId());
			deleteUser.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("UserDao: " + e.getMessage());
		}
	}

	public BigDecimal updateBalance(Bet bet) throws DaoException {
		BigDecimal updateAmount = bet.getWinning().subtract(bet.getAmount());

		try (Connection conn = ConnectionPool.getInstance().getConnection();
			 CallableStatement updateBalance = conn.prepareCall(UPDATE_CALL)) {
			
			updateBalance.setBigDecimal(1, updateAmount);
			updateBalance.setLong(2, bet.getUser().getUserId());
			updateBalance.registerOutParameter(3, java.sql.Types.DECIMAL);
			updateBalance.execute();
			return updateBalance.getBigDecimal(3);
		} catch (SQLException e) {
			throw new DaoException("Exception updating balance" + e.getMessage());
		}
	}

	public void updatePassword(User user) throws DaoException {
		try (Connection conn = ConnectionPool.getInstance().getConnection();
			 PreparedStatement updateHash = conn.prepareStatement(UPDATE_HASH)) {
			updateHash.setBytes(1, user.getHash());
			updateHash.setBytes(2, user.getSalt());
			updateHash.setLong(3, user.getUserId());
			updateHash.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("UserDao: " + e.getMessage());
		}
		
	}
}
