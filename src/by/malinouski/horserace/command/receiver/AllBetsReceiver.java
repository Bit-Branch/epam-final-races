/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.BetDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class AllBetsReceiver extends CommandReceiver {

	/**
	 * @param requestMap
	 */
	public AllBetsReceiver(Map<String, Object> requestMap) {
		super(requestMap);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<? extends Entity> act() {
		User user = (User) requestMap.get(RequestMapKeys.USER);
		BetDao dao = new BetDao();
		try {
			SortedSet<Bet> bets = dao.selectBetsByUser(user);
			requestMap.put(RequestMapKeys.RESULT, bets);
		} catch (DaoException e) {
			logger.error("Couldn't retreive bets " + e.getMessage());
		} finally {
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		}
		return Optional.empty();
	}

}
