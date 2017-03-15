package by.malinouski.horserace.command.receiver.initiator;

import java.util.Map;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.factory.CommandFactory;
import by.malinouski.horserace.command.receiver.CommandReceiver;
import by.malinouski.horserace.command.receiver.factory.CommandReceiverFactory;

/*
 * This class plays the role of client in command pattern
 * to initiate Receiver and Request (command)
 */
public class CommandInitiator {
	Map<String, Object> requestMap;
	public CommandInitiator(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	public Command init() {
		CommandReceiverFactory fact = new CommandReceiverFactory(requestMap);
		CommandReceiver receiver = fact.getReceiver();
		CommandFactory reqFact = new CommandFactory();
		Command req = reqFact.getRequest(receiver);
		return req;
		
	}

}
