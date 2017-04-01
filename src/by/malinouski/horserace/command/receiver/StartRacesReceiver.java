/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesCallable;
import by.malinouski.horserace.logic.racing.RacesRunner;
import by.malinouski.horserace.logic.racing.RacesSchedule;

/**
 * @author makarymalinouski
 *
 */
public class StartRacesReceiver extends CommandReceiver {

	/**
	 * @param requestMap
	 */
	public StartRacesReceiver(Map<String, Object> requestMap) {
		super(requestMap);
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<? extends Entity> act() {
		
		SortedSet<Race> races;
		try {
			races = new RaceDao().selectNextRaces();
			if (!races.isEmpty()) {
				RacesSchedule schedule = RacesSchedule.getInstance();
				races.forEach(race -> schedule.addRace(race));
				RacesRunner runner = RacesRunner.getInstance();
				runner.run(races);
				return Optional.of(races.first());
			}
		} catch (DaoException e) {
			logger.error("Problems with getting races " + e);
		}
		return Optional.empty();
	}

}
