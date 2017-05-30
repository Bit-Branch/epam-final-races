/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.generator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import by.malinouski.hrace.logic.entity.HorseUnit;
import by.malinouski.hrace.logic.entity.Race;

/**
 * The Class RacesGenerator.
 *
 * @author makarymalinouski
 */
public class RacesGenerator {

	/**
	 * Generate specified amount of races with specified horseUnits.
	 *
	 * @param firstDateTime the date time of the first race
	 * @param numRaces the number races
	 * @param intervalMinutes the interval between races in minutes
	 * @param units the horseUnits in the races
	 * @return the sorted set of races, sorted by DateTime
	 */
	public SortedSet<Race> generate(LocalDateTime firstDateTime, 
						int numRaces, int intervalMinutes, List<HorseUnit> units) {
		
		SortedSet<Race> races = new TreeSet<>(
				(r1, r2) -> r1.getDateTime().compareTo(r2.getDateTime()));
		LocalDateTime dateTime = firstDateTime;
		for (int i = 0; i <  numRaces; i++) {
			List<HorseUnit> unitsShallowCopy = new ArrayList<>(units.size());
			units.forEach(u -> unitsShallowCopy.add((HorseUnit) u.clone()));
			Race race = new Race(dateTime, unitsShallowCopy);
			races.add(race);
			dateTime = dateTime.plusMinutes(intervalMinutes);
		}
		return races;
	}

}
