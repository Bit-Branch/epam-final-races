/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.entity;

import java.time.LocalDate;

/**
 * @author makarymalinouski
 *
 */
public class Horse implements Entity {
	private long horseId;
	private String name;
	private int yearBorn;
	private int totalRaces;
	private int totalWins;
	private boolean immutable;
	
	public Horse(long id, String name, int yearBorn, int totRaces, int totWins) {
		horseId = id;
		this.name = name;
		this.yearBorn = yearBorn;
		this.totalRaces = totRaces;
		this.totalWins = totWins;
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
	
	public Horse incrNumRaces() {
		if (!immutable) {
			totalRaces++;
		}
		return this;
	}
	
	public int getNumWins() {
		return totalWins;
	}
	
	public Horse incrNumWins() {
		if (!immutable) {
			totalWins++;
		}
		return this;
	}
	
	public void setImmutable() {
		immutable = true;
	}
	
	@Override
	public String toString() {
		return String.format("Horse %s, %s, age %d, total races %d, total wins %d",
					horseId, name, getAge(), totalRaces, totalWins);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		
		Horse other = (Horse) obj;
		if (horseId == other.horseId && yearBorn == other.yearBorn 
			&& totalRaces == other.totalRaces && totalWins == other.totalWins
					&& immutable == other.immutable) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (int) (horseId ^ (horseId >>> 32));
		result = 31 * result + name.hashCode();
		result = 31 * result + yearBorn;
		result = 31 * result + totalRaces;
		result = 31 * result + totalWins;
		result = 31 * result + (immutable ? 1 : 0);
		return result;
	}
}
