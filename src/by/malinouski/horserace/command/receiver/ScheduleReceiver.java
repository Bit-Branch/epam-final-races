/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.command.receiver;

import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesSchedule;


/**
 * @author makarymalinouski
 *
 */
public class ScheduleReceiver extends CommandReceiver {
	
	/**
	 * @param requestMap 
	 */
	public ScheduleReceiver(Map<String, Object> requestMap) {
		super(requestMap);
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public Optional<? extends Entity> act() {
		RaceDao dao = new RaceDao();
		try {
//			RacesSchedule schedule = RacesSchedule.getInstance();
//			schedule.getUpcommingRaces();
			SortedSet<Race> raceSet = dao.selectNextRaces();
			requestMap.put(RequestMapKeys.RESULT, raceSet);
		} catch (DaoException e) {
			logger.error("Exception while preparing schedule: " + e);
		} finally {
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		}
		return Optional.empty();
	}

}
