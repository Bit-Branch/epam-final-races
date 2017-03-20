/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command;

import by.malinouski.horserace.command.receiver.CommandReceiver;

/**
 * @author makarymalinouski
 *
 */
public class RedirectHomeCommand implements Command {

	private CommandReceiver receiver;

	/**
	 * 
	 */
	public RedirectHomeCommand(CommandReceiver receiver) {
		this.receiver = receiver;
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.Command#execute()
	 */
	@Override
	public void execute() {
		receiver.act();
	}

}
