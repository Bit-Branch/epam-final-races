package test.by.malinouski.horserace.connection;


import org.junit.AfterClass;
import org.junit.Test;

import by.malinouski.hrace.connection.ConnectionPool;

public class ConnectionPoolTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ConnectionPool.getInstance().close();
	}

	@Test
	public void test() {
		ConnectionPool.getInstance(); 
	}

}
