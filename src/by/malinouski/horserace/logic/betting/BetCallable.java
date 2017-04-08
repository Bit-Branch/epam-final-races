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
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.dao.BetDao;
import by.malinouski.horserace.dao.UserDao;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesResults;

/**
 * @author makarymalinouski
 *
 */
public class BetCallable implements Callable<Bet> {
	private static Logger logger = 
			LogManager.getLogger(BetCallable.class);
	private Bet bet;

	public BetCallable(Bet bet) {
		this.bet = bet;
	}

	@Override
	public Bet call() throws Exception {
		RacesResults results = RacesResults.getInstance();

		LocalDateTime dateTime = bet.getRaceDateTime();
		Future<Race> futureRace = results.getFutureRace(dateTime);
		Race finishedRace = futureRace.get(); // waiting here
		
		BetsWinTester tester = new BetsWinTester();
		WinAmountCalculator calc = new WinAmountCalculator();
		logger.debug(bet);
		
		if (tester.isWinning(bet, finishedRace.getFinalPositions())) {
			logger.debug("is winning " + bet);
			BigDecimal win = calc.calculate(bet, finishedRace);
			logger.debug("win " + win);
			bet.setWinning(win);
			new BetDao().updateWinBet(bet);
		} else {
			bet.setWinning(BigDecimal.ZERO);
		}
		
		new UserDao().updateBalance(bet);
		return bet;
	}

}
