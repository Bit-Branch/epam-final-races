/**
 * Horserace betting
 * Epam ET Final Project
 * Makary Malinouski
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
public class RegisterReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(RegisterReceiver.class);
	private static final String SMTH_WRONG = "Something went wrong. Please, try again.";
	private static final String COULDNT_ADD_USER = "Could not add new user (need to localize!)";
	
	@Override
	public Entity act(Entity entity) {
//		Map<String, Entity> entityMap = new ConcurrentHashMap<>();
		User user = (User) entity;
		UserDao dao = new UserDao();
		boolean isUserCreated;
		try {
			isUserCreated = dao.addUser(user);
			if (isUserCreated) {
//				entityMap.put(RequestMapKeys.USER, user);
				return user;
			} else {
				return new Message(COULDNT_ADD_USER);
//				entityMap.put(RequestMapKeys.MESSAGE, message);
			}
		} catch (DaoException e) {
			logger.error("Error while creating user " + e);
			return new Message("Mistake occured (localize!!)");
		} 	
//		return entityMap;
	}
}
