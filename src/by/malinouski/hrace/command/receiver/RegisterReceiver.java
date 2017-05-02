/**
 * Horserace betting
 * Epam ET Final Project
 * Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.UserDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.exception.HasherException;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.security.Hasher;
import by.malinouski.hrace.security.SaltGenerator;

/**
 * @author makarymalinouski
 *
 */
public class RegisterReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(RegisterReceiver.class);
	
	@Override
	public Entity act(Entity entity) {
		try {
			User user = (User) entity;
			byte[] salt = new SaltGenerator().generate();
			byte[] hash = new Hasher().hash(user.getPassword(), salt);
			user.setSalt(salt);
			user.setHash(hash);
			
			boolean isUserCreated = new UserDao().addUser(user);
			
			if (isUserCreated) {
				return user;
			} else {
				return new Message(BundleConsts.COULDNT_ADD_USER);
			}
		} catch (DaoException | HasherException e) {
			logger.error("Error while registering user " + e);
			return new Message(BundleConsts.PROBLEM_OCCURED);
		} 	
	}
}
