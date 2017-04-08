package by.malinouski.horserace.command.receiver;

import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.EntityContainer;

public abstract class CommandReceiver <T extends Entity>{
	
	public abstract EntityContainer<? extends Entity> act(T entity);

}
