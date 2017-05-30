/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.entity;


/**
 * Represents odds of winning
 * as shown to clients
 * having odds agains, 
 * and odds in favor of winning
 * @author makarymalinouski
 */
public class Odds implements Entity {
	/**
	 * May 27, 2017 3:58 PM UTC
	 */
	private static final long serialVersionUID = -85690392749943170L;
	private int against;
	private int infavor;
	
	/**
	 * Instantiates a new odds.
	 *
	 * @param against the against
	 * @param infavor the infavor
	 */
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

	@Override
	public EntityType ofType() {
		return EntityType.ODDS;
	}
}
