/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.parser;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

import by.malinouski.hrace.constant.ParamsMapKeys;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class CreditParser extends EntityParser {

	/* (non-Javadoc)
	 * @see by.malinouski.hrace.parser.EntityParser#parse(java.util.Map, by.malinouski.hrace.logic.entity.User)
	 */
	@Override
	public Entity parse(Map<String, String[]> paramMap, User user) {
		String numberStr = paramMap.get(ParamsMapKeys.CARD_NUM)[0];
		String name = paramMap.get(ParamsMapKeys.CARDHOLDER_NAME)[0];
		String validMonth = paramMap.get(ParamsMapKeys.VALID_MONTH)[0];
		String cvvStr = paramMap.get(ParamsMapKeys.CVV_NUM)[0];
		String amountStr = paramMap.get(ParamsMapKeys.AMOUNT)[0];
		
		long number = Long.parseLong(numberStr);
		YearMonth valid = YearMonth.parse(validMonth);
		int cvv = Integer.parseInt(cvvStr);
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
		
		User.CreditCard card = user.new CreditCard(number, name, valid, cvv);
		
		user.setCard(card);
		user.setBalance(amount);

		return user;
	}

}
