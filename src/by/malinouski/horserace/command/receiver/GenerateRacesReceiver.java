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
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import by.malinouski.horserace.logic.racing.RacingCallable;

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
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<Queue<? extends Future<? extends Entity>>> act() {
		logger.debug("in " + this.getClass().getName());
		LocalDateTime datetime = LocalDateTime.parse( 
				((String[]) requestMap.get(RequestMapKeys.START_DATETIME))[0]); 
		int numRaces = Integer.parseInt( 
				((String[]) requestMap.get(RequestMapKeys.NUM_OF_RACES))[0]); 
		int interval = Integer.parseInt( 
				((String[]) requestMap.get(RequestMapKeys.INTERVAL_BT_RACES))[0]); 
		
		Queue<Future<Race>> futureResults = new ArrayBlockingQueue<>(numRaces);
		HorseDao horseDao = new HorseDao();
		try {
			Set<Horse> horses = horseDao.selectAllHorses();
			List<HorseUnit> allUnits = new ArrayList<>(horses.size());
			HorsesLineupGenerator lineupGen = new HorsesLineupGenerator();
			HorsesOddsGenerator oddsGen = new HorsesOddsGenerator();
			RacesGenerator racesGen = new RacesGenerator();

			horses.forEach(horse -> allUnits.add(new HorseUnit(horse)));
			List<HorseUnit> units = lineupGen.generate(allUnits);
			oddsGen.generate(units);
			
			SortedSet<Race> races = racesGen.generate(datetime, numRaces, interval, units);
			ExecutorService service = Executors.newSingleThreadExecutor();
			
			Iterator<Race> iter = races.iterator();
			while (iter.hasNext()) {
				futureResults.add(service.submit(new RacingCallable(iter.next())));
			}

			requestMap.put(RequestMapKeys.RESULT, futureResults);
			
			new Thread(() -> {
				RaceDao dao = new RaceDao();
				try {
					dao.insertNewRaces(races);
				} catch (DaoException e) {
					logger.error("Exception while inserting new races" + e);
				}
			}).start(); 
		} catch (DaoException e) {
			logger.error("Exception while accessing db: " + e);
		}

		requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		return Optional.of(futureResults);
	}

}

