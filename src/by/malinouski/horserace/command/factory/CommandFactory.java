package by.malinouski.horserace.command.factory;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.LoginCommand;
import by.malinouski.horserace.command.RegisterCommand;
import by.malinouski.horserace.command.ScheduleCommand;
import by.malinouski.horserace.command.receiver.CommandReceiver;

public class CommandFactory {
	private static final String REGISTER = "RegisterReceiver";
	private static final String LOGIN = "LoginReceiver";
	private static final String SCHEDULE = "ScheduleReceiver";
	
	public Command getRequest(CommandReceiver receiver) {
		switch (receiver.getClass().getSimpleName()) {
			case REGISTER:
				return new RegisterCommand(receiver);
			case LOGIN:
				return new LoginCommand(receiver);
			case SCHEDULE:
				return new ScheduleCommand(receiver);
			default:
				throw new UnsupportedOperationException();
		}
	}

}
