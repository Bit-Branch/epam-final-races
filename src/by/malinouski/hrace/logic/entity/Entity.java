/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.entity;

import java.io.Serializable;

/**
 * @author makarymalinouski
 *
 */
public interface Entity extends Serializable {
	EntityType ofType();
}
