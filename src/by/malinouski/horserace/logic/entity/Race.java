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
public class Race {
	private long raceId;
	private LocalDateTime dateTime;
	private SortedSet<HorseUnit> horseUnits;
	/*
	 * Convenience field for final positions and horses names
	 * Position of the horse is characterized by the index
	 */
	private List<String> finalPositions;
	
	public Race(long id, LocalDateTime dateTime, SortedSet<HorseUnit> horseUnits) {
		this.raceId = id;
		this.dateTime = dateTime;
		this.horseUnits = horseUnits;
	}
	
	public long getRaceId() {
		return raceId;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public SortedSet<HorseUnit> getHorseUnits() {
		return horseUnits;
	}
	
	public List<String> getFinalPositions() {
		if (finalPositions == null && isOver()) {
			finalPositions = new ArrayList<String>();
		} 
		return Collections.unmodifiableList(finalPositions);
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
}
