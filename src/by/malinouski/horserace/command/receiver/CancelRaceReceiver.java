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
import java.util.Optional;
import java.util.concurrent.Future;

import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.NoRacesScheduledException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesResults;

/**
 * @author makarymalinouski
 *
 */
public class CancelRaceReceiver extends CommandReceiver {

	/**
	 * @param requestMap
	 */
	public CancelRaceReceiver(Map<String, Object> requestMap) {
		super(requestMap);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<? extends Entity> act() {
		String[] dateStr = (String[]) requestMap.get(RequestMapKeys.DATETIME);
		LocalDateTime dateTime = LocalDateTime.parse(dateStr[0]);
		
		RacesResults results = RacesResults.getInstance();
		try {
			Future<Race> futureRace = results.getFutureRace(dateTime);
			boolean isCancelled = futureRace.cancel(true);
			if (isCancelled) {
				new RaceDao().cancelRace(dateTime);
			}
		} catch (NoRacesScheduledException e) {
			logger.error("There are no races at the specified time " + e);
		} catch (DaoException e) {
			logger.error("Couldn't adjust database for cancellation " + e);
		}
		return Optional.empty();
	}

}
