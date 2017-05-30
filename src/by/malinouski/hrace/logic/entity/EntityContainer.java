/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author makarymalinouski
 *
 */
public class EntityContainer<T extends Entity> implements Entity {

	/**
	 * May 27, 2017 3:35 PM UTC
	 */
	private static final long serialVersionUID = -6548002973763578613L;
	private ArrayList<T> entities;
	
	public List<T> getEntities() {
		return entities;
	}
	
	/**
	 * Gets the entities.
	 *
	 * @param fromIndex the from index
	 * @param toIndex the to index
	 * @return the entities
	 */
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
	
	public EntityType getChildEntityType() {
		if (!entities.isEmpty()) {
			return entities.get(0).ofType();
		} else {
			return EntityType.NONE;
		}
	}
	
	public int getEntityAmount() {
		return entities.size();
	}
	
	@Override
	public EntityType ofType() {
		return EntityType.ENTITY_CONTAINER;
	}
}
