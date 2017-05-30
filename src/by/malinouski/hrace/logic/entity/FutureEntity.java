/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.entity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author makarymalinouski
 *
 */
public class FutureEntity<T extends Entity> implements Entity, Future<T> {
	
	/**
	 * May 27, 2017 3:42 PM UTC
	 */
	private static final long serialVersionUID = 6232271862674283102L;
	private Future<T> entity;
	
	/**
	 * Instantiates a new future entity.
	 *
	 * @param futureEntity the future entity
	 */
	public FutureEntity(Future<T> futureEntity) {
		entity = futureEntity;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return entity.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return entity.isCancelled();
	}

	@Override
	public boolean isDone() {
		return entity.isDone();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		return entity.get();
	}

	@Override
	public T get(long timeout, TimeUnit unit) 
			throws InterruptedException, ExecutionException, TimeoutException {
		return entity.get(timeout, unit);
	}

	@Override
	public EntityType ofType() {
		return EntityType.FUTURE_ENTITY;
	}
	
}
