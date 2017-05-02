/**
 * 
 */
package by.malinouski.hrace.command.receiver;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.security.UserValidator;

/**
 * @author makarymalinouski
 */
public class LoginReceiver extends CommandReceiver {
	static final Logger logger = LogManager.getLogger(LoginReceiver.class);
	
	@Override
	public Entity act(Entity entity) {
		User user = (User) entity;
		UserValidator valid = new UserValidator();
		if (valid.validate(user)) {
			return user;
		} else {
			return new Message(BundleConsts.NO_MATCH_FOUND);
		}
	}

}
