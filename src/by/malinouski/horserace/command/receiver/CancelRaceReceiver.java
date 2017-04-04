/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.NoRacesScheduledException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Message;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesResults;

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
				return new Message("Race cancelled (LOCALIZE)");
			}
		} catch (NoRacesScheduledException e) {
			logger.error("There are no races at the specified time " + e);
		} catch (DaoException e) {
			logger.error("Couldn't adjust database for cancellation " + e);
		}
		return new Message("Encountered problems");
	}

}
