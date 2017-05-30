package test.by.malinouski.hrace.connection;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.malinouski.hrace.connection.ConnectionPool;

public class ConnectionPoolTest {
	
	ConnectionPool pool;
	InputStream is;
	
	@Before
	public void setUp() {
		pool = ConnectionPool.getInstance();
		is = getClass().getClassLoader()
				.getResourceAsStream("resources/dbinfo/dbinfo.properties");
	}
	
	@After
	public void tearDown() throws IOException {
		pool.close();
		is.close();
	}
	
	@Test
	public void getConnectionTestOverPoolSize() throws IOException, InterruptedException {
			
		Properties prop = new Properties();
		prop.load(is);
		
		int poolSize = Integer.valueOf(prop.getProperty("poolSize"));
		List<Connection> conns = new ArrayList<>();

		for (int i = 0; i < poolSize; i++) {
			conns.add(pool.getConnection());
		}

		Thread thread = new Thread(() -> pool.getConnection());
		thread.start();
		thread.join(2000);
		assertTrue(thread.isAlive());
		
		thread.interrupt();
	}
}
