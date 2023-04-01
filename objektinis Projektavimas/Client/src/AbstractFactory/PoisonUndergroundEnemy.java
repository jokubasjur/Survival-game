package AbstractFactory;

import java.awt.Color;

import edu.ktu.signalrclient.Player;

public class PoisonUndergroundEnemy extends UndergroundEnemy {
	
	public PoisonUndergroundEnemy() {
		super();
		setColor(new Color(147,112,219,100));
		setRad(Math.toRadians(90));
		setDx(Math.cos(getRad()));
		setDy(Math.sin(getRad()));
	}
	
	@Override
	public void damagePlayer(Player player) {
		player.Poisoned(3);
		super.damagePlayer(player);
	}
}
