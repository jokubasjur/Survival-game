package AbstractFactory;

import edu.ktu.signalrclient.Game;

public class FlyingEnemy extends Enemy {
	
	
	public FlyingEnemy() {
		super();
		
		setSpeed(6);
		setR(12);
		setHealth(10);
		setPointsValue(10);
		setDamage(5);
		setX((int) (Game.windowWidth / 2));
		setY(-getR());
		setType("flying");
		double angle = 140;
		setRad(Math.toRadians(angle));
		
		setDx(Math.cos(getRad()));
		setDy(Math.sin(getRad()));
		
		setReady(false);
		setDead(false);
	}
}
