/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.BundleConsts;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Message;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesRunner;
import by.malinouski.horserace.logic.racing.RacesCache;

/**
 * @author makarymalinouski
 *
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
				runner.run(races);
				message.setText(BundleConsts.RACES_STARTED);
			}
		} catch (DaoException e) {
			logger.error("Problems with getting races " + e);
		}
		
		return message;
	}

}
