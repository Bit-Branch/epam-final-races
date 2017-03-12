package by.malinouski.horserace.command.factory;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.ChangeLangCommand;
import by.malinouski.horserace.command.LoginCommand;
import by.malinouski.horserace.command.ParseCommand;
import by.malinouski.horserace.command.receiver.CommandReceiver;

public class RequestFactory {
	private static final String CHANGE_LANG = "ChangeLangReceiver";
	private static final String PARSE = "ParseReceiver";
	private static final String LOGIN = "LoginReceiver";
	
	public Command getRequest(CommandReceiver receiver) {
		switch (receiver.getClass().getSimpleName()) {
			case CHANGE_LANG:
				return new ChangeLangCommand(receiver);
			case PARSE:
				return new ParseCommand(receiver);
			case LOGIN:
				return new LoginCommand(receiver);
			default:
				throw new UnsupportedOperationException();
		}
	}

}
