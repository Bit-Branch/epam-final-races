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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.Odds;
import by.malinouski.horserace.logic.entity.Race;

/**
 * @author makarymalinouski
 *
 */
public class RaceDao extends Dao {
	private static final String SELECT_RACES =
			"SELECT `races_datetime`, `horses_id`, `name`, `birth_year`, "
			+ "`tot_races`, `tot_wins`, `num_in_race`, `odds_against`, "
			+ "`odds_for`, `real_prob`, `fin_pos` FROM `races_stat` "
			+ "INNER JOIN `horses` on `horses`.`id` = `horses_id` ";
	
	private static final String WHERE_DATETIME_GT_NOW = 
			"WHERE `races_datetime` > NOW() ORDER BY `races_datetime` DESC";
	
	private static final String WHERE_DATETIME_LT_NOW = 
			"WHERE `races_datetime` < NOW() ORDER BY `races_datetime` DESC";
	
	private static final String ORDER_LIMIT_1 = 
							" ORDER BY `races_datetime` DESC LIMIT 1";
	
	private static final String INSERT_NEW_RACES = 
							"INSERT INTO `races`(`datetime`) VALUES(?)";
	
	private static final String INSERT_NEW_RACES_STAT = 
			"INSERT INTO `races_stat`(`races_datetime`, `horses_id`, `num_in_race`, "
			+ "`odds_against`, `odds_for`, `real_prob`, `fin_pos`) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
	
	private static final String LAST_ID = "SELECT LAST_INSERT_ID();";
	
	private static final String UPDATE_RACES = 
			"UPDATE `races_stat` SET `fin_pos` = ? "
			+ "WHERE `races_datetime` = ? AND `horses_id` = ?";
	
	private static final String CANCEL_RACE = 
			"UPDATE races SET cancelled = ? WHERE datetime = ?";

	private static final String DATETIME_KEY = "races_datetime";
	private static final String HORSES_ID_KEY = "horses_id";
	private static final String NAME_KEY = "name";
	private static final String BIRTH_YEAR_KEY = "birth_year";
	private static final String TOT_RACES_KEY = "tot_races";
	private static final String TOT_WINS_KEY = "tot_wins";
	private static final String ODDS_AGAINST_KEY = "odds_against";
	private static final String ODDS_FOR_KEY = "odds_for";
	private static final String REAL_PROB_KEY = "real_prob";
	private static final String NUM_IN_RACE_KEY = "num_in_race";
	private static final String FIN_POS_KEY = "fin_pos";
	private static final String LIMIT = " LIMIT ";
	
	/**
	 * Prepares set of next races by fetching info from db
	 * and creating necessary objects
	 * @return set of future races sorted by datetime
	 * @throws DaoException
	 */
	public SortedSet<Race> selectNextRaces() throws DaoException {
		SortedSet<Race> races = new TreeSet<>((r1, r2) -> 
										r1.getDateTime().compareTo(r2.getDateTime()));
		selectRaces(races, WHERE_DATETIME_GT_NOW);
		return races;
	}
	
	/**
	 * Prepares set of past races by fetching info from db
	 * and creating necessary objects
	 * @param maxRaces 
	 * @return set of past races sorted by datetime from latest to earliest
	 * @throws DaoException
	 */
	public SortedSet<Race> selectPastRaces(final int maxRaces) throws DaoException {
		SortedSet<Race> races = new TreeSet<>((r1, r2) -> 
										r2.getDateTime().compareTo(r1.getDateTime()));
		selectRaces(races, WHERE_DATETIME_LT_NOW + LIMIT + maxRaces);
		return races;
	}
	
	public Race selectNextRaceOnly() throws DaoException {
		SortedSet<Race> races = new TreeSet<>((r1, r2) -> 
							r1.getDateTime().compareTo(r2.getDateTime()));
		selectRaces(races, WHERE_DATETIME_GT_NOW + ORDER_LIMIT_1);
		return races.first();
	}
	
