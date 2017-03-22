/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.connection.ConnectionPool;

/**
 * @author makarymalinouski
 *
 */
public abstract class Dao {
	static final Logger logger = LogManager.getLogger(Dao.class);
	protected ConnectionPool pool;
	
	public Dao() {
		pool = ConnectionPool.getConnectionPool();
	}
}
