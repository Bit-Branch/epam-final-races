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
 * @author makarymalinouski
 *
 */
public class Horse {
	private long horseId;
	private String name;
	
	/* Odds against winning
	 * as shown to user	
	 */
	private int oddsAgainst;
	
	/* Odds in favor of winning
	 * as shown to user	
	 */
	private int oddsFor;
	
	/* real chances of winning 	 
	 */
	private double realOdds;
	private int finalPos;
	
	public Horse(long id, String name, int oddsAgainst, int oddsFor, double realOdds) {
		horseId = id;
		this.name = name;
		this.oddsAgainst = oddsAgainst;
		this.oddsFor = oddsFor;
	}
	
	public long getHorseId() {
		return horseId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getOddsAgainst() {
		return oddsAgainst;
	}
	
	public int getOddsFor() {
		return oddsFor;
	}
	
	public double getRealOdds() {
		return realOdds;
	}
	
	public int getFinalPos() {
		return finalPos;
	}
	
	public void setFinalPos(int finalPos) {
		this.finalPos = finalPos;
	}
}
