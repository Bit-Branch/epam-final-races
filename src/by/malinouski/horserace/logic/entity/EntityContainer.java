/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author makarymalinouski
 *
 */
public class EntityContainer<T extends Entity> implements Entity {
	private static final long serialVersionUID = 1L;
	private ArrayList<T> entities;
	private String entityName;
	
	public List<T> getEntities() {
		return entities;
	}
	
	public List<T> getEntities(int fromIndex, int toIndex) {
		if (fromIndex > getEntityAmount() - 1 || fromIndex >= toIndex) {
			return Collections.emptyList();
		} else if (toIndex > getEntityAmount()) {
			toIndex = getEntityAmount();
		}
		return entities.subList(fromIndex, toIndex);
	}
	
	public void setEntities(Collection<T> entities) {
		this.entities = new ArrayList<>(entities);
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public int getEntityAmount() {
		return entities.size();
	}
}
