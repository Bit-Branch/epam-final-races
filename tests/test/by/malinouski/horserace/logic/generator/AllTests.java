package test.by.malinouski.horserace.logic.generator;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.generator.ResultsGenerator;

@RunWith(Suite.class)
@SuiteClasses({ HorsesOddsGeneratorTest.class, ResultsGeneratorTest.class })
public class AllTests {
    
	public static ResultsGenerator gen;
	public static HorseUnit u0;
	public static HorseUnit u1;
	public static HorseUnit u2;
	public static HorseUnit u3;
	public static HorseUnit u4;
	public static HorseUnit u5;
	public static int trials;
	
	@BeforeClass
	public static void initGen() {
		gen = new ResultsGenerator();
		u0 = new HorseUnit(new Horse(0, "new", 2014, 0, 0));
		u1 = new HorseUnit(new Horse(1, "lucky", 2013, 40, 12));
		u2 = new HorseUnit(new Horse(2, "smart", 2014, 34, 10));
		u3 = new HorseUnit(new Horse(3, "joy", 2014, 34, 10));
		u4 = new HorseUnit(new Horse(4, "warhorse", 2014, 34, 10));
		u5 = new HorseUnit(new Horse(5, "indigo", 2014, 34, 10));
		trials = 10000;
	}
}
