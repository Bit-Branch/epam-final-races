/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.time.LocalDateTime;
import java.util.concurrent.CancellationException;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.BetDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.logic.betting.BetCallable;
import by.malinouski.hrace.logic.entity.Bet;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.FutureEntity;
import by.malinouski.hrace.logic.entity.Message;

/**
 * @author makarymalinouski
 *
 */
public class PlaceBetReceiver extends CommandReceiver {
	private static Logger logger = 
			LogManager.getLogger(PlaceBetReceiver.class);

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		Bet bet = (Bet) entity;
		if (LocalDateTime.now().isAfter(bet.getRaceDateTime())) {
			return new Message(BundleConsts.RACE_FINISHED);
		}
		
		try {
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
