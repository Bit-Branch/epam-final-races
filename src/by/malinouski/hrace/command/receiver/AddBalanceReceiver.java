/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.UserDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.finance.Charger;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.User;

/**
 * The Class AddBalanceReceiver.
 *
 * @author makarymalinouski
 */
public class AddBalanceReceiver extends CommandReceiver {
	private static Logger logger = LogManager.getLogger(AddBalanceReceiver.class);

	@Override
	public Entity act(Entity entity) {
		User user = (User) entity;
		
		try {
			BigDecimal newBalance = new UserDao().updateBalance(user);
			user.setBalance(newBalance);
			Charger.charge(user.getCard());
			return user;
		} catch (DaoException e) {
			logger.error("Couldn't update " + e.getMessage());
		}
		return new Message(BundleConsts.PROBLEM_OCCURED);
	}

}
