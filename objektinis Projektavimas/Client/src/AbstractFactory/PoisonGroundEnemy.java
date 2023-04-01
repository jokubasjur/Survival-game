package AbstractFactory;

import java.awt.Color;

import edu.ktu.signalrclient.Player;

public class PoisonGroundEnemy extends GroundEnemy {
	public PoisonGroundEnemy() {
		super();
		setColor(new Color(147,112,219));
		setRad(Math.toRadians(90));
		setDx(Math.cos(getRad()));
		setDy(Math.sin(getRad()));
	}
	
	@Override
	public void damagePlayer(Player player) {
		player.Poisoned(2);
		super.damagePlayer(player);
	}
}
