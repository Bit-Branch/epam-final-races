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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.exception.NoRacesScheduledException;
import by.malinouski.hrace.logic.entity.Race;

// TODO: Auto-generated Javadoc
/**
 * @author makarymalinouski
 *
 */
public class RacesCache {
	private Lock lock = new ReentrantLock(); 
	private LinkedHashMap<LocalDateTime, Race> racesSchedule;
	private boolean updated;
	
	@SuppressWarnings("serial")
	private RacesCache() {
		racesSchedule = new LinkedHashMap<LocalDateTime, Race>(
								NumericConsts.MAX_RACES) {
			@Override
			protected boolean removeEldestEntry(
					Map.Entry<LocalDateTime, Race> eldest) {
				return size() > NumericConsts.MAX_RACES;
			}
		};
	}
	
	private static class InstanceHolder {
		private static final RacesCache instance = new RacesCache();
	}
	
	public static RacesCache getInstance() {
		return RacesCache.InstanceHolder.instance;
	}
	
	/**
	 * Adds the race.
	 *
	 * @param race the race
	 */
	public void addRace(Race race) {
		lock.lock();
		try {
			racesSchedule.put(race.getDateTime(), race);
		} finally {
			lock.unlock();
		}
		
	}
	
	/**
	 * Gets the race.
	 *
	 * @param dateTime the date time
	 * @return the race
	 * @throws NoRacesScheduledException the no races scheduled exception
	 */
	public Race getRace(LocalDateTime dateTime) 
							throws NoRacesScheduledException {
		lock.lock();
		try {
			Race race = racesSchedule.get(dateTime);
			if (race == null) {
				throw new NoRacesScheduledException();
			}
			return race;
		} finally {
			lock.unlock();
		}
	}
	

	/**
	 * Removes the race.
	 *
	 * @param race the race
	 */
	public void removeRace(Race race) {
		racesSchedule.remove(race.getDateTime());
	}

	public Collection<Race> getAllRaces() {
		lock.lock();
		try {
			return racesSchedule.values();
		} finally {
			lock.unlock();
		}
	}
	
	public Collection<Race> getUpcomingRaces() {
		lock.lock();
		try {
			Collection<Race> races = new ArrayList<>();
			/* this is very inefficient because of filter,
			 * has to go through each entry,
			 * should think of using NavigableMap */
			Iterator<Map.Entry<LocalDateTime, Race>> iter = 
				racesSchedule.entrySet().stream()
							.filter(entry -> 
										LocalDateTime.now()
										.compareTo(entry.getKey()) < 0)
				.iterator();
			
			while (iter.hasNext()) {
				races.add(iter.next().getValue());
			}
			return races;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Update all races.
	 *
	 * @param raceSet the race set
	 */
	public void updateAllRaces(Collection<Race> raceSet) {
		raceSet.forEach(race -> addRace(race));
		updated = true;
	}

	public boolean isUpdated() {
		return updated;
	}
}
