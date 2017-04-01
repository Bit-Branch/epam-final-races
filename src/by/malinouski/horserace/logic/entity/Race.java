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

import org.apache.logging.log4j.message.Message;

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
	private List<HorseUnit> horseUnits;
	/* List of final positions by number in race 	  *
	 * for convenience, indexed by final position - 1 */
	private List<Integer> finalPositions;
	private int hashCache;
	private boolean immutable;
	
	public Race(LocalDateTime dateTime, List<HorseUnit> horseUnits) {
		this.dateTime = dateTime;
		this.horseUnits = horseUnits;
		finalPositions = new ArrayList<>(horseUnits.size());
		for (int i = 0; i < horseUnits.size(); i++) {
			finalPositions.add(0);
		}
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public List<HorseUnit> getHorseUnits() {
		return Collections.unmodifiableList(horseUnits);
	}
	
	public List<Integer> getFinalPositions() {
		return Collections.unmodifiableList(finalPositions);
	}
	
	public void setFinalPositions() {
		horseUnits.forEach(u -> 
			finalPositions.set(u.getFinalPosition() - 1, u.getNumberInRace())
		);
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
	
	/**
	 * Sets immutable to true, and makes each HorseUnit object immutable,
	 * which as a result makes the whole Race object immutable
	 */
	public void setImmutable() {
		horseUnits.forEach(unit -> unit.setImmutable());
		immutable = true;
	}
	
	public boolean isImmutable() {
		return immutable;
	}
	
	/**
	 * Checks whether the two are the same race,
	 * but not necessarily equals due to possible
	 * mutation of the different representations
	 * of the same race
	 * @param other
	 * @return true if this and other are the same
	 */
	public boolean isSame(Race other) {
		if (dateTime == other.dateTime) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", dateTime, horseUnits);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		
		Race other = (Race) obj;
		if (dateTime == other.dateTime && immutable == other.immutable
				&& horseUnits.equals(other.horseUnits)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	public int hashCode() {
		if (hashCache != 0) {
			return hashCache;
		} else {
			int result = 17;
			result = 31 * result + dateTime.hashCode();
			result = 31 * result + (immutable ? 1 : 0);
			
			for(HorseUnit unit : horseUnits) {
				result = 31 * result + unit.hashCode(); 
			}
			
			for(int i : finalPositions) {
				result = 31 * result + i; 
			}
			hashCache = result;
			return result;
		}
	}
}
