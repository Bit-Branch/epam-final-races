/**
 * 
 */
package by.malinouski.horserace.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author makarymalinouski
 *
 */
public class ConnectionPool implements AutoCloseable {
	private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
	private static final int POOL_SIZE = 2;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/horse_racing";
	private static final String USER = "root";
	private static final String PASSWORD = "R00tP@ssword";
	private final BlockingQueue<Connection> pool;
	
	private ConnectionPool() {
		pool = new ArrayBlockingQueue<Connection>(POOL_SIZE);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			for (int i = 0; i < POOL_SIZE; i++) {
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				pool.add(new ProxyConnection(conn));
			}
		} catch (SQLException | InstantiationException | 
					IllegalAccessException | ClassNotFoundException e) {
			logger.fatal("Could not connect to DataBase: " + e);
			throw new RuntimeException("Could not connect to DataBase: " + e);
		}	
	}
	
	private static class ConnectionPoolHolder {
		private static final ConnectionPool instance = new ConnectionPool();
	}
	
	public static ConnectionPool getConnectionPool() {
		return ConnectionPoolHolder.instance;
	}
	
	public Connection getConnection() {
		return pool.poll();
	}
	
	public void returnConnection(Connection conn) {
		logger.debug("Real pool size before return: " + pool.size());
		pool.offer(conn);
		logger.debug("Real pool size after return: " + pool.size());
	}
	
	@Override
	public void close() {
		while (!pool.isEmpty()) {
			try {
				pool.remove().close();
			} catch (SQLException e) {
				logger.error("Couldn't close connection: " + e);
			}
		}
	}
	
	

}
