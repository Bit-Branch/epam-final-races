/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.BetDao;
import by.malinouski.horserace.dao.UserDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.NoRacesScheduledException;
import by.malinouski.horserace.exception.WinAmountAlreadySetException;
import by.malinouski.horserace.logic.betting.BetsWinTester;
import by.malinouski.horserace.logic.betting.WinAmountCalculator;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.logic.racing.RacesResults;

/**
 * @author makarymalinouski
 *
 */
public class PlaceBetReceiver extends CommandReceiver {

	public PlaceBetReceiver(Map<String, Object> requestMap) {
		super(requestMap);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<? extends Entity> act() {
		int amount = Integer.parseInt( 
				((String[]) requestMap.get(RequestMapKeys.AMOUNT))[0] );
		Bet.BetType betType = Bet.BetType.valueOf(
				((String[]) requestMap.get(RequestMapKeys.BET_TYPE))[0].toUpperCase() );
		LocalDateTime dateTime = LocalDateTime.parse(
				((String[]) requestMap.get(RequestMapKeys.DATETIME))[0] );

		User user = (User) requestMap.get(RequestMapKeys.USER);

		List<Integer> horsesNum = new ArrayList<>(); 
		String[] horsesNumStr = (String[]) requestMap.get(RequestMapKeys.HORSE_NUMBER);
		
		for (int i = 0; i < horsesNumStr.length && !horsesNumStr[i].isEmpty(); i++) {
			horsesNum.add(Integer.parseInt(horsesNumStr[i]));
		}

		BetDao betDao = new BetDao();
		Bet bet = new Bet(0, user, betType, BigDecimal.valueOf(amount), 
													dateTime, horsesNum);
		
		try {
			betDao.placeBet(bet);
				
			RacesResults results = RacesResults.getInstance();
			Future<Race> futureRace = results.getFutureRace(dateTime);
			requestMap.put(RequestMapKeys.FUTURE_RESULT, futureRace);
			
			// thread is going to wait here until race occurs
			Race finishedRace = futureRace.get();
			
			BetsWinTester tester = new BetsWinTester();
			WinAmountCalculator calc = new WinAmountCalculator();
			
			if (tester.isWinning(bet, finishedRace.getFinalPositions())) {
				logger.debug("is winning " + bet);
				BigDecimal win = calc.calculate(bet, finishedRace);
				logger.debug("win " + win);
				bet.setWinning(win);
				betDao.updateWinBet(bet);
			} else {
				bet.setWinning(BigDecimal.ZERO);
			}
			
			new UserDao().updateBalance(user, bet);
			
			return Optional.of(bet);
			
		} catch (CancellationException e) {
			logger.warn("Race was cancelled " + e.getMessage());
		} catch (InterruptedException | ExecutionException e) {
			logger.error("Problem with getting future results " + e);
		} catch (DaoException e) {
			logger.error("Exception while placing or updating bet " + e);
		} catch (NoRacesScheduledException e) {
			logger.error("No races at the specified time " + e);
		} catch (WinAmountAlreadySetException e) {
			logger.error("Win amount set second time " + e);
		} finally {
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		}

		return Optional.empty();
	}

}
