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
					+ "`horses_id`, `name`, `birth_year`, `tot_races`, `tot_wins`"
					+ "`num_in_race`, `odds_against`, `odds_for`, `real_prob` FROM `races` "
			+ "INNER JOIN `races_stat` on `id`=`races_id` "
			+ "INNER JOIN `horses` on `horses.id` = `horses_id` "
			+ "WHERE `datetime` > NOW()";
	
	/**
	 * Prepares set of next races by fetching info from db
	 * and creating necessary objects
	 * @return set of future races sorted by date
	 * @throws DaoException
	 */
	public SortedSet<Race> prepareNextRaces() throws DaoException {
		SortedSet<Race> races = new TreeSet<>((r1, r2) -> 
									r1.getDateTime().compareTo(r2.getDateTime()));
		
		Connection conn = pool.getConnection();
		 
		try (PreparedStatement stm = conn.prepareStatement(SELECT_UPCOMING_RACES)) {
			ResultSet res = stm.executeQuery();
			boolean hasRow = res.next();
			
			while (hasRow) {
				SortedSet<HorseUnit> units = new TreeSet<>((u1, u2) -> 
													Integer.compare(u1.getNumberInRace(), 
																	u2.getNumberInRace()));
				long raceId = res.getLong("races_id");
				LocalDateTime datetime = res.getTimestamp("datetime").toLocalDateTime();
				
				do {
					HorseUnit unit = new HorseUnit(new Horse(res.getLong("horses_id"), 
															 res.getString("name"), 
															 res.getInt("birth_year"), 
															 res.getInt("tot_races"), 
															 res.getInt("tot_wins"))
									);
					unit.setOdds(unit.new Odds(res.getInt("odds_against"),
											   res.getInt("odds_for")));
					unit.setRealProb(res.getDouble("real_prob"));
					unit.setNumberInRace(res.getInt("num_in_race"));
					units.add(unit);
					hasRow = res.next();
				} while (hasRow && raceId == res.getLong("races_id"));
				
				races.add(new Race(raceId, datetime, units));
			}
			return races;
			
		} catch (SQLException e) {
			 throw new DaoException("Exception while preparing races");
		} finally {
			 pool.returnConnection(conn);
		}
	}	
}
