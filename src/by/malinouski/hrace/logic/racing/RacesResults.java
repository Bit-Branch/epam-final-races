/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.racing;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import by.malinouski.hrace.exception.NoRacesScheduledException;
import by.malinouski.hrace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RacesResults {
	
	public static final int MAX_ENTRIES = 100;
	private Lock lock = new ReentrantLock(); 
	private LinkedHashMap<LocalDateTime, Future<Race>> racesMap;
	
	@SuppressWarnings("serial")
	private RacesResults() {
		
		racesMap = new LinkedHashMap<LocalDateTime, Future<Race>>() {
			@Override
			protected boolean removeEldestEntry(
					Map.Entry<LocalDateTime, Future<Race>> eldest) {
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
	
	/**
	 * Gets the future object holding race
	 * at a corresponding date & time
	 * @param dateTime
	 * @return race
	 * @throws NoRacesScheduledException
	 */
	public Future<Race> getFutureRace(LocalDateTime dateTime) 
									throws NoRacesScheduledException {
		lock.lock();
		try {
			Future<Race> race = racesMap.get(dateTime);
			if (race == null) {
				throw new NoRacesScheduledException();
			}
			return race;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Adds a Future<Race> only if it is not in the past 
	 * @param dateTime
	 * @param race
	 */
	public void addFutureRace(LocalDateTime dateTime, Future<Race> race) {
		lock.lock();
		try {
			if (LocalDateTime.now().isBefore(dateTime)) {
				racesMap.put(dateTime, race);
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Checks whether there is a Future race with such LocalDateTime. <br>
	 * Does not check whether it is cancelled
	 * @param dateTime
	 * @return true if there is such race, false if not
	 */
	public boolean isPresent(LocalDateTime dateTime) {
		return racesMap.containsKey(dateTime);
	}
	
	public Optional<Future<Race>> getUpcomingRace() {
		Stream<LocalDateTime> stream = racesMap.keySet().stream();
		Optional<LocalDateTime> optDate = stream.filter(
				dt -> LocalDateTime.now().isBefore(dt))
						.min((dt1, dt2) -> dt1.compareTo(dt2));
		
		if (optDate.isPresent()) {
			return Optional.of(racesMap.get(optDate.get()));
		} else {
			return Optional.empty();
		}
	}
	
}
