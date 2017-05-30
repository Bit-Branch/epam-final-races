package test.by.malinouski.hrace.logic.betting;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import by.malinouski.hrace.logic.betting.WinAmountCalculator;
import by.malinouski.hrace.logic.entity.Bet;
import by.malinouski.hrace.logic.entity.Bet.BetType;
import by.malinouski.hrace.logic.entity.Horse;
import by.malinouski.hrace.logic.entity.HorseUnit;
import by.malinouski.hrace.logic.entity.Odds;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.generator.HorsesOddsGenerator;

public class WinAmountCalculatorTestTestTwoHorses {
	public static HorseUnit u0;
	public static HorseUnit u1;
	public static HorseUnit u2;
	public static HorsesOddsGenerator oddsGen;
	public static WinAmountCalculator calc;
	public static Race race;
	public static Bet bet;

	@BeforeClass
	public static void initGen() {
		oddsGen = new HorsesOddsGenerator();
		calc = new WinAmountCalculator();
		u0 = new HorseUnit(new Horse(0, "new", 2014, 0, 0));
		u1 = new HorseUnit(new Horse(1, "lucky", 2013, 40, 10));
		u2 = new HorseUnit(new Horse(2, "smart", 2014, 20, 2));
		u0.setOdds(new Odds(4, 1));
		u0.setNumberInRace(1);
		u1.setOdds(new Odds(9, 1));
		u1.setNumberInRace(2);
		race = new Race(LocalDateTime.now(), Arrays.asList(u0, u1));
		bet = new Bet();
		bet.setAmount(BigDecimal.ONE);
		bet.setHorsesInBet(Arrays.asList(1, 2));
	}
	
	@Test
	public void testExacta() {
		
		bet.setBetType(BetType.EXACTA);
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 13", 13, win.doubleValue(), 0.0);
	}
	
	@Test
	public void testQuinella() {
		
		bet.setBetType(BetType.QUINELLA);
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 13/2", 13/2.0, win.doubleValue(), 0.0);
	}
	
}
