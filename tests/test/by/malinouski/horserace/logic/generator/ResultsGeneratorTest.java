package test.by.malinouski.horserace.logic.generator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.generator.HorsesRealProbGenerator;
import by.malinouski.horserace.logic.generator.ResultsGenerator;

@RunWith(Parameterized.class)
public class ResultsGeneratorTest {
	
	@Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[1][0]);
    }
    
	public static ResultsGenerator resGen;
	public static HorsesRealProbGenerator probGen;
	public static HorseUnit u0;
	public static HorseUnit u1;
	public static HorseUnit u2;
	public static HorseUnit u3;
	public static HorseUnit u4;
	public static HorseUnit u5;
	public static int trials;
	
	@BeforeClass
	public static void initGen() {
		resGen = new ResultsGenerator();
		probGen = new HorsesRealProbGenerator();
		u0 = new HorseUnit(new Horse(0, "new", 2014, 0, 0));
		u1 = new HorseUnit(new Horse(1, "lucky", 2013, 40, 10));
		u2 = new HorseUnit(new Horse(2, "smart", 2014, 20, 2));
		u3 = new HorseUnit(new Horse(3, "joy", 2012, 34, 5));
		u4 = new HorseUnit(new Horse(4, "warhorse", 2011, 40, 15));
		u5 = new HorseUnit(new Horse(5, "indigo", 2012, 30, 2));
		trials = 10000;
	}
	
	@Test
	public void generateTestResults() {
		u1.setRealProb(0.5);
		u2.setRealProb(0.1);
		List<HorseUnit> list = Arrays.asList(u1, u2);
		
		int smartNumWins = 0;
		
		// run generator sufficient number of times for statistics to become true
		for (int i = 0; i < trials; i++) {
			List<HorseUnit> l = resGen.generate(list);
			if (l.get(0).getHorse().getHorseId() == 2) {
				smartNumWins++;
			}
		}
		assertEquals("must be approximately equal", smartNumWins/100.0, 1/5.0, 0.1);
	}
	
	@Test
	public void generateTestResultsMore() {
		u1.setRealProb(0.4);
		u2.setRealProb(0.1);
		u3.setRealProb(0.2);
		u4.setRealProb(0.2);
		u5.setRealProb(0.1);
		List<HorseUnit> list = Arrays.asList(u1, u2, u3, u4, u5);
		
		int luckyCount = 0;
		int smartCount = 0;
		int joyCount = 0;
		int warCount = 0;
		int indigoCount = 0;
		
		// run generator sufficient number of times for statistics to become true
		for (int i = 0; i < trials; i++) {
			List<HorseUnit> l = resGen.generate(list);
			switch ((int)l.get(0).getHorse().getHorseId()) {
				case 1: luckyCount++;
				break;
				case 2: smartCount++;
				break;
				case 3: joyCount++;
				break;
				case 4: warCount++;
				break;
				case 5: indigoCount++;
				break;
			}
		}
		
		System.out.printf("lucky %6s, smart %6s, joy %6s, war %6s, indigo %6s\n", 
				luckyCount/(double)trials, smartCount/(double)trials, 
				joyCount/(double)trials, warCount/(double)trials, indigoCount/(double)trials);
		
		assertEquals("approx equals", luckyCount/(double)trials, 0.4, 0.05);
	}
	
	@Test
	public void generateTestProbOneHorse() {
		List<HorseUnit> units = Arrays.asList(u0);
		probGen.generate(units);
		assertEquals("must be 1", units.get(0).getRealProb(), 1.0, 0);
		
	}
	
	@Test
	public void generateTestProbTwoHorses() {
		List<HorseUnit> units = Arrays.asList(u1, u2);
		probGen.generate(units);
		
		System.out.println(units);
		/* u1 age coeff: -((4 - 5)/2)**2 + 5 = 19/4
		 * u1 win coeff: 1/4
		 * u1 prob: 19/16
		 * same way u2 prob: 4/10
		 * so, final prob u1 must be (4/10)/(19/16 + 4/10) = 95/127
		 */
		assertEquals("must be 95/127", 95/127.0, units.get(0).getRealProb(), 0.05);
		
	}
	
	@Test
	public void generateTestProbManyHorses() {
		List<HorseUnit> units = Arrays.asList(u0, u1, u2, u3, u4, u5);
		double sum = 0;
		for(HorseUnit unit : units) {
			sum += unit.getRealProb();
		}
		assertEquals("all real probs must add up to 1", sum, 1.0, 0.05);
	}

}
