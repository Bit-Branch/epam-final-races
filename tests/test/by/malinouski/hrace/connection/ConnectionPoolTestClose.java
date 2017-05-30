package test.by.malinouski.hrace.connection;


import org.junit.Test;

import by.malinouski.hrace.connection.ConnectionPool;
import by.malinouski.hrace.exception.PoolIsClosedException;

public class ConnectionPoolTestClose {

	@Test(expected = PoolIsClosedException.class)
	public void getInstanceTestClosed() throws InterruptedException {

		ConnectionPool pool = ConnectionPool.getInstance();
		pool.close();
		ConnectionPool.getInstance();

	}

}
