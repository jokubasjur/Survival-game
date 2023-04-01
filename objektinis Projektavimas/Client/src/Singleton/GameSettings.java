package Singleton;

public class GameSettings {
	private static GameSettings instance = new GameSettings();
	
	public int actionExecutionSpeed;
	
	private GameSettings() { 
		this.actionExecutionSpeed = 5;
	}
	
	public static GameSettings getInstance() {
		return instance; 
	}
}
