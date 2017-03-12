/**
 * 
 */
package by.malinouski.horserace.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.connection.ConnectionPool;
import by.malinouski.horserace.entity.User;
import by.malinouski.horserace.entity.User.Role;

/**
 * @author makarymalinouski
 *
 */
public class UserDAO {
	private static final Logger logger = LogManager.getLogger(UserDAO.class);
	private static final String CHECK_PASS = "SELECT `id`, `password`, `role` FROM `users` WHERE `login`=? and `password`=MD5(?)";
	private ConnectionPool pool;
	private User user;
	
	public UserDAO() {
		pool = ConnectionPool.getConnectionPool();
	}
	
	/**
	 * @return  User created with createUser 
	 * 			or null !!!!!
	 * 			if user wasn't created, i.e
	 * 			createUser wasn't called,
	 * 			or returned false
	 */
	public User getUser() {
		return user;
	}
	
	/** 
	 * Creates user, which has given parameters.
	 * To get the user, call getUser() 
	 * @param login 
	 * @param password
	 * @return  true if succeeded, 
	 * 			false, if user wasn't found
	 */
	public boolean createUser(String login, String password) {
		Connection conn = pool.getConnection();
		logger.debug(conn);
		try (PreparedStatement statement = conn.prepareStatement(CHECK_PASS)) {
			statement.setString(1, login);
			statement.setString(2, password);
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			boolean hasRow = res.next();
			
			logger.debug("hasRow: " + hasRow);
			if (hasRow) {
				long id = res.getLong("id");
				User.Role role = Role.valueOf(res.getString("role").toUpperCase());
				String hash = res.getString("password");
				user = new User(id, role, login, hash);
			}
			return hasRow;
		} catch (SQLException e) { 
			// I suppose it's better to throw runtime here,
			// since it wouldn't be nice to user to say password is incorrect
			logger.error("Could not check password: " + e);
			throw new RuntimeException();
		} finally {
			pool.returnConnection(conn);
		}
	}
	
	
	public boolean compareMD5(String pass, String dbPass) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passHash = md.digest(pass.getBytes());
			
			throw new UnsupportedOperationException("Not implemented yet");
		
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
			return false;
		}
	}
}
