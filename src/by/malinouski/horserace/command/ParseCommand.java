package by.malinouski.horserace.command;

import by.malinouski.horserace.command.receiver.CommandReceiver;

public class ParseCommand implements Command {

	private CommandReceiver receiver;
	
	public ParseCommand(CommandReceiver receiver) {
		this.receiver = receiver;
	}
	@Override
	public void execute() {
		receiver.act();
	}

}
