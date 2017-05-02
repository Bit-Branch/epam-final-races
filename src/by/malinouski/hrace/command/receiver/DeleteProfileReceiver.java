/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.UserDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.security.UserValidator;

/**
 * @author makarymalinouski
 *
 */
public class DeleteProfileReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(DeleteProfileReceiver.class);
	
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		User user = (User) entity;
		try {
			if (new UserValidator().validate(user)) {
				new UserDao().deleteUser(user);
				logger.debug("deleting user");
				return new Message(BundleConsts.USER_DELETED);
			} else {
				logger.debug("not deleting user");
				return new Message(BundleConsts.USER_NOT_DELETED);
			}
		} catch (DaoException e) {
			logger.error("Exception while deleting user " + e.getMessage());
			return new Message(BundleConsts.PROBLEM_OCCURED);
		} 
	}

}
