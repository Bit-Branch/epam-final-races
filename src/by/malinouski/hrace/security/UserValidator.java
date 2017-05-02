/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.dao.UserDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.exception.HasherException;
import by.malinouski.hrace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class UserValidator {
	private static Logger logger = LogManager.getLogger(UserValidator.class);

	/**
	 * Checks whether user is valid,
	 * and if yes, sets its id and balance
	 * @param userToValidate
	 * @return true if valid, else false
	 */
	public boolean validate(User user) {
		UserDao dao = new UserDao();
		try {
			if (dao.findUser(user)) {
				logger.debug("user found");
				Hasher hasher = new Hasher();
				String pass = user.getPassword();
				byte[] hash = hasher.hash(pass, user.getSalt());
				
				if (compareHashes(user.getHash(), hash)) {
					user.setPassword(null);
					logger.info("User is valid");
					return true;
				}
			}
			
		} catch (DaoException e) {
			logger.error("Error while finding user: " + e);
		} catch (HasherException e) {
			logger.error("Couldn't validate: " + e);
		}
		logger.info("User is not valid");
		return false;
	}

	private boolean compareHashes(byte[] hash1, byte[] hash2) {
		boolean isEqual = hash1.length == hash2.length;
		for (int i = 0; i < hash1.length && i < hash2.length; i++) {
			isEqual &= hash1[i] == hash2[i];  
		}
		return isEqual;
	}

}
