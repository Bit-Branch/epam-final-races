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
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import by.malinouski.horserace.exception.WinAmountAlreadySetException;


/**
 * @author makarymalinouski
 *
 */
public class Bet implements Entity {

	private long betId;
	private User user;
	private BetType type;
	private LocalDateTime raceDateTime;
	private BigDecimal amount;
	private BigDecimal winning;
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
						Bet.BetType type,
							BigDecimal amount, 
								LocalDateTime dateTime, 
									List<Integer> horsesInBet) {
		this.betId = id;
		this.user = user;
		this.raceDateTime = dateTime;
		this.amount = amount.setScale(4, RoundingMode.DOWN);
		this.type = type;
		this.horsesInBet = horsesInBet;
	}
	
	public Bet() {
		// TODO Auto-generated constructor stub
	}

	public enum BetType {
		WIN, SHOW, PLACE, QUINELLA, EXACTA, TRIFECTA
	}
	
	public long getBetId() {
		return betId;
	}
	
	/**
	 * Sets the betId. Can be done only once
	 * @param id
	 */
	public void setBetId(long id) {
		if (betId == 0) {
			betId = id;
		}
	}
	
	public User getUser() {
		return user;
	}
	
	public LocalDateTime getRaceDateTime() {
		return raceDateTime;
	}	
	
	public BetType getType() {
		return type;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public BigDecimal getWinning() {
		return winning;
	}

	/**
	 * Sets the winning amount,
	 * can be done only once
	 * @param winning
	 * @throws WinAmountAlreadySetException 
	 */
	public void setWinning(BigDecimal winning) throws WinAmountAlreadySetException {
		if (this.winning == null && winning != null) {
			this.winning = winning.setScale(4, RoundingMode.DOWN);
		} else {
			throw new WinAmountAlreadySetException("Amount is: " + this.winning);
		}
	}
	
	public List<Integer> getHorsesInBet() {
		return Collections.unmodifiableList(horsesInBet);
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setType(BetType type) {
		this.type = type;
	}

	public void setRaceDateTime(LocalDateTime raceDateTime) {
		this.raceDateTime = raceDateTime;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setHorsesInBet(List<Integer> horsesInBet) {
		this.horsesInBet = horsesInBet;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Bet: id %d, user %s, race %s, type %s, "
						+ "horses in bet %s, amount %s, winning %s",
						betId, user.getLogin(), raceDateTime, type, 
						horsesInBet, amount, winning);
	}
}

