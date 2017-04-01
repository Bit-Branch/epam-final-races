/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.racing;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RacesResults {

	public static final int MAX_ENTRIES = 100;
	public static final long MAX_DAYS_OLD = 7;
	private Lock lock = new ReentrantLock(); 
	private LinkedHashMap<LocalDateTime, Future<Race>> racesMap;
	
	@SuppressWarnings("serial")
	private RacesResults() {
		racesMap = new LinkedHashMap<LocalDateTime, Future<Race>>() {
			@Override
			protected boolean removeEldestEntry(Map.Entry<LocalDateTime, Future<Race>> eldest) {
				return size() > MAX_ENTRIES;
			}
		};
	}
	
	private static class InstanceHolder {
		private static final RacesResults instance = new RacesResults();
	}

	public static RacesResults getInstance() {
		return InstanceHolder.instance;
	}
	
	public Future<Race> getFutureRace(LocalDateTime dateTime) {
		lock.lock();
		try {
			return racesMap.get(dateTime);
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Adds a Future<Race> only if dateTime is not MAX_DAYS_OLD 
	 * @param dateTime
	 * @param race
	 */
	public void addFutureRace(LocalDateTime dateTime, Future<Race> race) {
		lock.lock();
		try {
			if (dateTime.isAfter(LocalDateTime.now().minusDays(MAX_DAYS_OLD))) {
				racesMap.put(dateTime, race);
			}
		} finally {
			lock.unlock();
		}
	}
	
}
