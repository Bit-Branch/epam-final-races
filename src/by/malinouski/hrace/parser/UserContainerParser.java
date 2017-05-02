/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.parser;

import java.util.Arrays;
import java.util.Map;

import by.malinouski.hrace.constant.ParamsMapKeys;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.EntityContainer;
import by.malinouski.hrace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class UserContainerParser extends EntityParser {

	/**
	 * 
	 */
	public UserContainerParser() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see by.malinouski.hrace.parser.EntityParser#parse(java.util.Map, by.malinouski.hrace.logic.entity.User)
	 */
	@Override
	public Entity parse(Map<String, String[]> paramMap, User user) {
		String oldPass = paramMap.get(ParamsMapKeys.OLD_PASSWORD)[0];
		String newPass = paramMap.get(ParamsMapKeys.PASSWORD)[0];

		EntityContainer<User> cont = new EntityContainer<>();
		User newUser = new User();
		
		user.setPassword(oldPass);
		newUser.setLogin(user.getLogin());
		newUser.setPassword(newPass);
		cont.setEntities(Arrays.asList(user, newUser));
		return cont;
	}

}
