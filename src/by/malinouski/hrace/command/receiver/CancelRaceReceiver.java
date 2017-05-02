/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.util.concurrent.Future;

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
public class CancelRaceReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(CancelRaceReceiver.class);
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		Race race = (Race) entity;
		RacesResults results = RacesResults.getInstance();
		try {
			Future<Race> futureRace = results.getFutureRace(race.getDateTime());
			boolean isCancelled = futureRace.cancel(true);
			if (isCancelled) {
				new RaceDao().cancelRace(race);
				RacesCache.getInstance().removeRace(race);
				logger.info("Cancelled race");
				return new Message(BundleConsts.RACE_CANCELLED);
			}
		} catch (NoRacesScheduledException e) {
			logger.error("There are no races at the specified time " + e);
		} catch (DaoException e) {
			logger.error("Couldn't adjust database for cancellation " + e);
		}
		return new Message(BundleConsts.PROBLEM_OCCURED);
	}

}
