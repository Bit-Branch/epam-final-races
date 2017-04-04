/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.generator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RacesGenerator {
	private static final Logger logger = LogManager.getLogger(RacesGenerator.class);

	public SortedSet<Race> generate(LocalDateTime firstDateTime, 
						int numRaces, int intervalMinutes, List<HorseUnit> units) {
		
		SortedSet<Race> races = new TreeSet<>(
				(r1, r2) -> r1.getDateTime().compareTo(r2.getDateTime()));
		LocalDateTime dateTime = firstDateTime;
		for (int i = 0; i <  numRaces; i++) {
			List<HorseUnit> unitsShallowCopy = new ArrayList<>(units.size());
			units.forEach(u -> {
				try {
					unitsShallowCopy.add((HorseUnit) u.clone());
				} catch (CloneNotSupportedException e) {
					logger.error("Shouldn't get here");
				}
			});
			Race race = new Race(dateTime, unitsShallowCopy);
			races.add(race);
			dateTime = dateTime.plusMinutes(intervalMinutes);
		}
		return races;
	}

}
