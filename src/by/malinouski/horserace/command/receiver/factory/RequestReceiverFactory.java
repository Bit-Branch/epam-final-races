package by.malinouski.horserace.command.receiver.factory;

import java.util.Map;

import by.malinouski.horserace.command.receiver.ChangeLangReceiver;
import by.malinouski.horserace.command.receiver.LoginReceiver;
import by.malinouski.horserace.command.receiver.ParseRequestReceiver;
import by.malinouski.horserace.command.receiver.CommandReceiver;
import by.malinouski.horserace.constant.RequestMapKeys;

public class RequestReceiverFactory {

	private static final String PARSE_REQ_TYPE = "parse";
	private static final String CHANGE_LANG_REQ_TYPE = "changeLang";
	private static final String LOGIN_REQ_TYPE = "login";
	private Map<String, Object> requestMap;
	
	/**
	 * @param requestMap not null
	 */
	public RequestReceiverFactory(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	public CommandReceiver getReceiver() {
		switch(((String[]) requestMap.get(RequestMapKeys.REQUEST_TYPE))[0]) {
			case PARSE_REQ_TYPE:
				return new ParseRequestReceiver(requestMap);
			case CHANGE_LANG_REQ_TYPE:
				return new ChangeLangReceiver(requestMap);
			case LOGIN_REQ_TYPE:
				return new LoginReceiver(requestMap);
			default:
				throw new UnsupportedOperationException();
		}
	}

}
