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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Future;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.BetDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.entity.User;

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
	public Optional<Queue<? extends Future<? extends Entity>>> act() {
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
		logger.debug("user " + user);
		Bet bet = new Bet(0, user, new Race(dateTime, Collections.emptyList()),
							BigDecimal.valueOf(amount), betType, horsesNum);
		
		BetDao dao = new BetDao();
		try {
			dao.placeBet(bet);
		} catch (DaoException e) {
			logger.error("Exception while placing bet " + e.getMessage());
		}
		
		requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		return Optional.empty() ;
	}

}
