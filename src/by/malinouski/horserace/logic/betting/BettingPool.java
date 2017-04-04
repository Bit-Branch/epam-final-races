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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import by.malinouski.horserace.exception.BetForDifferentRaceException;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class BettingPool {
	private Race race;
	private ConcurrentHashMap<Horse, Set<Bet>> betsByHorse;
	private ConcurrentHashMap<Horse, BigDecimal> sumsByHorse;
	private BigDecimal totalSum;
	
	/**
	 * 
	 */
	public BettingPool(Race race) {
		this.race = race;
		betsByHorse = new ConcurrentHashMap<>();
	}
	
	public void addBet(Bet bet) throws BetForDifferentRaceException {
		throw new UnsupportedOperationException("Not implemented");
//		if (bet.getRace().getDateTime() != race.getDateTime()) {
//			throw new BetForDifferentRaceException("This bet is for a different race");
//		}
//		Integer horse = bet.getHorsesInBet().get(0);
//		Set<Bet> bets = betsByHorse.get(horse);
//		if (bets == null) {
//			bets = new HashSet<>();
//		}
//		bets.add(bet);
//		// if first bet on a horse, add new amount, otherwise sum it 
//		
//		totalSum = totalSum.add(bet.getAmount());
	}

}
