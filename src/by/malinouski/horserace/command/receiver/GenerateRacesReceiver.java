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
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.dao.HorseDao;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Message;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.entity.RacesSeries;
import by.malinouski.horserace.logic.generator.HorsesLineupGenerator;
import by.malinouski.horserace.logic.generator.HorsesOddsGenerator;
import by.malinouski.horserace.logic.generator.RacesGenerator;
import by.malinouski.horserace.logic.racing.RacesRunner;
import by.malinouski.horserace.logic.racing.RacesSchedule;

/**
 * @author makarymalinouski
 *
 */
public class GenerateRacesReceiver extends CommandReceiver {
	private static final Logger logger = LogManager.getLogger(GenerateRacesReceiver.class);
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public 
//	Map<String, Entity>
	Entity act(Entity entity) {
		
//		Map<String, Entity> entityMap = new ConcurrentHashMap<>();
		RacesSeries series = (RacesSeries) entity;
		
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
			
			LocalDateTime datetime = series.getFirstRaceDateTime();
			int numRaces = series.getRacesAmount();
			int interval = series.getInterval();
			
			SortedSet<Race> races = racesGen.generate(datetime, numRaces, 
														interval, units);
			
			new RaceDao().insertNewRaces(races);
			RacesSchedule schedule = RacesSchedule.getInstance();
			races.forEach(race -> schedule.addRace(race));
			
			RacesRunner runner = RacesRunner.getInstance();
			runner.run(races);
			
			Message message = new Message("Races where generated (need to localize!)");
//			entityMap.put(RequestMapKeys.MESSAGE, message);
			return message;
		} catch (DaoException e) {
			logger.error("Exception while accessing db: " + e);
//			entityMap.put(RequestMapKeys.MESSAGE, 
			return new Message("not generated");
		}
		
//		return entityMap;
	}

}

