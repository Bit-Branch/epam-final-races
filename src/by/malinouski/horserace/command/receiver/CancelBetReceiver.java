/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.Map;
import java.util.Optional;

import by.malinouski.horserace.logic.entity.Entity;

/**
 * @author makarymalinouski
 *
 */
public class CancelBetReceiver extends CommandReceiver {

	/**
	 * @param requestMap
	 */
	public CancelBetReceiver(Map<String, Object> requestMap) {
		super(requestMap);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<? extends Entity> act() {
		return null;
	}

}
