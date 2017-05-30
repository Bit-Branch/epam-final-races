/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.RaceDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.racing.RacesCache;
import by.malinouski.hrace.logic.racing.RacesRunner;

/**
 * The Class StartRacesReceiver.
 *
 * @author makarymalinouski
 */
public class StartRacesReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(StartRacesReceiver.class);
	
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		
		SortedSet<Race> races;
		Message message = new Message(BundleConsts.RACES_NOT_STARTED);
		try {
			races = new RaceDao().selectNextRaces();
			if (!races.isEmpty()) {
				RacesCache schedule = RacesCache.getInstance();
				races.forEach(race -> schedule.addRace(race));
				RacesRunner runner = RacesRunner.getInstance();
				runner.startRaces(races);
				message.setText(BundleConsts.RACES_STARTED);
			}
			logger.debug("races empty");
		} catch (DaoException e) {
			logger.error("Problems with getting races " + e);
		}
		
		return message;
	}

}
