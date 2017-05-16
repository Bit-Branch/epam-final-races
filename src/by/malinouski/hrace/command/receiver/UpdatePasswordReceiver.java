/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.UserDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.exception.HasherException;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.EntityContainer;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.security.Hasher;
import by.malinouski.hrace.security.SaltGenerator;
import by.malinouski.hrace.security.UserValidator;

/**
 * @author makarymalinouski
 *
 */
public class UpdatePasswordReceiver extends CommandReceiver {
	private static Logger logger = 
					LogManager.getLogger(UpdatePasswordReceiver.class);
	@Override
	public Entity act(Entity entity) {
		EntityContainer<? extends Entity> cont = (EntityContainer<?>) entity;
		List<? extends Entity> list = cont.getEntities();
		User oldUser = (User) list.get(0);
		User newUser = (User) list.get(1);
		try {
			byte[] salt = new SaltGenerator().generate();
			byte[] hash = new Hasher().hash(newUser.getPassword(), salt);
			newUser.setSalt(salt);
			newUser.setHash(hash);
			if (new UserValidator().validate(oldUser)) {
				new UserDao().updatePassword(newUser);
				logger.debug("updating password");
				return new Message(BundleConsts.PASS_UPDATED);
			} else {
				logger.debug("not updating password");
				return new Message(BundleConsts.PASS_NOT_UPDATED);
			}
		} catch (DaoException e) {
			logger.error("Exception while deleting user " + e.getMessage());
		} catch (HasherException e) {
			logger.error("Security exception " + e.getMessage());
		} 
		return new Message(BundleConsts.PROBLEM_OCCURED);
	}

}
