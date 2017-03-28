/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import by.malinouski.horserace.constant.EntityConsts;

/**
 * @author makarymalinouski
 * Class Race represents a horserace
 * at a particular time, 
 * with a particular set of HorseUnits,
 * which represent a horse
 * each having an odds of winning,
 * position given at start,
 * and a final position,
 * set once the race is over
 */
public class Race implements Entity {
	private LocalDateTime dateTime;
	/* for convenience, 					 *
	 * future races indexed by num in race,  *
	 * past races by final position 		 */
	private List<HorseUnit> horseUnits;
	
	public Race(LocalDateTime dateTime, List<HorseUnit> horseUnits) {
		this.dateTime = dateTime;
		this.horseUnits = horseUnits;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public List<HorseUnit> getHorseUnits() {
		return horseUnits;
	}
	
	/**
	 * Convenience method to see if the race is over
	 * hence the finalPositions should be available
	 * @return race is finished
	 */
	public boolean isOver() {
		Instant inst = dateTime.toInstant(
				ZoneOffset.of(ZoneId.of(EntityConsts.TIME_ZONE).getId()));
		return inst.isAfter(Instant.now());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s %s", dateTime, horseUnits);
	}
}
