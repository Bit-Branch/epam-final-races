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
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import by.malinouski.horserace.entity.HorseUnit;

/**
 * @author makarymalinouski
 * Generates results for horse races
 */
public class ResultsGenerator {

	/**
	 * generates final positions for each horse in the set,
	 * and mutates the objects by setting final positions
	 * @param allHorses set of horses without final position
	 */
	public List<HorseUnit> generate(List<HorseUnit> allHorseUnits) {
		List<HorseUnit> resultList = new ArrayList<>(allHorseUnits.size());
		// set sorted by probability of winning
		TreeSet<HorseUnit> sorted = new TreeSet<>((unit1, unit2) -> {
			return Double.compare(unit1.getRealProb(), unit2.getRealProb());
		});
		
		allHorseUnits.forEach(unit -> sorted.add(unit));
		Iterator<HorseUnit> iter;
		Random rand = new Random();

		while (!sorted.isEmpty()) {
			iter = sorted.iterator();
			while (iter.hasNext()) {
				HorseUnit unit = iter.next();
				// prob of winning for each horse increases as the places are taken by other horses
				if (rand.nextDouble() < unit.getRealProb() * allHorseUnits.size() / sorted.size()) {
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
