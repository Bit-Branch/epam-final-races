/**
 * 
 */
package by.malinouski.horserace.command;

import by.malinouski.horserace.command.receiver.CommandReceiver;

/**
 * @author makarymalinouski
 *
 */
public class RegisterCommand implements Command {
	private CommandReceiver receiver;
	
	public RegisterCommand(CommandReceiver receiver2) {
		this.receiver = receiver2;
	}
	
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.Command#execute()
	 */
	@Override
	public void execute() {
		receiver.act();
	}

}
