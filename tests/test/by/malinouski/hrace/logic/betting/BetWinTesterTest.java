package test.by.malinouski.hrace.logic.betting;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.Test;

import by.malinouski.hrace.logic.betting.BetsWinTester;
import by.malinouski.hrace.logic.entity.Bet;
import by.malinouski.hrace.logic.entity.Bet.BetType;

public class BetWinTesterTest {

	public static Bet bet;
	public static BetsWinTester tester;
	
	@BeforeClass
	public static void init() {
		bet = new Bet();
		tester = new BetsWinTester();

	}
	
	@Test
	public void isWinningTestEmpty() {
		assertTrue(!tester.isWinning(bet, Collections.emptyList()));
	}
	
	@Test
	public void isWinningTestWinTrue() {
		bet.setBetType(BetType.WIN);
		bet.setHorsesInBet(Arrays.asList(7));
		assertTrue(tester.isWinning(bet, Arrays.asList(7)));
	}
	
	@Test
	public void isWinningTestWinFalse() {
		bet.setBetType(BetType.WIN);
		bet.setHorsesInBet(Arrays.asList(7));
		assertTrue(!tester.isWinning(bet, Arrays.asList(6, 5, 7)));
	}
	
	@Test
	public void isWinningTestPlaceTrue() {
		bet.setBetType(BetType.PLACE);
		bet.setHorsesInBet(Arrays.asList(7));
		assertTrue(tester.isWinning(bet, Arrays.asList(2, 4, 7)));
	}
	
	@Test
	public void isWinningTestPlaceFalse() {
		bet.setBetType(BetType.PLACE);
		bet.setHorsesInBet(Arrays.asList(7));
		assertTrue(!tester.isWinning(bet, Arrays.asList(1, 2, 5, 7)));
	}
	
	@Test
	public void isWinningTestQuinellaTrue() {
		bet.setBetType(BetType.QUINELLA);
		bet.setHorsesInBet(Arrays.asList(7, 4));
		assertTrue(tester.isWinning(bet, Arrays.asList(7, 4)));
	}
	
	@Test
	public void isWinningTestQuinellaReorderTrue() {
		bet.setBetType(BetType.QUINELLA);
		bet.setHorsesInBet(Arrays.asList(7, 4));
		assertTrue(tester.isWinning(bet, Arrays.asList(4, 7)));
	}
	
	@Test
	public void isWinningTestQuinellaFalse() {
		bet.setBetType(BetType.QUINELLA);
		bet.setHorsesInBet(Arrays.asList(7, 4));
		assertTrue(!tester.isWinning(bet, Arrays.asList(7, 2)));
	}
	
	@Test
	public void isWinningTestExactaTrue() {
		bet.setBetType(BetType.EXACTA);
		bet.setHorsesInBet(Arrays.asList(3, 5));
		assertTrue(tester.isWinning(bet, Arrays.asList(3, 5)));
	}
	
	@Test
	public void isWinningTestExactaFalse() {
		bet.setBetType(BetType.EXACTA);
		bet.setHorsesInBet(Arrays.asList(3, 5));
		assertTrue(!tester.isWinning(bet, Arrays.asList(5, 3)));
	}

}
