package by.malinouski.horserace.command;

import by.malinouski.horserace.command.receiver.CommandReceiver;

public class LoginCommand implements Command {
	
	private CommandReceiver receiver;
	
	public LoginCommand(CommandReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		receiver.act();
	}

}
