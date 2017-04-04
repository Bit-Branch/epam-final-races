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

public enum Command {
	REGISTER(new RegisterReceiver()),
	LOGIN(new LoginReceiver()),
	SCHEDULE(new ScheduleReceiver()),
	RESULTS(new ResultsReceiver()),
	GENERATE_RACES(new GenerateRacesReceiver()),
	START_RACES(new StartRacesReceiver()),
	PLACE_BET(new PlaceBetReceiver()),
	DELETE_PROFILE(new DeleteProfileReceiver()),
	ALL_BETS(new AllBetsReceiver()),
	CANCEL_BET(new CancelBetReceiver()),
	CANCEL_RACE(new CancelRaceReceiver());
	
	private Lock lock = new ReentrantLock();
	private CommandReceiver receiver;
	
	Command(CommandReceiver receiver) {
		this.receiver = receiver;
	}
	
	public Entity execute(Entity entity) {
		lock.lock();
		try {
			return receiver.act(entity);
		} finally {
			lock.unlock();
		}
	}
}
