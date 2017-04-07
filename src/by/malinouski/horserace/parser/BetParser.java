/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.parser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.malinouski.horserace.constant.ParamsMapKeys;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Bet.BetType;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class BetParser extends EntityParser {

	/**
	 * 
	 */
	public BetParser() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Entity parse(Map<String, String[]> paramMap, User user) {
		String amountStr = paramMap.get(ParamsMapKeys.AMOUNT)[0];
		String betTypeStr = paramMap.get(ParamsMapKeys.BET_TYPE)[0];
		String dateTimeStr = paramMap.get(ParamsMapKeys.DATETIME)[0];
		String[] horsesNumArr = paramMap.get(ParamsMapKeys.HORSE_NUMBER);
		
		BigDecimal amount = new BigDecimal(amountStr);
		BetType betType = BetType.valueOf(betTypeStr.toUpperCase());
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
		List<Integer> horsesInBet = new ArrayList<>(); 

		for (int i = 0; i < horsesNumArr.length 
								&& !horsesNumArr[i].isEmpty(); i++) {
			horsesInBet.add(Integer.parseInt(horsesNumArr[i]));
		}
		
		Bet bet = new Bet();
		bet.setAmount(amount);
		bet.setType(betType);
		bet.setRaceDateTime(dateTime);
		bet.setUser(user);
		bet.setHorsesInBet(horsesInBet);
		
		return bet;
	}

}
