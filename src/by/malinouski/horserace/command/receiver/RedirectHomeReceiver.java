/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.Map;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;

/**
 * @author makarymalinouski
 *
 */
public class RedirectHomeReceiver implements CommandReceiver {

	private Map<String, Object> requestMap;
	/**
	 * @param requestMap 
	 */
	public RedirectHomeReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public void act() {
		requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
	}

}
