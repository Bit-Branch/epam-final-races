/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.parser;

import java.util.Map;

import by.malinouski.hrace.constant.ParamsMapKeys;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class UserParser extends EntityParser {

	@Override
	public Entity parse(Map<String, String[]> paramMap, User user) {
		String login = paramMap.get(ParamsMapKeys.LOGIN)[0];
		String password = paramMap.get(ParamsMapKeys.PASSWORD)[0];
		
		User newUser = new User();
		newUser.setLogin(login);
		newUser.setPassword(password);
		
		return newUser;
	}

}
