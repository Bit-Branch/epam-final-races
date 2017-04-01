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
public class NumericConsts {


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
	public static final long RACING_THREAD_SLEEP_TIME = 30000;
	public static final int ONE_HORSE = 1;
	public static final int TWO_HORSES = 2;
	public static final int WIN_INDEX = 0;
	public static final double DOUBLE_DIVISOR = 2;
	public static final double TRIPLE_DIVISOR = 3;
	public static final int SHOW_INDEX = 1;
	public static final int PLACE_INDEX = 2;
	public static final double DIVISOR_COEFF = 1.1;
	public static final double MIN_FACTOR = 1.1;

	private NumericConsts() {
	}
}
