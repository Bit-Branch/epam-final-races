/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.HorseDao;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.generator.HorsesLineupGenerator;
import by.malinouski.horserace.logic.generator.HorsesOddsGenerator;
import by.malinouski.horserace.logic.generator.RacesGenerator;
import by.malinouski.horserace.logic.racing.RacesResults;
import by.malinouski.horserace.logic.racing.RacesRunner;
import by.malinouski.horserace.logic.racing.RacesSchedule;
import by.malinouski.horserace.logic.racing.RacesCallable;

/**
 * @author makarymalinouski
 *
 */
public class GenerateRacesReceiver extends CommandReceiver {

	/**
	 * @param requestMap
	 */
	public GenerateRacesReceiver(Map<String, Object> requestMap) {
		super(requestMap);
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<? extends Entity> act() {
		logger.debug("in " + this.getClass().getName());
		String[] dateTimeArr = (String[]) requestMap.get(
									RequestMapKeys.START_DATETIME);
		String[] numRacesArr = (String[]) requestMap.get(
									RequestMapKeys.NUM_OF_RACES);
		String[] intervalArr = (String[]) requestMap.get(
									RequestMapKeys.INTERVAL_BT_RACES);
		
		LocalDateTime datetime = LocalDateTime.parse(dateTimeArr[0]); 
		int numRaces = Integer.parseInt(numRacesArr[0]); 
		int interval = Integer.parseInt(intervalArr[0]); 
		
		HorseDao horseDao = new HorseDao();
		try {
			Set<Horse> horses = horseDao.selectAllHorses();
			
			List<HorseUnit> allUnits = new ArrayList<>(horses.size());
			HorsesLineupGenerator lineupGen = new HorsesLineupGenerator();
			HorsesOddsGenerator oddsGen = new HorsesOddsGenerator();
			RacesGenerator racesGen = new RacesGenerator();

			horses.forEach(horse -> allUnits.add(new HorseUnit(horse.incrNumRaces())));
			List<HorseUnit> units = lineupGen.generate(allUnits);
			oddsGen.generate(units);
			
			SortedSet<Race> races = racesGen.generate(datetime, numRaces, 
															interval, units);
			
			new RaceDao().insertNewRaces(races);
			RacesSchedule schedule = RacesSchedule.getInstance();
			races.forEach(race -> schedule.addRace(race));
			
			RacesRunner runner = RacesRunner.getInstance();
			runner.run(races);
			
			return Optional.of(races.first());
		} catch (DaoException e) {
			logger.error("Exception while accessing db: " + e);
			return Optional.empty();
		} finally {
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		}
	}

}

