/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.SortedSet;

import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.generator.RacesGenerator;

/**
 * @author makarymalinouski
 *
 */
public class GenerateRacesReceiver extends CommandReceiver {

	/**
	 * @param requestMap
	 */
	public GenerateRacesReceiver(Map<String, Object> requestMap) {
		super(requestMap);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public void act() {
		LocalDateTime datetime = LocalDateTime.parse( 
				((String[]) requestMap.get(RequestMapKeys.START_DATETIME))[0]); 
		int numRaces = Integer.parseInt( 
				((String[]) requestMap.get(RequestMapKeys.NUM_OF_RACES))[0]); 
		int interval = Integer.parseInt( 
				((String[]) requestMap.get(RequestMapKeys.INTERVAL_BT_RACES))[0]); 
		
		RacesGenerator gen = new RacesGenerator();
		SortedSet<Race> races = gen.generate(datetime, numRaces, interval);

		RaceDao dao = new RaceDao();
		try {
			dao.insertNewRaces(races);
		} catch (DaoException e) {
			logger.error("Exception while inserting new races" + e);
		}
		
		// TODO Auto-generated method stub

	}

}
