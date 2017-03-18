package test.by.malinouski.horserace.logic.generator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import by.malinouski.horserace.entity.Horse;
import by.malinouski.horserace.entity.HorseUnit;
import by.malinouski.horserace.logic.generator.ResultsGenerator;

public class ResultsGeneratorTest {

	@Test
	public void generateTest() {
		ResultsGenerator gen = new ResultsGenerator();
		HorseUnit u1 = new HorseUnit(new Horse(1, "lucky", 2013, 40, 12));
		HorseUnit u2 = new HorseUnit(new Horse(2, "smart", 2014, 34, 10));
		u1.setRealProb(0.5);
		u2.setRealProb(0.1);
		List<HorseUnit> list = Arrays.asList(u1, u2);
		
		int smartNumWins = 0;
		
		// run generator sufficient number of times for statistics to become true
		for (int i = 0; i < 100; i++) {
			List<HorseUnit> l = gen.generate(list);
			if (l.get(0).getHorse().getHorseId() == 2) {
				smartNumWins++;
			}
		}
		System.out.println(smartNumWins/100.0);
		assertEquals("must be approximately equal", smartNumWins/100.0, 1/5.0, 0.1);
	}

}
