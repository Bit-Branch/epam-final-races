/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.betting;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Horse;

/**
 * @author makarymalinouski
 *
 */
public class BettingPool {

	private ConcurrentHashMap<Horse, Set<Bet>> betsByHorse;
	private ConcurrentHashMap<Horse, BigDecimal> sumsByHorse;
	private BigDecimal totalSum;
	
	/**
	 * 
	 */
	public BettingPool() {
		betsByHorse = new ConcurrentHashMap<>();
	}
	
	public void addBet(Bet bet) {
		Integer horse = bet.getHorsesInBet().get(0);
		Set<Bet> bets = betsByHorse.get(horse);
		if (bets == null) {
			bets = new HashSet<>();
		}
		bets.add(bet);
		// if first bet on a horse, add new amount, otherwise sum it 
		
		totalSum = totalSum.add(bet.getAmount());
	}

}
