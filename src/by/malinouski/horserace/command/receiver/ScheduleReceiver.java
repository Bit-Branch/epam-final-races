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

import by.malinouski.horserace.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class ScheduleReceiver implements CommandReceiver {
	
	Map<String, Object> requestMap;
	
	/**
	 * @param requestMap 
	 */
	public ScheduleReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public void act() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
