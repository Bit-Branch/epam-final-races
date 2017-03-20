/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.dao;

import by.malinouski.horserace.connection.ConnectionPool;

/**
 * @author makarymalinouski
 *
 */
public abstract class Dao {
	protected ConnectionPool pool;
	
	public Dao() {
		pool = ConnectionPool.getConnectionPool();
	}
}
