package by.malinouski.hrace.command;

import by.malinouski.hrace.command.receiver.AddBalanceReceiver;
import by.malinouski.hrace.command.receiver.AllBetsReceiver;
import by.malinouski.hrace.command.receiver.CancelAllRacesReceiver;
import by.malinouski.hrace.command.receiver.CancelBetReceiver;
import by.malinouski.hrace.command.receiver.CancelRaceReceiver;
import by.malinouski.hrace.command.receiver.CommandReceiver;
import by.malinouski.hrace.command.receiver.DeleteProfileReceiver;
import by.malinouski.hrace.command.receiver.GenerateRacesReceiver;
import by.malinouski.hrace.command.receiver.LoginReceiver;
import by.malinouski.hrace.command.receiver.PlaceBetReceiver;
import by.malinouski.hrace.command.receiver.RegisterReceiver;
import by.malinouski.hrace.command.receiver.ResultsReceiver;
import by.malinouski.hrace.command.receiver.ScheduleReceiver;
import by.malinouski.hrace.command.receiver.StartRacesReceiver;
import by.malinouski.hrace.command.receiver.UpdatePasswordReceiver;
import by.malinouski.hrace.logic.entity.Entity;

public enum Command {
	REGISTER (new RegisterReceiver()),
	LOGIN (new LoginReceiver()),
	SCHEDULE (new ScheduleReceiver()),
	RESULTS (new ResultsReceiver()),
	GENERATE_RACES (new GenerateRacesReceiver()),
	START_RACES (new StartRacesReceiver()),
	PLACE_BET (new PlaceBetReceiver()),
	DELETE_PROFILE (new DeleteProfileReceiver()),
	UPDATE_PASSWORD (new UpdatePasswordReceiver()),
	ALL_BETS (new AllBetsReceiver()),
	CANCEL_BET (new CancelBetReceiver()),
	CANCEL_RACE (new CancelRaceReceiver()),
	CANCEL_ALL_RACES(new CancelAllRacesReceiver()),
	ADD_BALANCE(new AddBalanceReceiver());
	
	
	private CommandReceiver receiver;
	
	Command(CommandReceiver receiver) {
		this.receiver = receiver;
	}
	
	public Entity execute(Entity entity) {	
		return receiver.act(entity);
	}
}
