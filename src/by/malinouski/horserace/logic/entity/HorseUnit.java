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
 * Represents a unit with a horse
 * and information relating to 
 * particular horse at particular race
 * @author makarymalinouski
 */
public class HorseUnit {
	private Horse horse;
	private Odds odds;
	private double realProb;
	private int numberInRace;
	private int finalPosition;
	
	public HorseUnit(Horse horse) {
		this.horse = horse;
	}
	
	public Horse getHorse() {
		return horse;
	}
	
	public Odds getOdds() {
		return odds;
	}
	
	public void setOdds(Odds odds) {
		this.odds = odds;
	}
	
	public double getRealProb() {
		return realProb;
	}
	
	public void setRealProb(double realProb) {
		this.realProb = realProb;
	}
	
	public int getNumberInRace() {
		return numberInRace;
	}
	
	public void setNumberInRace(int numberInRace) {
		this.numberInRace = numberInRace;
	}
	
	public int getFinalPosition() {
		return finalPosition;
	}
	
	public void setFinalPosition(int i) {
		this.finalPosition = i;
	}
	
	/**
	 * Represents odds of winning
	 * as shown to clients
	 * having odds agains, 
	 * and odds in favor of winning
	 * @author makarymalinouski
	 */
	public class Odds {
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
		
		public String toString() {
			return String.format("%d/%d", against, infavor);
		}
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format(
				"HorseUnit: Horse %s, Odds %s, real prob %s, position at start %s, final position %s\n", 
				horse, odds, realProb, numberInRace, finalPosition);
	}
}
