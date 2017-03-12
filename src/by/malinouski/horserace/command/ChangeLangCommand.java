package by.malinouski.horserace.command;

import by.malinouski.horserace.command.receiver.CommandReceiver;

public class ChangeLangCommand implements Command {
	
	private CommandReceiver receiver;
	
	public ChangeLangCommand(CommandReceiver receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {
		receiver.act();
	}

}
