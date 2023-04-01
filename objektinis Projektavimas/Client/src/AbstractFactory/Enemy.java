package AbstractFactory;

import java.awt.Color;
import java.awt.Graphics;

import edu.ktu.signalrclient.Game;
import edu.ktu.signalrclient.Player;

public abstract class Enemy {
	private int x;
	private int y;

	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	protected int health;   //padariau protected, kad si lauka matytu Boss klase
	private int pointsValue;
	private String type;	//bridge
	private int rank;
	
	private Color color;
	
	private boolean ready;
	protected boolean dead;	//padariau protected, kad si lauka matytu Boss klase
	private boolean deadUpdated;
	
	private String name;

	private int damage;
	
	/* Strategy for Enemy
	private long defendTimer;
	private long defendTime;
	private long defendAgainTime;
	private boolean defended;
	private double previousSpeed;
	*/
	
	public Enemy() {
		
		name = "";
		
		speed = 2;
		r = 12;
		health = 1;
		pointsValue = 10;
		damage = 1;
		
		x = (int) (Game.windowWidth / 2);
		y = -r;
		
		double angle = 70;
		rad = Math.toRadians(angle);
		
		dx = Math.cos(rad);
		dy = Math.sin(rad);
		
		color = Color.GRAY;
		ready = false;
		dead = false;
		deadUpdated = false;
		
		/* Strategy for Enemy.
		defendTimer = 0;
		defendTime = 200;
		defendAgainTime = 1000;
		defended = false;
		previousSpeed = speed;
		*/
	}
	
	public void hit(int damage) {
		health -= damage;
		if (health <= 0) {
			dead = true;
		}
	}
	
	public void damagePlayer(Player player) {
		player.loseLife(damage);
	}
	
	/* Strategy for Enemy
	public void Defend() {
		defendTimer = System.nanoTime();
	}*/
	
	public void update() {
		
		x += dx * speed;
		y += dy * speed;
		
		/* Strategy for Enemy
		long elapsed = (System.nanoTime() - defendTimer) / 1000000;
		if (elapsed < defendTime && !defended) {
			previousSpeed = speed;
			speed = speed * 2.5f;
			defended = true;
		} else if (defended && elapsed > defendTime){
			speed = previousSpeed;
			if (elapsed > defendAgainTime) {
				defendTimer = 0;
				defended = false;
			}
		}*/
		
		if(!ready) {
			if (x > r && x < Game.windowWidth - r && y > r && y < Game.windowHeight - r) {
				ready = true;
			}
		}
		
		if (x < r && dx < 0) {
			dx = -dx;
		}
		if (y < r && dy < 0) {
			dy = -dy;
		}
		if (x > Game.windowWidth -r && dx > 0) {
			dx = -dx;
		}
		if (y > Game.windowHeight - r - 10 && dy > 0) {
			dy = -dy;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (x -r), (int)(y-r), 2*r, 2*r);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}


	public double getRad() {
		return rad;
	}

	public void setRad(double rad) {
		this.rad = rad;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getType() {										//bridge
		return type;
	}

	public void setType(String type) {								//bridge
		this.type = type;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isDeadUpdated() {
		return deadUpdated;
	}

	public void setDeadUpdated(boolean deadUpdated) {
		this.deadUpdated = deadUpdated;
	}

	public int getPointsValue() {
		return pointsValue;
	}

	public void setPointsValue(int pointsValue) {
		this.pointsValue = pointsValue;
	}
}
