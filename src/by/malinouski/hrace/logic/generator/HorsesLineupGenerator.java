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
import java.util.List;
import java.util.ListIterator;

import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.logic.entity.HorseUnit;

/**
 * @author makarymalinouski
 *
 */
public class HorsesLineupGenerator {
	
	/**
	 * Generates a random selection of HorseUnits
	 * @param allHorseUnits list of all HorseUnits available
	 * @return list of randomly selected HorseUnits
	 * 	for convenience index in the list matches number in race
	 */
	public List<HorseUnit> generate(List<HorseUnit> allHorseUnits) {
		List<HorseUnit> copyList = new ArrayList<>(allHorseUnits.size());
		copyList.addAll(allHorseUnits);
		Collections.shuffle(copyList);
		ListIterator<HorseUnit> iter = copyList.listIterator();
		while (iter.hasNext()) {
			iter.next().setNumberInRace(iter.nextIndex());
		}
		return copyList.subList(0, NumericConsts.NUM_HORSES_IN_RACE);
	}
}
