package Bridge;

import java.awt.Color;
import java.awt.Graphics;

import edu.ktu.signalrclient.Game;

public abstract class BulletTypeAbstraction {
	protected int x;
	protected int y;
	protected int r;


	protected double dx;
	protected double dy;
	protected double rad;
	protected int speed;
	protected int damage;

	protected Color color;
	
	protected BulletModeInplementor mode;
	
	public int dealDamage(String enemyType) {
		int dealingDamage = 0;
		
		switch (enemyType) {											
			case "ground":
				dealingDamage = damage * mode.dealDamageGround();
				break;
			case "underground":
				dealingDamage = damage * mode.dealDamageUnderground();
				break;
			case "flying":
				dealingDamage = damage * mode.dealDamageFlying();
				break;
			default:
				throw new IllegalArgumentException("EnemyType is incorrect in dealDamage(String enemyType)");
		}
		
		return dealingDamage;
	}
	
	public boolean update() {
		x += dx * speed;
		y += dy * speed;
		
		if (x < -r || x > Game.windowWidth + r || y < -r || y > Game.windowHeight + r) {
			return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (x-r), (int) (y-r), 2*r, 2*r);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("Coordinate x is negative in setX(int x)");
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if (y < 0) {
			throw new IllegalArgumentException("Coordinate y is negative in setY(int y)");
		}
		this.y = y;
	}
	
	public int getR() {
		return r;
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		if (damage <= 0) {
			throw new IllegalArgumentException("Damage is negative in setDamage(int damage)");
		}
		this.damage = damage;
	}

	public void setR(int r) {
		if (r < 0) {
			throw new IllegalArgumentException("Parameter r is negative in setR(int r)");
		}
		this.r = r;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public BulletModeInplementor getMode() {
		return mode;
	}
}
