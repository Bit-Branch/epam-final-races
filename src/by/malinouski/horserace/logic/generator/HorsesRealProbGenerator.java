/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.generator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import by.malinouski.horserace.constant.NumericConsts;
import by.malinouski.horserace.logic.entity.Horse;
import by.malinouski.horserace.logic.entity.HorseUnit;
import by.malinouski.horserace.logic.entity.HorseUnit.Odds;

/**
 * @author makarymalinouski
 * Generator of the real probabilities of winning
 * for each horse in a list of horses (HorseUnits)
 */
public class HorsesRealProbGenerator {
	/**
	 * @param horseUnits list mutated by setting the realProb
	 * for each HorseUnit in the list
	 * horse's age must be more than 2 and less then 11
	 */
	public void generate(List<HorseUnit> horseUnits) {
		double probSum = 0;
		HashMap<Horse, Double> probMap = new HashMap<>();
		
		for(HorseUnit unit : horseUnits) {
			Horse horse = unit.getHorse();
			double prob = calcAbsProb(horse.getAge(), horse.getNumWins(), 
													horse.getNumRaces());
			probMap.put(horse, prob);
			probSum += prob;
		}
		
		final double finSum = probSum;
		
		horseUnits.forEach(unit -> {
			double finProb = probMap.get(unit.getHorse())/finSum;
			Odds odds = genOdds(unit, finProb);
			unit.setRealProb(finProb);
			unit.setOdds(odds);
		});
	}

	/*
	 * Calculates absolute probability of horse's winning based on info
	 * from https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4013968/figure/fig_002/
	 * where it is suggested that prime horse age is 5
	 * and the decline in power rate is slower after the prime age
	 * than the increase before the prime age.
	 * Based on this info, the formula I made is following:
	 * for age 2 to 5:
	 * numWins/numRaces * (-((age - 5)/2)**4 + 5)
	 * for age 6 to 10:
	 * numWins/numRaces * (-((age - 5)/2)**2 + 5)
	 * 
	 * If numWins or numRace is 0, random coefficient
	 * with some min and max bounds
	 */
	private double calcAbsProb(int age, int numWins, int numRaces) {
		int primeAge = NumericConsts.HORSE_PRIME_AGE;
		double coeff = NumericConsts.GROW_AGE_COEFF;
		double minInitCoeff = NumericConsts.MIN_INIT_COEFF;
		double maxInitCoeff = NumericConsts.MAX_INIT_COEFF;
		int pow = age <= primeAge 
				? NumericConsts.BEFORE_PRIME_POWER 
				: NumericConsts.AFTER_PRIME_POWER;
		
		Random rand = new Random();
		double priorWinProb = (numWins != 0 && numRaces != 0) 
							? numWins / (double) numRaces 
							: minInitCoeff + rand.nextDouble() % (maxInitCoeff - minInitCoeff);
		
		double ageCoeff = -Math.pow((age - primeAge)/coeff, pow) + primeAge; 
			
		return priorWinProb * ageCoeff;
				
	}
	
	private Odds genOdds(HorseUnit unit, double realProb) {
		int oddsFor = (int) Math.round(realProb * NumericConsts.TEN + NumericConsts.EXTRA_PROB);
		int oddsAgainst = NumericConsts.TEN - oddsFor;
		int gcd = calcGcd(oddsFor, oddsAgainst);
		oddsFor /= gcd;
		oddsAgainst /= gcd;
		Odds odds = unit.new Odds(oddsAgainst, oddsFor); 
		return odds;
	}
	
	private int calcGcd(int oddsFor, int oddsAgainst) {
		BigInteger bi1 = BigInteger.valueOf(oddsFor);
		BigInteger bi2 = BigInteger.valueOf(oddsAgainst);
		BigInteger gcd = bi1.gcd(bi2);
		return gcd.intValue();
	}
}
