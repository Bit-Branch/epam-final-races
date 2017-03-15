package test.by.malinouski.horserace.connection;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import by.malinouski.horserace.connection.ConnectionPool;

public class ConnectionPoolTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ConnectionPool.getConnectionPool().close();
	}

	@Test
	public void test() {
		ConnectionPool.getConnectionPool(); 
	}

}
