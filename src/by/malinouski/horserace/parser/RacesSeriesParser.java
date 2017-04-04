/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.parser;

import java.time.LocalDateTime;
import java.util.Map;

import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.RacesSeries;
import by.malinouski.horserace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class RacesSeriesParser extends EntityParser {

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.parser.
	 * RequestParamEntityParser#parse(
	 * java.util.Map, by.malinouski.horserace.logic.entity.User)
	 */
	@Override
	public Entity parse(Map<String, String[]> paramMap, User user) {
		String dateTimeStr = paramMap.get(RequestMapKeys.START_DATETIME)[0];
		String numRacesStr = paramMap.get(RequestMapKeys.NUM_OF_RACES)[0];
		String intervalStr = paramMap.get(RequestMapKeys.INTERVAL_BT_RACES)[0];

		LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr); 
		int numRaces = Integer.parseInt(numRacesStr); 
		int interval = Integer.parseInt(intervalStr);
		
		RacesSeries series = new RacesSeries();
		series.setFirstRaceDateTime(dateTime);
		series.setRacesAmount(numRaces);
		series.setInterval(interval);
		
		return series;
	}

}
