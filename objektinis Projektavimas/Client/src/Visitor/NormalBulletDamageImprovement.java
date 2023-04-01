package Visitor;

import edu.ktu.signalrclient.Player;

public class NormalBulletDamageImprovement implements ImprovementVisitor{

private Player player;
	
	public NormalBulletDamageImprovement(Player player) {
		this.player = player;
	}

	@Override
	public void activateLowBoost(LowBoost b) {
		int currentNormalBulletDamage = player.getNormalBulletDamage();
		player.setHealthPoints(currentNormalBulletDamage + 1);
	}

	@Override
	public void activateMidBoost(MidBoost b) {
		int currentNormalBulletDamage = player.getNormalBulletDamage();
		player.setHealthPoints(currentNormalBulletDamage + 2);
	}

	@Override
	public void activateHighBoost(HighBoost b) {
		int currentNormalBulletDamage = player.getNormalBulletDamage();
		player.setHealthPoints(currentNormalBulletDamage + 3);
	}
}
