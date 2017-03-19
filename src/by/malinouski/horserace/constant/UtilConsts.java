/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.constant;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransitionRule;
import java.util.Locale;

/**
 * @author makarymalinouski
 *
 */
public class UtilConsts {


	public static final int NUM_HORSES_IN_RACE = 7;
	public static final int HORSE_PRIME_AGE = 5;
	public static final double GROW_AGE_COEFF = 2.0;
	public static final int BEFORE_PRIME_POWER = 4;
	public static final int AFTER_PRIME_POWER = 2;
	public static final double MIN_INIT_COEFF = 0.2;
	public static final double MAX_INIT_COEFF = 0.4;
	public static final double EXTRA_PROB = 0.05;
	public static final int HUNDRED = 100;
	public static final int TEN = 10;

	private UtilConsts() {
	}
}
