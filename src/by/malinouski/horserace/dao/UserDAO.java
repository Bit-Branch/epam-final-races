/**
 * 
 */
package by.malinouski.horserace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.EntityConsts;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.UserNotCreatedException;
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.logic.entity.User.Role;

/**
 * @author makarymalinouski
 *
 */
public class UserDao extends Dao {
	private static final Logger logger = LogManager.getLogger(UserDao.class);
	private static final String CHECK_PASS = 
			"SELECT `id`, `password`, `role` FROM `users` WHERE `login`=? and `password`=MD5(?);";
	private static final String INSERT_USER = 
			"INSERT INTO `users`(`login`, `password`) VALUES(?, MD5(?));";
	private static final String LAST_ID = "SELECT LAST_INSERT_ID();";
	private User user;
	
	/**
	 * @return  User created with find/addUser 
	 * @throws UserNotCreatedException 
	 * 			if user wasn't created, i.e
	 * 			findUser wasn't called,
	 * 			or returned false
	 */
	public User getUser() throws UserNotCreatedException {
		if (user == null) {
			throw new UserNotCreatedException(
					"User was not created. To create user find/addUser must return true");
		}
		return user;
	}
	
	/**
	 * Finds user with specified params
	 * @param login
	 * @param password
	 * @return true if found, false if not
	 * @throws DaoException
	 */
	public boolean findUser(String login, String password) throws DaoException {
		Connection conn = pool.getConnection();

		try (PreparedStatement statement = conn.prepareStatement(CHECK_PASS)) {
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet res = statement.executeQuery();
			boolean hasRow = res.next();
			logger.debug("hasRow: " + hasRow);

			if (hasRow) {
				long id = res.getLong(EntityConsts.ID);
				User.Role role = Role.valueOf(res.getString(EntityConsts.ROLE).toUpperCase());
				user = new User(id, role, login);
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
	public boolean addUser(String login, String password) throws DaoException {
		Connection conn = pool.getConnection();

		try (PreparedStatement insertStatement = conn.prepareStatement(INSERT_USER);
				PreparedStatement lastIdStatement = conn.prepareStatement(LAST_ID)) {
			insertStatement.setString(1, login);
			insertStatement.setString(2, password);
			int rowsAffected = insertStatement.executeUpdate();
			logger.debug(rowsAffected);
			boolean succeeded = rowsAffected > 0;
			
			if (succeeded) {
				ResultSet idRes = lastIdStatement.executeQuery();
				if (succeeded = idRes.next()) {
					long id = Long.valueOf(idRes.getString(1));
					User.Role role = Role.USER;
					user = new User(id, role, login);
				}
			}
			return succeeded;
		} catch (SQLException e) { 
			throw new DaoException(e);
		} finally {
			pool.returnConnection(conn);
		}
	}
}
