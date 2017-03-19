/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.logic.entity.HorseUnit;

/**
 * @author makarymalinouski
 * Generates results for horse races
 */
public class ResultsGenerator {
	private static final Logger logger = LogManager.getLogger(ResultsGenerator.class);

	/**
	 * generates final positions for each horse in the set,
	 * and mutates the objects by setting final positions
	 * @param allHorses set of horses without final position
	 */
	public List<HorseUnit> generate(List<HorseUnit> allHorseUnits) {
		List<HorseUnit> resultList = new ArrayList<>(allHorseUnits.size());
		List<HorseUnit> copy = new ArrayList<>(allHorseUnits.size());
		for (int i = 0; i < allHorseUnits.size(); i++) {
			copy.add(i, null);
		}
		Collections.copy(copy, allHorseUnits);
		// set sorted by probability of winning
//		TreeSet<HorseUnit> sorted = new TreeSet<>(new Comparator<HorseUnit>() {
//			public int compare(HorseUnit unit1, HorseUnit unit2) {
//				return Double.compare(unit2.getRealProb(), unit1.getRealProb());
//			}
//		}.thenComparing(new Comparator<HorseUnit>() {
//			public int compare(HorseUnit unit1, HorseUnit unit2) {
//				return Long.compare(unit2.getHorse().getHorseId(), unit1.getHorse().getHorseId());
//			}
//		}));
		
//		allHorseUnits.forEach(unit -> sorted.add(unit));
		
		Iterator<HorseUnit> iter;
		Random rand = new Random();

		while (!copy.isEmpty()) {
			Collections.shuffle(copy);
			iter = copy.iterator();
			while (iter.hasNext()) {
				HorseUnit unit = iter.next();
//				logger.debug(unit.getHorse().toString() + " " + (double) (allHorseUnits.size() / copy.size()));
				// prob of winning for each horse increases as the places are taken by other horses
				if (rand.nextDouble() < unit.getRealProb() * ((double)allHorseUnits.size() / copy.size())) {
					resultList.add(unit);
					unit.setFinalPosition(resultList.size());
					iter.remove();
					break;
				}
			}
		}
		
		return resultList;
	}
}
