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
import java.util.List;
import java.util.Random;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.logic.entity.HorseUnit;

/**
 * @author makarymalinouski
 *
 */
public class HorsesLineupGenerator {
	
	/**
	 * Generates a random selection of HorseUnits
	 * @param allHorseUnits list of all HorseUnits available
	 * @return list of randomly selected HorseUnits
	 * 	for convenience index in the list represents matches position at start
	 */
	public List<HorseUnit> generate(List<HorseUnit> allHorseUnits) {
		List<HorseUnit> resultList = new ArrayList<>();
		
		Random random = new Random();
		for (int i = 0; i < NumericConsts.NUM_HORSES_IN_RACE; i++) {
			int index = random.nextInt(allHorseUnits.size());
			HorseUnit unit = allHorseUnits.get(index);
			unit.setNumberInRace((short)i);
			resultList.add(unit);
		}
		return resultList;
	}
}
