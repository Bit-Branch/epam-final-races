/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.racing;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.malinouski.hrace.logic.entity.Race;

/**
 * The Class RacesRunner.
 *
 * @author makarymalinouski
 */
public class RacesRunner {
	private Lock lock = new ReentrantLock();
	
	private RacesRunner() {
	}
	
	private static class InstanceHolder {
		private static final RacesRunner instance = new RacesRunner();
	}
	
	public static RacesRunner getInstance() {
		return InstanceHolder.instance;
	}
	
	/**
	 * Starts RacesCallables for each race and 
	 * adds them to the RacesResults
	 * @param races
	 */
	public void startRaces(SortedSet<Race> races) {
		lock.lock();
		RacesResults res = RacesResults.getInstance();
		try {
			RacesResults results = RacesResults.getInstance();
			ExecutorService service = Executors.newSingleThreadExecutor();
			Iterator<Race> iter = races.iterator();
			while (iter.hasNext()) {
				Race race = iter.next();
				if (!res.isPresent(race.getDateTime())) {
					results.addFutureRace(race.getDateTime(), 
								service.submit(new RacesCallable(race)));
				}
			}
		} finally {
			lock.unlock();
		}
	}

}
