/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.parser.factory;

import java.util.Map;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.parser.BetParser;
import by.malinouski.horserace.parser.RacesSeriesParser;
import by.malinouski.horserace.parser.EntityParser;
import by.malinouski.horserace.parser.RaceParser;
import by.malinouski.horserace.parser.UserParser;

/**
 * @author makarymalinouski
 *
 */
public class EntityParserFactory {
	public EntityParser getParser(Command command) {
		switch (command) {
			case REGISTER:
			case LOGIN:
			case DELETE_PROFILE:
				return new UserParser();
			case PLACE_BET:
				return new BetParser();
			case GENERATE_RACES:
				return new RacesSeriesParser();
			case CANCEL_RACE:
				return new RaceParser();
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
