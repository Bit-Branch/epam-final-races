/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author makarymalinouski
 *
 */
public class Bet {

	private long betId;
	private User user;
	private Race race;
	private BigDecimal amount;
	/* if horse's order matter in bet type
	 * then it's reflected in indices, 
	 * i.e. first horse must be index 0, second 1, etc.
	 */
	private List<Horse> horsesInBet;
	
	/**
	 * 
	 */
	public Bet() {
		// TODO Auto-generated constructor stub
	}
	
	public enum BetType {
		WIN, SHOW, PLACE, QUINELLA, EXACTA, TRIFECTA
	}
	
	public long getBetId() {
		return betId;
	}
	
	public long getUserId() {
		return user.getUserId();
	}
	
	public long getRaceId() {
		return race.getRaceId();
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public List<Horse> getHorsesInBet() {
		return Collections.unmodifiableList(horsesInBet);
	}
}
