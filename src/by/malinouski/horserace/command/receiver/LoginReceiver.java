/**
 * 
 */
package by.malinouski.horserace.command.receiver;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.UserDAO;

/**
 * @author makarymalinouski
 */
public class LoginReceiver implements CommandReceiver {
	private static final Logger logger = LogManager.getLogger(CommandReceiver.class);
	private static final String NO_MATCH_FOUND = "Passwords do not match. Please try again";
	private Map<String, Object> requestMap;
	
	public LoginReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	/* (non-Javadoc)
	 * @see by.malinouski.xmlparse.command.receiver.RequestReceiver#act()
	 */
	@Override
	public void act() {
		UserDAO dao = new UserDAO();
		String login = ((String[]) requestMap.get(RequestMapKeys.LOGIN))[0];
		String password = ((String[]) requestMap.get(RequestMapKeys.PASSWORD))[0];
		boolean isUserCreated = dao.createUser(login, password);
		logger.debug(isUserCreated);
		requestMap.put(RequestMapKeys.IS_LOGGED_IN, isUserCreated);
		if (isUserCreated) {
			requestMap.put(RequestMapKeys.RESULT, dao.getUser());
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		} else {
			requestMap.put(RequestMapKeys.RESULT, NO_MATCH_FOUND);
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.LOGIN);
		}
	}

}
