/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.dao.UserDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Message;
import by.malinouski.horserace.logic.entity.User;

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
		try {
			User user = (User) entity;
			UserDao dao = new UserDao();
			if (dao.findUser(user)) {
				dao.deleteUser(user);
				logger.debug("deleting user");
				return new Message(
						"User deleted. You will not be able to login again. (need to localize)");
			} else {
				logger.debug("not deleting user");
				return new Message("Coulnd't delete (need to localize this)");
			}
		} catch (DaoException e) {
			logger.error("Exception while deleting user " + e.getMessage());
			return new Message("Problem occured (need to localize this)");
		} 
	}

}
