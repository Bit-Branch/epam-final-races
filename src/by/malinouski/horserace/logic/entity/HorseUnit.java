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
public class HorseUnit implements Entity, Cloneable {
	private static final long serialVersionUID = 1L;
	private Horse horse;
	private Odds odds;
	private double realProb;
	private int numberInRace;
	private int finalPosition;
	private boolean immutable;
	
	public HorseUnit(Horse horse) {
		this.horse = horse;
	}
	
	public Horse getHorse() {
		if (immutable) {
			horse.setImmutable();
		}
		return horse;
	}
	
	public Odds getOdds() {
		return odds;
	}
	
	public void setOdds(Odds odds) {
		if (!immutable) {
			this.odds = odds;
		}
	}
	
	public double getRealProb() {
		return realProb;
	}
	
	public void setRealProb(double realProb) {
		if (!immutable) {
			this.realProb = realProb;
		}
	}
	
	public int getNumberInRace() {
		return numberInRace;
	}
	
	public void setNumberInRace(int numberInRace) {
		if (!immutable) {
			this.numberInRace = numberInRace;
		}
	}
	
	public int getFinalPosition() {
		return finalPosition;
	}
	
	public void setFinalPosition(int i) {
		if (!immutable) {
			this.finalPosition = i;
		}
	}
	
	public void setImmutable() {
		immutable = true;
	}
	
	public boolean isImmutable() {
		return immutable;
	}
	
	@Override
	public String toString() {
		return String.format(
				"HorseUnit: Horse %s, Odds %s, real prob %s, number in race %s, final position %s\n", 
				horse, odds, realProb, numberInRace, finalPosition);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		
		HorseUnit other = (HorseUnit) obj;
		if (horse.equals(other.horse) && realProb == other.realProb
				&& numberInRace == other.numberInRace
				&& finalPosition == other.finalPosition
				&& immutable == other.immutable
				&& (odds == null ? other.odds == null 
								  : odds.equals(other.odds))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + horse.hashCode();
		result = 31 * result + odds.hashCode();
		long realProbLong = Double.doubleToLongBits(realProb);
		result = 31 * result + (int) (realProbLong ^ (realProbLong >>> 32));
		result = 31 * result + numberInRace;
		result = 31 * result + finalPosition;
		result = 31 * result + (immutable ? 1 : 0);

		return result;
	}
	
	/**
	 * Makes a shallow copy of the horseUnit
	 * Horse objects are not cloned,
	 * hence all HorseUnits with the same horses
	 * will point to the same horse
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
