package by.malinouski.horserace.command.receiver;

import java.util.Map;

public abstract class CommandReceiver {
	protected Map<String, Object> requestMap;
	
	public CommandReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
	public abstract void act();
}
