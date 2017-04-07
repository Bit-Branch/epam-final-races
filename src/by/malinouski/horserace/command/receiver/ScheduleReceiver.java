/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.EntityContainer;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesCache;


/**
 * @author makarymalinouski
 *
 */
public class ScheduleReceiver extends CommandReceiver {
	private static final Logger logger = LogManager.getLogger(ScheduleReceiver.class);
	
	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Entity act(Entity entity) {
		EntityContainer<Race> racesCont = new EntityContainer<>();
		RaceDao dao = new RaceDao();
		try {
			SortedSet<Race> raceSet = dao.selectNextRaces();
			racesCont.setEntities(raceSet);
			racesCont.setEntityName(Race.class.getSimpleName());
		} catch (DaoException e) {
			logger.error("Exception while preparing schedule: " + e);
		}
		return racesCont;
	}

}
