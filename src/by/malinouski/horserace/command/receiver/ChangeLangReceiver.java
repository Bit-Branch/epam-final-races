/**
 * 
 */
package by.malinouski.horserace.command.receiver;

import java.util.Map;

import by.malinouski.horserace.constant.RequestMapKeys;

/**
 * @author makarymalinouski
 *
 */
public class ChangeLangReceiver implements CommandReceiver {
	private static final Object CHANGE_LANG_REDIRECT_PATH = "/jsp/parse.jsp";
	private Map<String, Object> requestMap;

	public ChangeLangReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	/**
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public void act() {
		String locale = ((String[]) requestMap.get(RequestMapKeys.LOCALE))[0];
		requestMap.put(RequestMapKeys.LOCALE, locale);
		requestMap.put(RequestMapKeys.REDIRECT_PATH, CHANGE_LANG_REDIRECT_PATH);
	}

}
