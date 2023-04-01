package AbstractFactory;


import edu.ktu.signalrclient.Game;

public class GroundEnemy extends Enemy {

	public GroundEnemy() {
		super();
		setSpeed(3);
		setR(20);
		setHealth(20);
		setPointsValue(15);
		setDamage(3);
		setType("ground");
		
		setX((int) (Game.windowWidth / 2));
		setY(-getR());
		
		double angle = 140;
		setRad(Math.toRadians(angle));
		
		setDx(Math.cos(getRad()));
		setDy(Math.sin(getRad()));
		
		setReady(false);
		setDead(false);
	}

}
