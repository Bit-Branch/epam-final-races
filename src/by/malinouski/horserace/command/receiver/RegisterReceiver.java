/**
 * Horserace betting
 * Epam ET Final Project
 * Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.BundleConsts;
import by.malinouski.horserace.dao.UserDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Message;
import by.malinouski.horserace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class RegisterReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(RegisterReceiver.class);
	
	@Override
	public Entity act(Entity entity) {
		User user = (User) entity;
		UserDao dao = new UserDao();
		boolean isUserCreated;
		try {
			isUserCreated = dao.addUser(user);
			if (isUserCreated) {
				return user;
			} else {
				return new Message(BundleConsts.COULDNT_ADD_USER);
			}
		} catch (DaoException e) {
			logger.error("Error while creating user " + e);
			return new Message(BundleConsts.PROBLEM_OCCURED);
		} 	
	}
}
