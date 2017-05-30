/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.racing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.dao.HorseDao;
import by.malinouski.hrace.dao.RaceDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.exception.RaceCancelledException;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.generator.ResultsGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class RacesCallable.
 *
 * @author makarymalinouski
 */
public class RacesCallable implements Callable<Race> {
	private static Logger logger = LogManager.getLogger(RacesCallable.class);
	private Race race;
	
	/**
	 * Instantiates a new races callable.
	 *
	 * @param race the race
	 */
	public RacesCallable(Race race) {
		this.race = race;
	}
	
	@Override
	public Race call() throws RaceCancelledException {
		
		LocalDateTime datetime = race.getDateTime();
		logger.debug(datetime+"/now: " + LocalDateTime.now());
		while (LocalDateTime.now().isBefore(datetime)) {
			try { 
				TimeUnit.SECONDS.sleep(NumericConsts.RACING_THREAD_SLEEP_TIME); 
			} catch (InterruptedException e) { 
				throw new RaceCancelledException("Race cancelled at " + LocalDateTime.now());
			} 
		}
		ResultsGenerator gen = new ResultsGenerator();
		List<Integer> finalPos = gen.generate(race.getHorseUnits());
		logger.debug(String.format("Final positions for race %s\n%s", 
											race.getDateTime(), finalPos));
		int winnersNumber = finalPos.get(0);
		race.getHorseUnits().get(winnersNumber - 1).getHorse().incrNumWins();
		race.setFinalPositions();
		logger.debug(race.getFinalPositions());
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
