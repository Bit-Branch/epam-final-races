/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.racing;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.generator.ResultsGenerator;

/**
 * @author makarymalinouski
 *
 */
public class RacingCallable implements Callable<Race> {
	static final Logger logger = LogManager.getLogger(RacingCallable.class);
	private Race race;
	
	public RacingCallable(Race race) {
		this.race = race;
	}
	
	@Override
	public Race call() {
		
		LocalDateTime datetime = race.getDateTime();
		logger.debug(datetime+"/now: " + LocalDateTime.now());
		while (LocalDateTime.now().isBefore(datetime)) {
			try { 
				Thread.sleep(NumericConsts.RACING_THREAD_SLEEP_TIME); 
			} catch (InterruptedException e) { 
				logger.error("Sleep interrupted: " + e); 
			}
		}
		ResultsGenerator gen = new ResultsGenerator();
		gen.generate(race.getHorseUnits());
		return race;
	}
}
