/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.exception.DaoException;
import by.malinouski.horserace.exception.WinAmountAlreadySetException;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Bet.BetType;
import by.malinouski.horserace.logic.entity.User;

/**
 * @author makarymalinouski
 *
 */
public class BetDao extends Dao {

	private static final String INSERT_NEW_BET = 
			"INSERT INTO bets(users_id, races_datetime, bet_type, amount, "
			+ "horse_num1, horse_num2, horse_num3) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_WINNING = 
			"UPDATE bets SET winning = ? WHERE id = ?";
	private static final String LAST_INS_ID = "SELECT LAST_INSERT_ID()";
	private static final String SELECT_BY_USER = 
			"SELECT id, races_datetime, bet_type, "
			+ "horse_num1, horse_num2, horse_num3, amount, winning "
			+ "FROM bets WHERE users_id = ?";
	private static final String CANCEL_BET = 
			"UPDATE bets SET cancelled = ? WHERE id = ?";
	
	private static final String ID_COL = "id";
	private static final String RACES_DATETIME_COL = "races_datetime";
	private static final String BET_TYPE_COL = "bet_type";
	private static final String HORSE_NUM1_COL = "horse_num1";
	private static final String HORSE_NUM2_COL = "horse_num2";
	private static final String HORSE_NUM3_COL = "horse_num3";
	private static final String AMOUNT_COL = "amount";
	private static final String WINNING_COL = "winning";
	
	/**
	 * Inserts a new bet into database.
	 * Sets the bet id (mutating bet parameter)
	 * @param bet
	 * @return id of the inserted bet
	 * @throws DaoException
	 */
	public void placeBet(Bet bet) throws DaoException {
		Connection conn = pool.getConnection();
		
		try (PreparedStatement insertNewBet = conn.prepareStatement(INSERT_NEW_BET);
			 PreparedStatement lastInsId = conn.prepareStatement(LAST_INS_ID)) {
			/* users_id, races_datetime, bets_types_id,   *
			 * horses_id1, horses_id2, horses_id3, amount */
			insertNewBet.setLong(1, bet.getUser().getUserId());
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
				bet.setBetId(resSet.getLong(1));
			}
		} catch (SQLException e) {
			throw new DaoException("Couldn't insert bet: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
	}

	public void updateWinBet(Bet bet) throws DaoException {
		Connection conn = pool.getConnection();
		
		try (PreparedStatement updateBet = conn.prepareStatement(UPDATE_WINNING)) {
			updateBet.setBigDecimal(1, bet.getWinning());
			updateBet.setLong(2,  bet.getBetId());
			updateBet.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Couldn't update bet " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
	}

	/**
	 * Retrieves all the bets of the given user
	 * @param user
	 * @return set of bets sorted by race's datetime
	 * @throws DaoException
	 * @throws WinAmountAlreadySetException 
	 */
	public SortedSet<Bet> selectBetsByUser(User user) 
								throws DaoException, WinAmountAlreadySetException {
		Connection conn = pool.getConnection();
		SortedSet<Bet> bets = new TreeSet<>((b1, b2) -> 
								b1.getRaceDateTime().compareTo(b2.getRaceDateTime()));
		
		try (PreparedStatement selectBets = conn.prepareStatement(SELECT_BY_USER)) {
			selectBets.setLong(1, user.getUserId());
			ResultSet res = selectBets.executeQuery();
			
			while (res.next()) {
				long id = res.getLong(ID_COL);
				Timestamp timest = res.getTimestamp(RACES_DATETIME_COL);
				String betType = res.getString(BET_TYPE_COL);
				int horse1 = res.getInt(HORSE_NUM1_COL);
				int horse2 = res.getInt(HORSE_NUM2_COL);
				int horse3 = res.getInt(HORSE_NUM3_COL);
				BigDecimal amount = res.getBigDecimal(AMOUNT_COL);
				BigDecimal winning = res.getBigDecimal(WINNING_COL);

				BetType type = BetType.valueOf(betType);
				LocalDateTime dateTime = timest.toLocalDateTime();
				List<Integer> horsesInBet = Arrays.asList(horse1, horse2, horse3);
				
				Bet bet = new Bet(id, user, type, amount, dateTime, horsesInBet);
				bet.setWinning(winning);
				bets.add(bet);
			}
			return bets;
		} catch (SQLException e) {
			throw new DaoException("Couldn't select bets " + e.getMessage());
		}
	}

	public void cancelBet(Bet bet) throws DaoException {
		Connection conn = pool.getConnection();

		try (PreparedStatement cancel = conn.prepareStatement(CANCEL_BET)) {
			cancel.setBoolean(1, true);
			cancel.setLong(2, bet.getBetId());
			cancel.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("BetDao: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}	
		
	}

}
