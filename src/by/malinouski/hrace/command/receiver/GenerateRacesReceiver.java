/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.dao.HorseDao;
import by.malinouski.hrace.dao.RaceDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.Horse;
import by.malinouski.hrace.logic.entity.HorseUnit;
import by.malinouski.hrace.logic.entity.Message;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.entity.RacesSeries;
import by.malinouski.hrace.logic.generator.HorsesLineupGenerator;
import by.malinouski.hrace.logic.generator.HorsesOddsGenerator;
import by.malinouski.hrace.logic.generator.RacesGenerator;
import by.malinouski.hrace.logic.racing.RacesCache;
import by.malinouski.hrace.logic.racing.RacesRunner;

/**
 * The Class GenerateRacesReceiver.
 *
 * @author makarymalinouski
 */
public class GenerateRacesReceiver extends CommandReceiver {
	private static final Logger logger = LogManager.getLogger(GenerateRacesReceiver.class);
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
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
			RacesCache cache = RacesCache.getInstance();
			races.forEach(race -> cache.addRace(race));
			
			RacesRunner runner = RacesRunner.getInstance();
			runner.startRaces(races);
			
			return new Message(BundleConsts.RACES_GENERATED);
			
		} catch (DaoException e) {
			logger.error("Exception while accessing db: " + e);
			return new Message(BundleConsts.RACES_NOT_GENERATED);
		}
	}

}

