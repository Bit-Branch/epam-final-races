/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.entity;

import java.util.SortedSet;

/**
 * @author makarymalinouski
 *
 */
public class EntityContainer<T extends Entity> implements Entity {
	private SortedSet<T> entities;

	public SortedSet<T> getEntities() {
		return entities;
	}

	public void setEntities(SortedSet<T> entities) {
		this.entities = entities;
	}
	
}
