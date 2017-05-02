package by.malinouski.hrace.command.receiver;

import by.malinouski.hrace.logic.entity.Entity;

public abstract class CommandReceiver {
	
	public abstract <T extends Entity> Entity act(T entity);

}
