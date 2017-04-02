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
import java.util.List;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Bet.BetType;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Odds;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class WinAmountCalculator {

	/**
	 * Calculates the amount of winning for the bet
	 * @param bet
	 * @param race 
	 * @return winning amount
	 */
	public BigDecimal calculate(Bet bet, Race race) {
		List<HorseUnit> units = race.getHorseUnits();
		List<Integer> horseNums = bet.getHorsesInBet();
		HorseUnit unitInBet = units.get(
						horseNums.get(NumericConsts.WIN_INDEX) - 1);

		double multiplFactor = 0;
		BetType type = bet.getType();
		
		switch (type) {
			case WIN:
			case EXACTA:
			case TRIFECTA:
				for (Integer horseNum : horseNums) {
					HorseUnit unit = units.get(horseNum - 1);
					Odds odds = unit.getOdds();
					multiplFactor += odds.getAgainst() / 
										(double) odds.getInfavor();
				}
				break;
				
			case QUINELLA:
				for (Integer horseNum : horseNums) {
					HorseUnit unit = units.get(horseNum - 1);
					Odds odds = unit.getOdds();
					multiplFactor += odds.getAgainst() / 
										(double) odds.getInfavor();
				}
				
				multiplFactor /= NumericConsts.DOUBLE_DIVISOR;
				break;
				
			case PLACE:
				multiplFactor = unitInBet.getOdds().getAgainst() / 
									(double) unitInBet.getOdds().getInfavor();
				
				if (multiplFactor > NumericConsts.TRIPLE_DIVISOR) {
					multiplFactor /= NumericConsts.TRIPLE_DIVISOR;
					break;
				} 
				// else fall through and try DOUBLE_DIVISOR
			case SHOW:
				multiplFactor = unitInBet.getOdds().getAgainst() / 
									(double) unitInBet.getOdds().getInfavor();
				if (multiplFactor > NumericConsts.DOUBLE_DIVISOR) {
					multiplFactor /= NumericConsts.DOUBLE_DIVISOR;
				} else {
					double divisor = NumericConsts.DOUBLE_DIVISOR;
					while (multiplFactor <= divisor) {
						divisor /= NumericConsts.DIVISOR_COEFF;
						if (divisor < 1) {
							divisor = 1;
							break;
						}
					}
					multiplFactor /= divisor;
				}
				break;
		}
		
		BigDecimal multiplicand = BigDecimal.valueOf(
				multiplFactor > 1 ? multiplFactor : NumericConsts.MIN_FACTOR);
		
		BigDecimal winning = bet.getAmount().multiply(multiplicand);
		return winning;
	}

}
