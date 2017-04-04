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
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.dao.HorseDao;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.generator.ResultsGenerator;

/**
 * @author makarymalinouski
 *
 */
public class RacesCallable implements Callable<Race> {
	static final Logger logger = LogManager.getLogger(RacesCallable.class);
	private Race race;
	
	public RacesCallable(Race race) {
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
		List<Integer> finalPos = gen.generate(race.getHorseUnits());
		logger.debug(String.format("Final positions for race %s\n%s", 
											race.getDateTime(), finalPos));
		int winnersNumber = finalPos.get(0);
		race.getHorseUnits().get(winnersNumber - 1).getHorse().incrNumWins();
		race.setFinalPositions();
		race.setImmutable();
		
		try {
			new RaceDao().updateResults(race);
			new HorseDao().updateHorsesAfterRace(
						race.getHorseUnits().get(winnersNumber - 1).getHorse());
		} catch (DaoException e) {
			logger.error("Couldn't update results " + e);
		}
		
		return race;
	}
}
