/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import by.malinouski.hrace.logic.entity.HorseUnit;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultsGenerator.
 *
 * @author makarymalinouski
 * Generates results for horse races
 */
public class ResultsGenerator {

	/**
	 * generates final positions for each horse in the set,
	 * and mutates the objects by setting their final positions.
	 *
	 * @param list the list
	 * @return convenience list with index corresponding to final pos minus 1
	 */
	public List<Integer> generate(List<HorseUnit> list) {
		List<Integer> results = new ArrayList<>(list.size()); 
		
		List<HorseUnit> copy = new ArrayList<>(list.size());
		copy.addAll(list);

		Iterator<HorseUnit> iter;
		Random rand = new Random();
		int finPos = 0;
		while (!copy.isEmpty()) {
			Collections.shuffle(copy);
			iter = copy.listIterator();
			
			while (iter.hasNext()) {
				HorseUnit unit = iter.next();
				/* prob of winning for each horse increases *
				 * as the places are taken by other horses  */
				if (unit.getRealProb() <= 0) { // check to prevent locking 
					iter.remove();
				} else if (rand.nextDouble() < 
						unit.getRealProb() * ((double) list.size()/copy.size())) {
					
					unit.setFinalPosition(++finPos);
					results.add(unit.getNumberInRace());
					iter.remove();
					break;
				}
			}
		}
		
		return results;
	}
}
