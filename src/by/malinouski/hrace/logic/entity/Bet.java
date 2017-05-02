/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import by.malinouski.hrace.exception.WinAmountAlreadySetException;


/**
 * @author makarymalinouski
 *
 */
public class Bet implements Entity {

	private static final long serialVersionUID = 1L;
	private long betId;
	private User user;
	private BetType betType;
	private LocalDateTime raceDateTime;
	private BigDecimal amount;
	private BigDecimal winning;
	/* if horse's order matter in bet type
	 * then it's reflected in indices, 
	 * i.e. first horse must be index 0, second 1, etc.
	 */
	private List<Integer> horsesInBet;

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
		this.betType = type;
		this.horsesInBet = horsesInBet;
	}
	
	public Bet() {
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

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getRaceDateTime() {
		return raceDateTime;
	}	

	public void setRaceDateTime(LocalDateTime raceDateTime) {
		this.raceDateTime = raceDateTime;
	}
	
	public BetType getBetType() {
		return betType;
	}

	public void setBetType(BetType type) {
		this.betType = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	

	public void setHorsesInBet(List<Integer> horsesInBet) {
		this.horsesInBet = horsesInBet;
	}
	
	@Override
	public EntityType ofType() {
		return EntityType.BET;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Bet: id %d, user %s, race %s, type %s, "
						+ "horses in bet %s, amount %s, winning %s",
						betId, user.getLogin(), raceDateTime, betType, 
						horsesInBet, amount, winning);
	}
	
	public enum BetType {
		WIN, SHOW, PLACE, QUINELLA, EXACTA, TRIFECTA
	}
}

