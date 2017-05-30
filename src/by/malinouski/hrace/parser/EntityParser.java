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

import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public abstract class EntityParser {

	/**
	 * Parses the.
	 *
	 * @param paramMap the param map
	 * @param user the user
	 * @return the entity
	 */
	public abstract Entity parse(Map<String, String[]> paramMap, User user);
}
