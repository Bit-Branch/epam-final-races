package by.malinouski.horserace.command.receiver;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;

public abstract class CommandReceiver {
	static final Logger logger = LogManager.getLogger(CommandReceiver.class);
	protected Map<String, Object> requestMap;
	
	public CommandReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
		logger.debug(requestMap);
	}
	
	public abstract Optional<Queue<? extends Future<? extends Entity>>> act();
}
