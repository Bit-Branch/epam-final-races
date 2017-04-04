/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.entity;


/**
 * Represents odds of winning
 * as shown to clients
 * having odds agains, 
 * and odds in favor of winning
 * @author makarymalinouski
 */
public class Odds implements Entity {
	private int against;
	private int infavor;
	
	public Odds(int against, int infavor) {
		this.against = against;
		this.infavor = infavor;
	}
	
	public int getAgainst() {
		return against;
	}
	
	public int getInfavor() {
		return infavor;
	}
	
	
	@Override
	public String toString() {
		return String.format("%d/%d", against, infavor);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		
		Odds other = (Odds) obj;
		if (against == other.against && infavor == other.infavor) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int result = 31 * 17 + against;
		result = 31 * result + infavor;
		return result;
	}
}
