/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RaceDao extends Dao {
	private static final String SELECT_UPCOMING_RACES =
			"SELECT `datetime`, `races_id`, "
					+ "`horses_id`, `name`, `birth_year`, `tot_races`, `tot_wins`, "
					+ "`num_in_race`, `odds_against`, `odds_for`, `real_prob`, `fin_pos` FROM `races` "
			+ "INNER JOIN `races_stat` on `races`.`id`=`races_id` "
			+ "INNER JOIN `horses` on `horses`.`id` = `horses_id` ";
	private static final String WHERE_DATETIME_GT_NOW = "WHERE `datetime` > NOW()";
	private static final String WHERE_DATETIME_LT_NOW = "WHERE `datetime` < NOW()";

	private static final String RACES_ID_KEY = "races_id";
	private static final String DATETIME_KEY = "datetime";
	private static final String HORSES_ID_KEY = "horses_id";
	private static final String NAME_KEY = "name";
	private static final String BIRTH_YEAR_KEY = "birth_year";
	private static final String TOT_RACES_KEY = "tot_races";
	private static final String TOT_WINS_KEY = "tot_races";
	private static final String ODDS_AGAINST_KEY = "odds_against";
	private static final String ODDS_FOR_KEY = "odds_for";
	private static final String REAL_PROB_KEY = "real_prob";
	private static final String NUM_IN_RACE_KEY = "num_in_race";
	private static final String FIN_POS_KEY = "fin_pos";
	
	/**
	 * Prepares set of next races by fetching info from db
	 * and creating necessary objects
	 * @return set of future races sorted by datetime
	 * @throws DaoException
	 */
	public SortedSet<Race> prepareNextRaces() throws DaoException {
		SortedSet<Race> races = new TreeSet<>((r1, r2) -> 
										r1.getDateTime().compareTo(r2.getDateTime()));
		selectRaces(races, WHERE_DATETIME_GT_NOW);
		return races;
	}
	
	/**
	 * Prepares set of past races by fetching info from db
	 * and creating necessary objects
	 * @return set of past races sorted by datetime from latest to earliest
	 * @throws DaoException
	 */
	public SortedSet<Race> preparePastRaces() throws DaoException {
		SortedSet<Race> races = new TreeSet<>((r1, r2) -> 
										r2.getDateTime().compareTo(r1.getDateTime()));
		selectRaces(races, WHERE_DATETIME_LT_NOW);
		return races;
	}
	
	private void selectRaces(SortedSet<Race> races, 
												final String whereClause) 
																throws DaoException {
		Connection conn = pool.getConnection();
		
		try (PreparedStatement stm = conn.prepareStatement(
										SELECT_UPCOMING_RACES + whereClause)) {
			ResultSet res = stm.executeQuery();
			boolean hasRow = res.next();
			
			while (hasRow) {
				SortedSet<HorseUnit> units = new TreeSet<>((u1, u2) -> 
										Integer.compare(u1.getNumberInRace(), 
														u2.getNumberInRace()));
				long raceId = res.getLong(RACES_ID_KEY);
				LocalDateTime datetime = res.getTimestamp(DATETIME_KEY).toLocalDateTime();
				
				do {
					HorseUnit unit = new HorseUnit(new Horse(res.getLong(HORSES_ID_KEY), 
													 res.getString(NAME_KEY), 
													 res.getInt(BIRTH_YEAR_KEY), 
													 res.getInt(TOT_RACES_KEY), 
													 res.getInt(TOT_WINS_KEY)));
					
					unit.setOdds(unit.new Odds(res.getInt(ODDS_AGAINST_KEY),
									   res.getInt(ODDS_FOR_KEY)));
					unit.setRealProb(res.getDouble(REAL_PROB_KEY));
					unit.setNumberInRace(res.getInt(NUM_IN_RACE_KEY));
					unit.setFinalPosition(res.getInt(FIN_POS_KEY));
					units.add(unit);
					hasRow = res.next();
				} while (hasRow && raceId == res.getLong(RACES_ID_KEY));
			
				races.add(new Race(raceId, datetime, units));
			}
		} catch (SQLException e) {
			throw new DaoException("\nException while accessing database: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	public void updateResults(Race race) throws DaoException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented");
	}

	public void insertNewRaces(SortedSet<Race> races) throws DaoException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented");
	}

	public Race prepareNextRaceOnly() throws DaoException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented");
	}
	
	
	
}
