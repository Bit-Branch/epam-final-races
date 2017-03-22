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
import java.util.Set;
import java.util.SortedSet;
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
	public List<HorseUnit> generate(SortedSet<HorseUnit> allHorseUnits) {
		List<HorseUnit> results = new ArrayList<>(allHorseUnits.size()); 
		
		List<HorseUnit> copy = new ArrayList<>(allHorseUnits.size());
		copy.addAll(allHorseUnits);

		Iterator<HorseUnit> iter;
		Random rand = new Random();
		int finPos = 0;
		while (!copy.isEmpty()) {
			Collections.shuffle(copy);
			iter = copy.iterator();
			
			while (iter.hasNext()) {
				HorseUnit unit = iter.next();
				// prob of winning for each horse increases as the places are taken by other horses
				if (rand.nextDouble() < unit.getRealProb() * ((double)allHorseUnits.size() / copy.size())) {
					results.add(unit);
					unit.setFinalPosition(++finPos);
					iter.remove();
					break;
				}
			}
		}
		
		return results;
	}
}
