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
import java.util.SortedSet;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.dao.RaceDao;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class ResultsReceiver extends CommandReceiver {

	/**
	 * @param requestMap
	 */
	public ResultsReceiver(Map<String, Object> requestMap) {
		super(requestMap);
	}

	/* (non-Javadoc)
	 * @see by.malinouski.horserace.command.receiver.CommandReceiver#act()
	 */
	@Override
	public void act() {
		RaceDao dao = new RaceDao();
		try {
			SortedSet<Race> raceSet = dao.preparePastRaces();
			requestMap.put(RequestMapKeys.RESULT, raceSet);
		} catch (DaoException e) {
			logger.error("Exception while preparing results: " + e);
		} finally {
			requestMap.put(RequestMapKeys.REDIRECT_PATH, PathConsts.HOME);
		}
	}

}
