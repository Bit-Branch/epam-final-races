package by.malinouski.horserace.command.receiver.initiator;

import java.util.Map;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.factory.RequestFactory;
import by.malinouski.horserace.command.receiver.CommandReceiver;
import by.malinouski.horserace.command.receiver.factory.RequestReceiverFactory;

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
		RequestReceiverFactory fact = new RequestReceiverFactory(requestMap);
		CommandReceiver receiver = fact.getReceiver();
		RequestFactory reqFact = new RequestFactory();
		Command req = reqFact.getRequest(receiver);
		return req;
		
	}

}
