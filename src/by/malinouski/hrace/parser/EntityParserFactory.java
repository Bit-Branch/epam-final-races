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

import by.malinouski.hrace.command.Command;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.User;

/**
 * A factory for creating EntityParser objects.
 *
 * @author makarymalinouski
 */
public class EntityParserFactory {
	public EntityParser getParser(Command command) {
		switch (command) {
			case REGISTER:
			case LOGIN:
			case DELETE_PROFILE:
				return new UserParser();
			case UPDATE_PASSWORD:
				return new UserContainerParser();
			case PLACE_BET:
				return new BetParser();
			case GENERATE_RACES:
				return new RacesSeriesParser();
			case CANCEL_RACE:
				return new RaceParser();
			case ADD_BALANCE:
				return new CreditParser();
			case SCHEDULE:
			case RESULTS:
			case START_RACES:
			case ALL_BETS:
			case CANCEL_BET:
			default:
				return new EntityParser() {
					@Override
					public Entity parse(Map<String, String[]> paramMap, User user) {
						return user;
					}
				};
		}
	}
}
