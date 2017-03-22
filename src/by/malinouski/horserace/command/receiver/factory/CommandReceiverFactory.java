package by.malinouski.horserace.command.receiver.factory;

import java.util.Map;

import by.malinouski.horserace.command.receiver.LoginReceiver;
import by.malinouski.horserace.command.receiver.RedirectHomeReceiver;
import by.malinouski.horserace.command.receiver.RegisterReceiver;
import by.malinouski.horserace.command.receiver.ResultsReceiver;
import by.malinouski.horserace.command.receiver.ScheduleReceiver;
import by.malinouski.horserace.command.receiver.CommandReceiver;
import by.malinouski.horserace.constant.RequestMapKeys;

public class CommandReceiverFactory {

	private static final String REGISTER_REQ_TYPE = "register";
	private static final String SCHEDULE_REQ_TYPE = "schedule";
	private static final String LOGIN_REQ_TYPE = "login";
	private static final String RESULTS_REQ_TYPE = "results";
	private Map<String, Object> requestMap;
	
	/**
	 * @param requestMap must be not null
	 */
	public CommandReceiverFactory(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	public CommandReceiver getReceiver() {
		
		String[] req = (String[]) requestMap.get(RequestMapKeys.REQUEST_TYPE);
		if (req != null) {
			switch(req[0]) {
				case REGISTER_REQ_TYPE:
					return new RegisterReceiver(requestMap);
				case SCHEDULE_REQ_TYPE:
					return new ScheduleReceiver(requestMap);
				case LOGIN_REQ_TYPE:
					return new LoginReceiver(requestMap);
				case RESULTS_REQ_TYPE:
					return new ResultsReceiver(requestMap);
				default:
					return new RedirectHomeReceiver(requestMap);
			}
		} else {
			return new RedirectHomeReceiver(requestMap);
		}
	}

}
