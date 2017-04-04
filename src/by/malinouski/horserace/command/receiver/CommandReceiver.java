package by.malinouski.horserace.command.receiver;

import by.malinouski.horserace.logic.entity.Entity;

public abstract class CommandReceiver {
	
	public abstract Entity act(Entity entity);

}
