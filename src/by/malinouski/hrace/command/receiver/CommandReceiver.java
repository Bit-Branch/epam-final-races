package by.malinouski.hrace.command.receiver;

import by.malinouski.hrace.logic.entity.Entity;

/**
 * The Class CommandReceiver.
 */
public abstract class CommandReceiver {
	
	public abstract Entity act(Entity entity);

}
