/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.dao.BetDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.WinAmountAlreadySetException;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.EntityContainer;
import by.malinouski.horserace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class AllBetsReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(AllBetsReceiver.class);
	
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		User user = (User) entity;
		EntityContainer<Bet> betsContainer = new EntityContainer<>();
		try {
			BetDao dao = new BetDao();
			SortedSet<Bet> bets = dao.selectBetsByUser(user);
			betsContainer.setEntities(bets);
		} catch (DaoException | WinAmountAlreadySetException e) {
			logger.error("Couldn't retreive bets " + e.getMessage());
		} 
		
		return betsContainer;
	}

}
