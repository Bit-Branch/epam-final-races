/**
 * Horserace betting
 * Epam ET Final Project
 * Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.UserDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.UserNotCreatedException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class RegisterReceiver extends CommandReceiver {
	private static final Object SMTH_WRONG = "Something went wrong. Please, try again.";
	
	public RegisterReceiver(Map<String, Object> requestMap) {
		super(requestMap);
	}

	@Override
	public Optional<? extends Entity> act() {
		UserDao dao = new UserDao();
		String login = ((String[]) requestMap.get(RequestMapKeys.LOGIN))[0];
		String password = ((String[]) requestMap.get(RequestMapKeys.PASSWORD))[0];
		boolean isUserCreated;
		try {
			isUserCreated = dao.addUser(login, password);
			logger.debug("user created: " + isUserCreated);
			requestMap.put(RequestMapKeys.IS_LOGGED_IN, isUserCreated);
			if (isUserCreated) {
				User user = dao.getUser();
				requestMap.put(RequestMapKeys.RESULT, user);
				requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
				return Optional.of(user);
			} else {
				requestMap.put(RequestMapKeys.RESULT, SMTH_WRONG);
				requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.LOGIN);
				return Optional.empty();
			}
		} catch (DaoException e) {
			logger.error("Error while creating user " + e);
			requestMap.put(RequestMapKeys.RESULT, SMTH_WRONG);
			return Optional.empty();
		} catch (UserNotCreatedException e) {
			logger.error("User was not created " + e);
			return Optional.empty();
		}
	}

}
