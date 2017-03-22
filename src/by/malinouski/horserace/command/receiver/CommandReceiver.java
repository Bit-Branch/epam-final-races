package by.malinouski.horserace.command.receiver;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class CommandReceiver {
	static final Logger logger = LogManager.getLogger(CommandReceiver.class);
	protected Map<String, Object> requestMap;
	
	public CommandReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
		logger.debug(requestMap);
	}
	
	public abstract void act();
}
