package by.malinouski.hrace.logic.entity;

public enum EntityType {
	BET("Bet"), 
	HORSE("Horse"), 
	HORSE_UNIT("HorseUnit"), 
	MESSAGE("Message"), 
	ODDS("Odds"), 
	RACE("Race"), 
	RACES_SERIES("RacesSeries"), 
	USER("User"),
	ENTITY_CONTAINER("EntityContainer"), 
	FUTURE_ENTITY("FutureEntity"),
	NONE("None");
	
	private String value;
	
	EntityType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
