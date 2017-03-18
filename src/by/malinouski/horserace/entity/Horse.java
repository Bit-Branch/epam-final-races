/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.entity;

import java.time.LocalDate;

/**
 * @author makarymalinouski
 *
 */
public class Horse {
	private long horseId;
	private String name;
	private int yearBorn;
	private int totalRaces;
	private int totalWins;
	
	public Horse(long id, String name, int yearBorn, int numRaces, int numWins) {
		horseId = id;
		this.name = name;
		this.yearBorn = yearBorn;
		this.totalRaces = numRaces;
		this.totalWins = numWins;
	}
	
	public long getHorseId() {
		return horseId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getYearBorn() {
		return yearBorn;
	}
	
	public int getAge() {
		return LocalDate.now().getYear() - yearBorn;
	}
	
	public int getNumRaces() {
		return totalRaces;
	}
	
	public void incrementNumRaces() {
		totalRaces++;
	}
	
	public int getNumWins() {
		return totalWins;
	}
	
	public void incrementNumWins() {
		totalWins++;
	}
	
	@Override
	public String toString() {
		return String.format("Horse %s, %s, age %d, total races %d, total wins %d",
					horseId, name, getAge(), totalRaces, totalWins);
	}
}
