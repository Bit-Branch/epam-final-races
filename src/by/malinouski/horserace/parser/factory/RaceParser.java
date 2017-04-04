/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.parser.factory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.parser.EntityParser;

/**
 * @author makarymalinouski
 *
 */
public class RaceParser extends EntityParser {

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.parser.RequestParamEntityParser#parse(java.util.Map, by.malinouski.horserace.logic.entity.User)
	 */
	@Override
	public Entity parse(Map<String, String[]> paramMap, User user) {
		String dateStr = paramMap.get(RequestMapKeys.DATETIME)[0];
		LocalDateTime dateTime = LocalDateTime.parse(dateStr);
		
		Race race = new Race(dateTime, Collections.emptyList());
		return race;
		
	}

}
