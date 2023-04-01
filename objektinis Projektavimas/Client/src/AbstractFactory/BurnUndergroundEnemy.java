package AbstractFactory;

import java.awt.Color;

import edu.ktu.signalrclient.Player;

public class BurnUndergroundEnemy extends UndergroundEnemy {
	
	public BurnUndergroundEnemy() {
		super();
		setColor(new Color(226, 56, 34, 100));
		setRad(Math.toRadians(120));
		setDx(Math.cos(getRad()));
		setDy(Math.sin(getRad()));
	}
	
	@Override
	public void damagePlayer(Player player) {
		player.Burning(3);
		super.damagePlayer(player);
	}
}
