/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.comparator;

import java.util.Comparator;

import by.malinouski.horserace.logic.entity.HorseUnit;

/**
 * @author makarymalinouski
 *
 */
public class ProbabilityComparator implements Comparator<HorseUnit> {


	@Override
	public int compare(HorseUnit unit1, HorseUnit unit2) {
		return Double.compare(unit1.getRealProb(), unit2.getRealProb());
	}

}
