/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.command.receiver;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.dao.RaceDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.EntityContainer;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.racing.RacesCache;

/**
 * The Class ResultsReceiver.
 *
 * @author makarymalinouski
 */
public class ResultsReceiver extends CommandReceiver {
	private static final Logger logger = 
			LogManager.getLogger(StartRacesReceiver.class);


	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		RaceDao dao = new RaceDao();
		EntityContainer<Race> racesCont = new EntityContainer<>();
		try {
			RacesCache cache = RacesCache.getInstance();
			Collection<Race> races;
			if (cache.isUpdated()) {
				races = cache.getAllRaces();
			} else {
				races = dao.selectPastRaces(NumericConsts.MAX_RACES);
				cache.updateAllRaces(races);
			}
			racesCont.setEntities(races);
		} catch (DaoException e) {
			logger.error("Exception while preparing results: " + e);
		} 
		return racesCont;
	}

}
