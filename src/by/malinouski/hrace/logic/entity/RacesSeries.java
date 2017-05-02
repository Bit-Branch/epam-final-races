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
 * @author makarymalinouski
 *
 */
public class RacesSeries implements Entity {
	private static final long serialVersionUID = 1L;
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
