package by.malinouski.horserace.command;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.malinouski.horserace.command.receiver.AllBetsReceiver;
import by.malinouski.horserace.command.receiver.CancelBetReceiver;
import by.malinouski.horserace.command.receiver.CancelRaceReceiver;
import by.malinouski.horserace.command.receiver.CommandReceiver;
import by.malinouski.horserace.command.receiver.DeleteProfileReceiver;
import by.malinouski.horserace.command.receiver.GenerateRacesReceiver;
import by.malinouski.horserace.command.receiver.LoginReceiver;
import by.malinouski.horserace.command.receiver.PlaceBetReceiver;
import by.malinouski.horserace.command.receiver.RegisterReceiver;
import by.malinouski.horserace.command.receiver.ResultsReceiver;
import by.malinouski.horserace.command.receiver.ScheduleReceiver;
import by.malinouski.horserace.command.receiver.StartRacesReceiver;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.EntityContainer;

public enum Command {
	REGISTER (new RegisterReceiver()),
	LOGIN (new LoginReceiver()),
	SCHEDULE (new ScheduleReceiver()),
	RESULTS (new ResultsReceiver()),
	GENERATE_RACES (new GenerateRacesReceiver()),
	START_RACES (new StartRacesReceiver()),
	PLACE_BET (new PlaceBetReceiver()),
	DELETE_PROFILE (new DeleteProfileReceiver()),
	ALL_BETS (new AllBetsReceiver()),
	CANCEL_BET (new CancelBetReceiver()),
	CANCEL_RACE (new CancelRaceReceiver());
	
	
	private CommandReceiver<? extends Entity> receiver;
	
	Command(CommandReceiver<? extends Entity> receiver) {
		this.receiver = receiver;
	}
	
	public EntityContainer<? extends Entity> execute(Entity entity) {	
		return receiver.act(entity);
	}
}
