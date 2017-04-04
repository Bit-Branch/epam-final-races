/**
 * 
 */
package by.malinouski.horserace.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.logic.entity.User.Role;

/**
 * @author makarymalinouski
 *
 */
public class UserDao extends Dao {
	private static final Logger logger = LogManager.getLogger(UserDao.class);
	private static final String CHECK_PASS = 
			"SELECT id, role, balance, deleted FROM users "
			+ "WHERE login = ? and password = MD5(?)";
	private static final String INSERT_USER = 
			"INSERT INTO users(login, password, balance) VALUES(?, MD5(?), ?)";
	private static final String LAST_ID = "SELECT LAST_INSERT_ID()";
	private static final String DELETE_USER = 
			"UPDATE users SET deleted = ? WHERE id = ?";
	private static final String INCR_USER_BALANCE = 
			"UPDATE users SET balance = balance + ? WHERE id = ?";
	private static final String DECR_USER_BALANCE = 
			"UPDATE users SET balance = balance - ? WHERE id = ?";
	private static final String ID_COL = "id";
	private static final String ROLE_COL = "role";
	private static final String BALANCE_COL = "balance";
	private static final String DELETED_COL = "deleted";
	
	
	/**
	 * Finds user with specified params
	 * @param login
	 * @param password
	 * @return true if found, false if not
	 * @throws DaoException
	 */
	public boolean findUser(User user) throws DaoException {
		Connection conn = pool.getConnection();

		try (PreparedStatement statement = conn.prepareStatement(CHECK_PASS)) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			ResultSet res = statement.executeQuery();
			boolean hasRow = res.next();
			logger.debug("hasRow: " + hasRow);

			if (hasRow) {
				boolean isDeleted = res.getBoolean(DELETED_COL);
				if (isDeleted) {
					return false;
				}
				
				long id = res.getLong(ID_COL);
				String roleStr = res.getString(ROLE_COL);
				BigDecimal balance = res.getBigDecimal(BALANCE_COL);
				User.Role role = Role.valueOf(roleStr.toUpperCase());
				
				user.setUserId(id);
				user.setRole(role);
				user.setBalance(balance);
			}
			return hasRow;
		} catch (SQLException e) { 
			throw new DaoException(e);
		} finally {
			pool.returnConnection(conn);
		}
	}
	
	/**
	 * adds new user with specified params
	 * @param login
	 * @param password
	 * @return true if succeeded, else false
	 * @throws DaoException
	 */
	public boolean addUser(User user) throws DaoException {
		Connection conn = pool.getConnection();
		BigDecimal balance = BigDecimal.valueOf(NumericConsts.USER_INIT_BALANCE);

		try (PreparedStatement insertStatement = conn.prepareStatement(INSERT_USER);
				PreparedStatement lastIdStatement = conn.prepareStatement(LAST_ID)) {
			insertStatement.setString(1, user.getLogin());
			insertStatement.setString(2, user.getPassword());
			insertStatement.setBigDecimal(3, balance);

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
		} finally {
			pool.returnConnection(conn);
		}
	}

	public void deleteUser(User user) throws DaoException {
		Connection conn = pool.getConnection();

		try (PreparedStatement deleteUser = conn.prepareStatement(DELETE_USER)) {
			deleteUser.setBoolean(1, true);
			deleteUser.setLong(2, user.getUserId());
			deleteUser.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("UserDao: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
	}

	public void updateBalance(Bet bet) throws DaoException {
		Connection conn = pool.getConnection();
		BigDecimal updateAmount = bet.getWinning().subtract(bet.getAmount());
		try (PreparedStatement updateBalance = 
						conn.prepareStatement(INCR_USER_BALANCE)) {
			updateBalance.setBigDecimal(1, updateAmount);
			updateBalance.setLong(2, bet.getUser().getUserId());
			updateBalance.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Exception updating balance" 
													+ e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
		
	}
}
