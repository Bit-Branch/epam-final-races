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
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import by.malinouski.horserace.constant.UtilStringConsts;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RacesGenerator {


	public SortedSet<Race> generate(LocalDateTime firstDateTime, 
						int numRaces, int intervalMinutes, List<HorseUnit> units) {
		
		SortedSet<Race> races = new TreeSet<>((r1, r2) -> r1.getDateTime().compareTo(r2.getDateTime()));
		LocalDateTime dateTime = firstDateTime;
		for (int i = 0; i <  numRaces; i++) {
			Race race = new Race(dateTime, units);
			races.add(race);
			dateTime = dateTime.plusMinutes(intervalMinutes);
		}
		return races;
	}

}
