/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.entity;


/**
 * Represents a unit with a horse
 * and information relating to 
 * particular horse at particular race
 * @author makarymalinouski
 */
public class HorseUnit {
	private Horse horse;
	private Odds odds;
	private double realProb;
	private short positionAtStart;
	private short finalPosition;
	
	public HorseUnit(Horse horse, short oddsAgainst, short oddsInfavor, 
			double realProb, short posAtStart) {
		this.horse = horse;
		this.odds.against = oddsAgainst;
		this.odds.infavor = oddsInfavor;
		this.realProb = realProb;
		positionAtStart = posAtStart;
	}
	
	public Horse getHorse() {
		return horse;
	}
	
	public Odds getOdds() {
		return odds;
	}
	
	public double getRealProb() {
		return realProb;
	}
	
	public short getPositionAtStart() {
		return positionAtStart;
	}
	
	public short getFinalPosition() {
		return finalPosition;
	}
	
	public void setFinalPosition(short finalPosition) {
		this.finalPosition = finalPosition;
	}
	
	/**
	 * Represents odds of winning
	 * as shown to clients
	 * having odds agains, 
	 * and odds in favor of winning
	 * @author makarymalinouski
	 */
	public class Odds {
		private short against;
		private short infavor;
		
		public Odds(short against, short infavor) {
			this.against = against;
			this.infavor = infavor;
		}
		
		public short getAgainst() {
			return against;
		}
		
		public short getInfavor() {
			return infavor;
		}
		
		public String toString() {
			return String.format("%d/%d", against, infavor);
		}
	}
}
