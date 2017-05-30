/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.entity;

import java.time.LocalDateTime;

/**
 * The Class RacesSeries.
 * Represents information on a series of races,
 * with a start datetime of the first race,
 * an amount of races and an interval of time between each.
 * @author makarymalinouski
 */
public class RacesSeries implements Entity {
	/**
	 * May 27, 2017 3:54 PM UTC
	 */
	private static final long serialVersionUID = -4716819087982797624L;
	private LocalDateTime firstRaceDateTime;
	private int racesAmount;
	private int interval;
	
	public LocalDateTime getFirstRaceDateTime() {
		return firstRaceDateTime;
	}
	
	public void setFirstRaceDateTime(LocalDateTime firstRaceDateTime) {
		this.firstRaceDateTime = firstRaceDateTime;
	}
	
	public int getRacesAmount() {
		return racesAmount;
	}
	
	public void setRacesAmount(int racesAmount) {
		this.racesAmount = racesAmount;
	}
	
	public int getInterval() {
		return interval;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	@Override
	public EntityType ofType() {
		return EntityType.RACES_SERIES;
	}
	
}
