/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.dao.BetDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.exception.WinAmountAlreadySetException;
import by.malinouski.hrace.logic.entity.Bet;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.EntityContainer;
import by.malinouski.hrace.logic.entity.User;

/**
 * The Class AllBetsReceiver.
 *
 * @author makarymalinouski
 */
public class AllBetsReceiver extends CommandReceiver {
	private static Logger logger = LogManager.getLogger(AllBetsReceiver.class);
	
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
