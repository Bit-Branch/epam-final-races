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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.malinouski.horserace.exception.NoRacesScheduledException;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RacesSchedule {

	public static final int MAX_ENTRIES = 100;
	private Lock lock = new ReentrantLock(); 
	private LinkedHashMap<LocalDateTime, Race> racesSchedule;
	
	@SuppressWarnings("serial")
	private RacesSchedule() {
		racesSchedule = new LinkedHashMap<LocalDateTime, Race>() {
			@Override
			protected boolean removeEldestEntry(
					Map.Entry<LocalDateTime, Race> eldest) {
				return size() > MAX_ENTRIES;
			}
		};
	}
	
	private static class InstanceHolder {
		private static final RacesSchedule instance = new RacesSchedule();
	}
	
	public static RacesSchedule getInstance() {
		return RacesSchedule.InstanceHolder.instance;
	}
	
	public void addRace(Race race) {
		lock.lock();
		try {
			racesSchedule.put(race.getDateTime(), race);
		} finally {
			lock.unlock();
		}
		
	}
	
	public Race getRace(LocalDateTime dateTime) throws NoRacesScheduledException {
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

}
