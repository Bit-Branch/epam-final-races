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
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.BetDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.HorseUnit;
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
		logger.debug("user " + user);
		Bet bet = new Bet(0, user, new Race(dateTime, Collections.emptyList()),
							BigDecimal.valueOf(amount), betType, horsesNum);
		
		BetDao dao = new BetDao();
		
		try {
			dao.placeBet(bet);
				
			RacesResults results = RacesResults.getInstance();
			Future<Race> futureRace = results.getFutureRace(dateTime);
			requestMap.put(RequestMapKeys.FUTURE_RESULT, futureRace);
		
			Race race = futureRace.get();
			boolean isWinning = true;
			double multiplFactor = 0;
			ListIterator<Integer> iter = bet.getHorsesInBet().listIterator();
			logger.debug("size " + bet.getHorsesInBet().size());
			while (iter.hasNext()) {
				int index = iter.nextIndex();
				int horseNum = iter.next();
				logger.debug(String.format("finPos %s: %s - %s", index, 
						race.getFinalPositions().get(index), horseNum));
				
				if (horseNum != race.getFinalPositions().get(index)) {
					logger.debug("is winning set false");
					isWinning = false;
					break;
				}
				HorseUnit unit = race.getHorseUnits().get(horseNum - 1);
				multiplFactor += unit.getOdds().getAgainst() / 
									(double) unit.getOdds().getInfavor();
				
			}
			
			if (isWinning) {
				logger.debug("is winning " + bet);
				bet.setWinning(multiplFactor);
				dao.updateWinBet(bet);
			}
			
			return Optional.of(bet);
			
		} catch (InterruptedException | ExecutionException e) {
			logger.error("Problem with getting future results " + e);
			return Optional.empty();
		} catch (DaoException e) {
			logger.error("Exception while placing or updating bet " + e);
			return Optional.empty();
		} finally {
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		}
	}

}
