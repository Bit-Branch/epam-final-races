/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.betting;

import java.util.List;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.logic.entity.Bet;
import by.malinouski.hrace.logic.entity.Bet.BetType;

/**
 * @author makarymalinouski
 *
 */
public class BetsWinTester {

	private static final Logger logger = 
							LogManager.getLogger(BetsWinTester.class);
	
	/**
	 * Checks whether the bet is winning
	 * @param bet
	 * @param finPos list of final positions by horse number,<br>
	 * where 0 is first position, 1 second etc.
	 * @return true if winning, false otherwise
	 */
	public boolean isWinning(Bet bet, List<Integer> finPos) {
		List<Integer> horsesInBet = bet.getHorsesInBet();
		BetType type = bet.getBetType();
		boolean isWinning = false;

		switch(type) {
		case WIN:
		case EXACTA:
		case TRIFECTA:
			isWinning = true;
			ListIterator<Integer> iter = horsesInBet.listIterator();
			while (iter.hasNext()) {
				int index = iter.nextIndex();
				int horseNum = iter.next();
				logger.debug(String.format("finPos %s: %s - %s", 
								index, finPos.get(index), horseNum));
				
				if (horseNum != finPos.get(index)) {
					isWinning = false;
					break;
				}
				
			}
			break;
			
		case QUINELLA:
			isWinning =	horsesInBet.contains(
								finPos.get(NumericConsts.WIN_INDEX)) &&
						horsesInBet.contains(
								finPos.get(NumericConsts.SHOW_INDEX));
			break;
			
		case SHOW:
			int pos = finPos.indexOf(horsesInBet.get(0));
			isWinning = pos == NumericConsts.WIN_INDEX || 
						pos == NumericConsts.SHOW_INDEX;
			break;
			
		case PLACE:
			int pos2 = finPos.indexOf(horsesInBet.get(0));
			isWinning = pos2 == NumericConsts.WIN_INDEX || 
						pos2 == NumericConsts.SHOW_INDEX ||
						pos2 == NumericConsts.PLACE_INDEX;
			break;
		}
		
		return isWinning;
	}
}
