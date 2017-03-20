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
public class CommandAll implements Command {
	private CommandReceiver receiver;
	
	public CommandAll(CommandReceiver receiver) {
		this.receiver = receiver;
	}
	
	public void execute() {
		receiver.act();
	}
}
