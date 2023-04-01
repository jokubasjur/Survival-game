package Visitor;

import edu.ktu.signalrclient.Player;

public class GoldenBulletDamageImprovement implements ImprovementVisitor{

	private Player player;
	
	public GoldenBulletDamageImprovement(Player player) {
		this.player = player;
	}

	@Override
	public void activateLowBoost(LowBoost b) {
		int currentGoldenBulletDamage = player.getGoldenBulletDamage();
		player.setHealthPoints(currentGoldenBulletDamage + 1);
	}

	@Override
	public void activateMidBoost(MidBoost b) {
		int currentGoldenBulletDamage = player.getGoldenBulletDamage();
		player.setHealthPoints(currentGoldenBulletDamage + 2);
	}

	@Override
	public void activateHighBoost(HighBoost b) {
		int currentGoldenBulletDamage = player.getGoldenBulletDamage();
		player.setHealthPoints(currentGoldenBulletDamage + 3);
	}
}
