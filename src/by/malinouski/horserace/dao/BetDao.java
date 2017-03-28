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
import java.sql.Types;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.logic.entity.Bet;

/**
 * @author makarymalinouski
 *
 */
public class BetDao extends Dao {

	private static final String INSERT_NEW_BET = 
			"INSERT INTO bets(users_id, races_datetime, bet_type, amount, "
			+ "horse_num1, horse_num2, horse_num3) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String LAST_INS_ID = "SELECT LAST_INSERT_ID()";
	
	public long placeBet(Bet bet) throws DaoException {
		Connection conn = pool.getConnection();
		
		try (PreparedStatement insertNewBet = conn.prepareStatement(INSERT_NEW_BET);
			 PreparedStatement lastInsId = conn.prepareStatement(LAST_INS_ID)) {
			/* users_id, races_datetime, bets_types_id,   *
			 * horses_id1, horses_id2, horses_id3, amount */
			insertNewBet.setLong(1, bet.getUserId());
			insertNewBet.setTimestamp(2, Timestamp.valueOf(bet.getRaceDateTime()));
			insertNewBet.setString(3, bet.getType().toString());
			insertNewBet.setBigDecimal(4, bet.getAmount());
			insertNewBet.setInt(5, bet.getHorsesInBet().get(0));
			if (bet.getHorsesInBet().size() > NumericConsts.TWO_HORSES) {
				insertNewBet.setInt(6, bet.getHorsesInBet().get(NumericConsts.ONE_HORSE));
				insertNewBet.setInt(7, bet.getHorsesInBet().get(NumericConsts.TWO_HORSES));
			} else if (bet.getHorsesInBet().size() > NumericConsts.ONE_HORSE) {
				insertNewBet.setInt(6, bet.getHorsesInBet().get(NumericConsts.ONE_HORSE));
				insertNewBet.setNull(7, Types.INTEGER);
			} else {
				insertNewBet.setNull(6, Types.INTEGER);
				insertNewBet.setNull(7, Types.INTEGER);
			}
			
			if (1 == insertNewBet.executeUpdate()) {
				ResultSet resSet = lastInsId.executeQuery();
				resSet.next();
				return resSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DaoException("Couldn't insert bet " + e);
		} finally {
			pool.returnConnection(conn);
		}
		throw new DaoException("Couldn't insert bet");
	}

}
