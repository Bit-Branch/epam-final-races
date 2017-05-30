/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import by.malinouski.hrace.connection.ConnectionPool;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.logic.entity.Horse;

// TODO: Auto-generated Javadoc
/**
 * The Class HorseDao.
 *
 * @author makarymalinouski
 */
public class HorseDao {

	private static final String SELECT_ALL = 
			"SELECT `id`, `name`, `birth_year`, `tot_races`, `tot_wins` from `horses`";
	private static final String UPDATE_NUM_RACES = 
			"UPDATE `horses` SET `tot_races` = `tot_races` + 1";
	private static final String UPDATE_NUM_WINS = 
			"UPDATE `horses` SET `tot_wins` = `tot_wins` + 1 WHERE `id` = ?";
	
	private static final String ID_KEY = "id";
	private static final String NAME_KEY = "name";
	private static final String BIRTH_YEAR_KEY = "birth_year";
	private static final String TOT_RACES_KEY = "tot_races";
	private static final String TOT_WINS_KEY = "tot_wins";
	
	/**
	 * Select all horses.
	 *
	 * @return the sets the
	 * @throws DaoException the dao exception
	 */
	public Set<Horse> selectAllHorses() throws DaoException {
		
		Set<Horse> horses = new HashSet<>();
		try (Connection conn = ConnectionPool.getInstance().getConnection();
				PreparedStatement stm = conn.prepareStatement(SELECT_ALL)) {
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				horses.add(new Horse(res.getLong(ID_KEY), 
									 res.getString(NAME_KEY), 
									 res.getInt(BIRTH_YEAR_KEY), 
									 res.getInt(TOT_RACES_KEY), 
									 res.getInt(TOT_WINS_KEY)));
			}
			return horses;
		} catch (SQLException e) {
			throw new DaoException("Exception while selecting horses: " + e);
		}
	}

	/**
	 * Update horses after race.
	 *
	 * @param winner the winner
	 * @throws DaoException the dao exception
	 */
	public void updateHorsesAfterRace(Horse winner) throws DaoException {
		
		try (Connection conn = ConnectionPool.getInstance().getConnection();
			 PreparedStatement numRacesStm = conn.prepareStatement(UPDATE_NUM_RACES);
			 PreparedStatement numWinsStm  = conn.prepareStatement(UPDATE_NUM_WINS)) {
			numRacesStm.executeUpdate();
			numWinsStm.setLong(1, winner.getHorseId());
			numWinsStm.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Couldn't update " + e.getMessage());
		}
	}
}
