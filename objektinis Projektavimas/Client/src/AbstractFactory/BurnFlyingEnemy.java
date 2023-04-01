package AbstractFactory;

import java.awt.Color;

import edu.ktu.signalrclient.Player;

public class BurnFlyingEnemy extends FlyingEnemy {
	
	public BurnFlyingEnemy() {
		super();
		setColor(new Color(226, 56, 34));
		setRad(Math.toRadians(120));
		setDx(Math.cos(getRad()));
		setDy(Math.sin(getRad()));
	}
	
	@Override
	public void damagePlayer(Player player) {
		player.Burning(1);
		super.damagePlayer(player);
	}
}
