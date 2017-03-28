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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.SortedSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.generator.RacesGenerator;
import by.malinouski.horserace.logic.racing.RacingCallable;

/**
 * @author makarymalinouski
 *
 */
public class StartRacesReceiver extends CommandReceiver {

	public StartRacesReceiver(Map<String, Object> requestMap) {
		super(requestMap);
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<Queue<? extends Future<? extends Entity>>> act() {
		try {
			RaceDao dao = new RaceDao(); 
			Race race = dao.prepareNextRaceOnly();

			int numRaces = Integer.parseInt(
					((String[]) requestMap.get(RequestMapKeys.NUM_OF_RACES))[0]);
			
			ExecutorService service = Executors.newSingleThreadExecutor();
			Queue<Future<Race>> futureResults = new ArrayBlockingQueue<>(numRaces);
			
			for (int i = 0; i < numRaces; i++) {
				futureResults.add(service.submit(new RacingCallable(race)));
			}
			
			requestMap.put(RequestMapKeys.RESULT, futureResults);
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
			
				new Thread(() -> {
					try {
						dao.updateResults(race);
					} catch (DaoException e) {
						logger.error("Exception while updating results " + e);
					}
				}).start(); 
				return Optional.of(futureResults);
		} catch (DaoException e) {
			logger.error("Exception while preparing races " + e);
			return Optional.empty();
		}
	}

}