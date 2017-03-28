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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author makarymalinouski
 *
 */
public class Bet implements Entity {

	private long betId;
	private User user;
	private Race race;
	private BetType type;
	private BigDecimal amount;
	/* if horse's order matter in bet type
	 * then it's reflected in indices, 
	 * i.e. first horse must be index 0, second 1, etc.
	 */
	private List<Integer> horsesInBet;
	
	/**
	 * 
	 */
	public Bet(long id, 
					User user, 
						Race race, 
							BigDecimal amount, 
								Bet.BetType type,
									List<Integer> horsesInBet) {
		this.betId = id;
		this.user = user;
		this.race = race;
		this.amount = amount;
		this.type = type;
		this.horsesInBet = horsesInBet;
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
	
	public LocalDateTime getRaceDateTime() {
		return race.getDateTime();
	}
	
	public BetType getType() {
		return type;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public List<Integer> getHorsesInBet() {
		return Collections.unmodifiableList(horsesInBet);
	}
}