	private void selectRaces(SortedSet<Race> races, 
							 final String whereClause) throws DaoException {
		
		try (Connection conn = pool.getConnection();
  			 PreparedStatement stm = 
				conn.prepareStatement(SELECT_RACES + whereClause)) {
			ResultSet res = stm.executeQuery();
			boolean hasRow = res.next();
			
			while (hasRow) {
				SortedSet<HorseUnit> units = new TreeSet<>((u1, u2) -> 
					Integer.compare(u1.getNumberInRace(), u2.getNumberInRace()));
				Timestamp timestamp = res.getTimestamp(DATETIME_KEY);
				List<HorseUnit> unitsList = 
						new ArrayList<>(NumericConsts.NUM_HORSES_IN_RACE);
				LocalDateTime datetime = timestamp.toLocalDateTime();
				
				do {
					Long horseId = res.getLong(HORSES_ID_KEY);
					String horseName = res.getString(NAME_KEY);
					int birthYear = res.getInt(BIRTH_YEAR_KEY);
					int totRaces = res.getInt(TOT_RACES_KEY);
					int totWins = res.getInt(TOT_WINS_KEY);
					int oddsAgainst = res.getInt(ODDS_AGAINST_KEY);
					int oddsFor = res.getInt(ODDS_FOR_KEY);
					double realProb = res.getDouble(REAL_PROB_KEY);
					int numInRace = res.getInt(NUM_IN_RACE_KEY);
					int finPos = res.getInt(FIN_POS_KEY);
					
					Horse horse = new Horse(horseId, horseName, 
										birthYear, totRaces, totWins);
					
					HorseUnit unit = new HorseUnit(horse);
					unit.setOdds(new Odds(oddsAgainst, oddsFor));
					unit.setRealProb(realProb);
					unit.setNumberInRace(numInRace);
					unit.setFinalPosition(finPos);
					units.add(unit);
					hasRow = res.next();
				} while (hasRow && timestamp.equals(res.getTimestamp(DATETIME_KEY)));
				
				unitsList.addAll(units);
				races.add(new Race(datetime, unitsList));
			}
		} catch (SQLException e) {
			throw new DaoException("\nException while accessing database: " + e.getMessage());
		} 
	}

	/**
	 * Updates results of the `races_stat`
	 * @param race
	 * @throws DaoException
	 */
	public void updateResults(Race race) throws DaoException {
		
		try (Connection conn = pool.getConnection();
			PreparedStatement update = conn.prepareStatement(UPDATE_RACES)) {
			Timestamp stamp = Timestamp.valueOf(race.getDateTime());
			
			for (HorseUnit unit : race.getHorseUnits()) {
				update.setInt(1, unit.getFinalPosition());
				update.setTimestamp(2, stamp);
				update.setLong(3, unit.getHorse().getHorseId());
				update.addBatch();
			}
			
			update.executeBatch();
			
		} catch (SQLException e) {
			throw new DaoException("Exception while updating results: " + e.getMessage());
		}
	}

	/**
	 * Inserts new races statistics to `races_stat`
	 * @param races
	 * @throws DaoException
	 */
	public void insertNewRaces(SortedSet<Race> races) throws DaoException {

		try (Connection conn = pool.getConnection();
			 PreparedStatement insertRacesStm = conn.prepareStatement(INSERT_NEW_RACES);
			 PreparedStatement insertStatStm = conn.prepareStatement(INSERT_NEW_RACES_STAT)) {
			
			for (Race race : races) {
				insertRacesStm.setTimestamp(1, Timestamp.valueOf(race.getDateTime()));
				insertRacesStm.addBatch();
				
				// `races_datetime`, `horses_id`, `num_in_race`,
			    // `odds_agains`, `odds_for`, `real_prob`, `fin_pos`
				for(HorseUnit unit : race.getHorseUnits()) {
					insertStatStm.setTimestamp(1, Timestamp.valueOf(race.getDateTime()));
					insertStatStm.setLong(2, unit.getHorse().getHorseId());
					insertStatStm.setInt(3, unit.getNumberInRace());
					insertStatStm.setInt(4, unit.getOdds().getAgainst());
					insertStatStm.setInt(5, unit.getOdds().getInfavor());
					insertStatStm.setDouble(6, unit.getRealProb());
					insertStatStm.setInt(7, unit.getFinalPosition());

					insertStatStm.addBatch();
				}
			}
			insertRacesStm.executeBatch();
			insertStatStm.executeBatch();
			
		} catch (SQLException e) {
			throw new DaoException("Couldn't insert: " + e.getMessage());
		}
	}

	public void cancelRace(Race race) throws DaoException {

		try (Connection conn = pool.getConnection();
				PreparedStatement cancel = conn.prepareStatement(CANCEL_RACE)) {
			cancel.setBoolean(1, true);
			cancel.setTimestamp(2, Timestamp.valueOf(race.getDateTime()));
			cancel.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("RaceDao: " + e.getMessage());
		}	
	}

	
	
	
}
