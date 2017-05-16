package test.by.malinouski.hrace.logic.betting;

import static org.junit.Assert.*;

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

public class WinAmountCalculatorTestOneHorse {
	
	public static HorseUnit u0;
	public static HorseUnit u1;
	public static HorseUnit u2;
	public static HorsesOddsGenerator oddsGen;
	public static WinAmountCalculator calc;
	public static Bet bet;
	public static Race race;

	@BeforeClass
	public static void initGen() {
		u0 = new HorseUnit(new Horse(0, "new", 2014, 0, 0));
		u1 = new HorseUnit(new Horse(1, "lucky", 2013, 40, 10));
		u2 = new HorseUnit(new Horse(2, "smart", 2014, 20, 2));
		oddsGen = new HorsesOddsGenerator();
		calc = new WinAmountCalculator();
		u0.setOdds(new Odds(4, 1));
		u0.setNumberInRace(1);
		race = new Race(LocalDateTime.now(), Arrays.asList(u0));
		bet = new Bet();
		bet.setAmount(BigDecimal.ONE);
		bet.setHorsesInBet(Arrays.asList(1));
	}
	
	@Test
	public void testWin() {
		bet.setBetType(BetType.WIN);
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 4", 4, win.doubleValue(), 0.0);
	}
	
	@Test
	public void testShow() {
		bet.setBetType(BetType.SHOW);
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 2", 2, win.doubleValue(), 0.0);
	}
	
	@Test
	public void testShowLessThanTwo() {
		bet.setBetType(BetType.SHOW);
		race.getHorseUnits().get(0).setOdds(new Odds(3, 2));
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 1.5/1.366", 1.5/1.366, win.doubleValue(), 0.001);
	}
	
	@Test
	public void testPlace() {
		bet.setBetType(BetType.PLACE);
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 4/3", 4/3.0, win.doubleValue(), 0.0);
	}
	
	@Test
	public void testPlaceLessThanThreeBiggerThanTwo() {
		bet.setBetType(BetType.PLACE);
		race.getHorseUnits().get(0).setOdds(new Odds(7, 3));
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 7/6", 7/6.0, win.doubleValue(), 0.001);
	}
	
	@Test
	public void testPlaceLessThanTwo() {
		bet.setBetType(BetType.PLACE);
		race.getHorseUnits().get(0).setOdds(new Odds(3, 2));
		BigDecimal win = calc.calculate(bet, race);
		
		assertEquals("Must be 1.5/1.366", 1.5/1.366, win.doubleValue(), 0.001);
	}
	
	
}
