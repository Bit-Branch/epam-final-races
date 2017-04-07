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
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.BundleConsts;
import by.malinouski.horserace.dao.BetDao;
import by.malinouski.horserace.dao.UserDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.betting.BetCallable;
import by.malinouski.horserace.logic.betting.BetsWinTester;
import by.malinouski.horserace.logic.betting.WinAmountCalculator;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.FutureEntity;
import by.malinouski.horserace.logic.entity.Message;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesResults;

/**
 * @author makarymalinouski
 *
 */
public class PlaceBetReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(PlaceBetReceiver.class);

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		try {
			Bet bet = (Bet) entity;
			BetDao betDao = new BetDao();
			betDao.placeBet(bet);
			
			BetCallable betCall = new BetCallable(bet);
			FutureTask<Bet> futureBet = new FutureTask<>(betCall);
			futureBet.run();
			return new FutureEntity<>(futureBet);
		} catch (CancellationException e) {
			logger.warn("Race was cancelled " + e.getMessage());
		} catch (DaoException e) {
			logger.error("Exception while placing or updating bet " + e);
		}

		return new Message(BundleConsts.PROBLEM_OCCURED);
	}

}
