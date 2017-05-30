/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.betting;

import java.math.BigDecimal;
import java.util.List;

import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.logic.entity.Bet;
import by.malinouski.hrace.logic.entity.HorseUnit;
import by.malinouski.hrace.logic.entity.Odds;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.entity.Bet.BetType;

/**
 * The Class WinAmountCalculator.
 *
 * @author makarymalinouski
 * 
 * Caclulator that calculates the amount of win.
 * Works by multiplication principle: 
 * bet money are multiplied by factor,
 * which in case of 'win' bet - is odds against divided by odds for;
 * in case of 'exacta', 'trifecta' and 'quinella', 
 * the factor for each horse adds up,
 * but for 'quinella' it is then divided by DOUBLE_DIVISOR;
 * for 'place' and for 'show' the factor is divided by 
 * TRIPLE_ and DOUBLE_DIVISOR respectively,
 * unless that would produce a result smaller than 1. 
 * In that case:
 * 	for 'show' the divisor is divided by DIVISOR_COEFF
 * 	until multiplication factor would be larger than divisor,
 * 	and then it is divided by that divisor;
 * 	for 'place', first DOUBLE_DIVISOR tried, 
 * 	and if that is still smaller than one,
 * 	the same procedure as for 'show' is followed
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
		BetType type = bet.getBetType();
		
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
