package Visitor;

import edu.ktu.signalrclient.Player;

public class PlayerHealthImprovement implements ImprovementVisitor{
	
	private Player player;
	
	public PlayerHealthImprovement(Player player) {
		this.player = player;
	}

	@Override
	public void activateLowBoost(LowBoost b) {
		int currentHealth = player.getHealthPoints();
		player.setHealthPoints(currentHealth + 5);
	}

	@Override
	public void activateMidBoost(MidBoost b) {
		int currentHealth = player.getHealthPoints();
		player.setHealthPoints(currentHealth + 10);
	}

	@Override
	public void activateHighBoost(HighBoost b) {
		int currentHealth = player.getHealthPoints();
		player.setHealthPoints(currentHealth + 20);
	}

}
