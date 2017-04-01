/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.racing;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RacesRunner {
	private static final Logger logger = LogManager.getLogger(RacesRunner.class);
	private Lock lock = new ReentrantLock();
	
	private RacesRunner() {
	}
	
	private static class InstanceHolder {
		private static final RacesRunner instance = new RacesRunner();
	}
	
	public static RacesRunner getInstance() {
		return InstanceHolder.instance;
	}
	
	public void run(SortedSet<Race> races) {
		lock.lock();
		try {
			RacesResults results = RacesResults.getInstance();
			ExecutorService service = Executors.newSingleThreadExecutor();
			Iterator<Race> iter = races.iterator();
			while (iter.hasNext()) {
				Race race = iter.next();
				results.addFutureRace(race.getDateTime(), 
								service.submit(new RacesCallable(race)));
			}
		} finally {
			lock.unlock();
		}
	}

}
