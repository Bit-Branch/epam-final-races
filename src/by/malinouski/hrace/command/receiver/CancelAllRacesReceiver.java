/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Future;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.RaceDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.exception.NoRacesScheduledException;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.racing.RacesCache;
import by.malinouski.hrace.logic.racing.RacesResults;

/**
 * @author makarymalinouski
 *
 */
public class CancelAllRacesReceiver extends CommandReceiver {
	private static Logger logger = 
			LogManager.getLogger(CancelAllRacesReceiver.class);
	@Override
	public Entity act(Entity entity) {
		RacesCache cache = RacesCache.getInstance();
		RacesResults futures = RacesResults.getInstance();
		
		Collection<Race> races = cache.getUpcomingRaces();
		Iterator<Race> iter = races.iterator();
		
		while (iter.hasNext()) {
			Race race = iter.next();
			try {
				Future<Race> future = futures.getFutureRace(race.getDateTime());
				boolean wasCancelled = future.cancel(true);
				if (!wasCancelled) {
					logger.debug("not cancelled " + race.getDateTime());
					iter.remove();
				}
				logger.debug("cancelled " + race.getDateTime());
			} catch (NoRacesScheduledException e) {
				logger.log(Level.ERROR, 
					"Race %s was not cancelled ", race.getDateTime());
			}
		}
		
		try {
			new RaceDao().cancelRaces(races);
			return new Message(BundleConsts.RACE_CANCELLED);
		} catch (DaoException e) {
			logger.error("Exception while cancelling races: " + e);
		}
		return new Message(BundleConsts.RACE_NOT_CANCELLED);
	}

}
