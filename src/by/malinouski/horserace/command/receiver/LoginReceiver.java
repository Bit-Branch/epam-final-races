/**
 * 
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
 */
public class LoginReceiver extends CommandReceiver {
	static final Logger logger = LogManager.getLogger(LoginReceiver.class);
	
	@Override
	public Entity act(Entity entity) {
		
		User user = (User) entity;
		UserDao dao = new UserDao();
		boolean isUserFound;
		try {
			isUserFound = dao.findUser(user);
			
			logger.debug("user found " + isUserFound);
			
			if (isUserFound) {
				return user;
			} else {
				return new Message(BundleConsts.NO_MATCH_FOUND);
			}
		} catch (DaoException e) {
			logger.error("Error while finding user: " + e);
			return new Message(BundleConsts.PROBLEM_OCCURED);
		}
	}

}
