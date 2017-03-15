/**
 * 
 */
package by.malinouski.horserace.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
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
	private static final String POOL_SIZE_KEY = "poolSize";
	private static final String DB_URL_KEY = "url";
	private static final String USER_KEY = "user";
	private static final String PASSWORD_KEY = "password";
	private static final String DB_INFO_PATH = "resources/dbinfo/dbinfo.properties";
	private static final String DRIVER_KEY = "driver";
	private final BlockingQueue<Connection> pool;
	
	private ConnectionPool() {
		try {
			Properties prop = new Properties();
			prop.load(getClass().getClassLoader().getResourceAsStream(DB_INFO_PATH));
			
			pool = new ArrayBlockingQueue<Connection>(
					Integer.valueOf(prop.getProperty(POOL_SIZE_KEY)));
			
			logger.debug(prop.getProperty(DRIVER_KEY));
			Class.forName(prop.getProperty(DRIVER_KEY)).newInstance();
			
			for (int i = 0; i < Integer.valueOf(prop.getProperty(POOL_SIZE_KEY)); i++) {
				Connection conn = DriverManager.getConnection(
						prop.getProperty(DB_URL_KEY), 
						prop.getProperty(USER_KEY), 
						prop.getProperty(PASSWORD_KEY));
				pool.add(new ProxyConnection(conn));
			}
		} catch (SQLException | InstantiationException | 
					IllegalAccessException | ClassNotFoundException e) {
			logger.fatal("Could not connect to DataBase: " + e);
			throw new RuntimeException("Could not connect to DataBase: " + e);
		} catch (IOException e) {
			logger.fatal("Error loading properties file: " + e);
			throw new RuntimeException("Error loading properties file: " + e);
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
		try {
			 Enumeration<Driver> drivers = DriverManager.getDrivers();
			 while (drivers.hasMoreElements()) {
				 Driver driver = drivers.nextElement();
				 DriverManager.deregisterDriver(driver);
			 }
		 } catch (SQLException e) {
			 logger.error("DriverManager wasn't found." + e);
		 }
	}
	
	

}
