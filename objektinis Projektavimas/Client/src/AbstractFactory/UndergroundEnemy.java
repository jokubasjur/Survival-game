package AbstractFactory;

import java.awt.Color;

import edu.ktu.signalrclient.Game;

public class UndergroundEnemy extends Enemy {
	
	public UndergroundEnemy() {
		super();
		
		setSpeed(2);
		setR(30);
		setHealth(40);
		setDamage(1);
		setPointsValue(20);
		setType("underground");
		
		setX((int) (Game.windowWidth / 2));
		setY(-getR());
		
		double angle = 140;
		setRad(Math.toRadians(angle));
		
		setDx(Math.cos(getRad()));
		setDy(Math.sin(getRad()));
		Color underGround = new Color(Color.gray.getRed(), Color.gray.getGreen(), Color.gray.getBlue(), 100);
		setColor(underGround);
		setReady(false);
		setDead(false);
	}
}
