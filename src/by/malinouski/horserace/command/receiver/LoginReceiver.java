/**
 * 
 */
package by.malinouski.horserace.command.receiver;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.UserDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.UserNotCreatedException;

/**
 * @author makarymalinouski
 */
public class LoginReceiver extends CommandReceiver {
	private static final Logger logger = LogManager.getLogger(CommandReceiver.class);
	private static final String NO_MATCH_FOUND = "Passwords do not match. Please try again";
	private static final Object SMTH_WRONG = "Something went wrong. Please, try again.";
	private Map<String, Object> requestMap;
	
	public LoginReceiver(Map<String, Object> requestMap) {
		super(requestMap);
	}

	@Override
	public void act() {
		UserDao dao = new UserDao();
		String login = ((String[]) requestMap.get(RequestMapKeys.LOGIN))[0];
		String password = ((String[]) requestMap.get(RequestMapKeys.PASSWORD))[0];
		boolean isUserCreated;
		try {
			isUserCreated = dao.findUser(login, password);
			logger.debug(isUserCreated);
			requestMap.put(RequestMapKeys.IS_LOGGED_IN, isUserCreated);
			if (isUserCreated) {
				requestMap.put(RequestMapKeys.RESULT, dao.getUser());
				requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
			} else {
				requestMap.put(RequestMapKeys.RESULT, NO_MATCH_FOUND);
				requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.LOGIN);
			}
		} catch (DaoException e) {
			logger.error("Error while finding user: " + e);
			requestMap.put(RequestMapKeys.RESULT, SMTH_WRONG);
		} catch (UserNotCreatedException e) {
			logger.error("User was not created: " + e);
		}
	}

}
