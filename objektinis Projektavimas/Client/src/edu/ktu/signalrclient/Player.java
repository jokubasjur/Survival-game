package edu.ktu.signalrclient;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.microsoft.signalr.HubConnection;

import Builder.DragonSlayer;
import Builder.DragonSlayerBuilder;
import Builder.Hero;
import Builder.HeroPortal;
import Builder.PlagueDoctorBuilder;
import Decorator.DefaultWeapon;
import Decorator.Weapon;
import Strategy.Diagonal;
import Strategy.FrontBullet;
import Strategy.IShootStrategy;
import Strategy.Machinegun;
import Strategy.Multishot;
import Strategy.NormalShot;

public class Player implements Cloneable{
	private int x, y;
	private int velX, velY;
	private int healthPoints;
	private int points;
	private int size;
	private boolean shooting;
	private long shootingTimer;
	private long shootingDelay;
	private boolean recovering;
	private long recoveryTimer;
	private long burntTimer;
	private long poisonedTimer;
	private long strategyTimer;
	
	private String bulletType;												//bridge
	
	private Hero selectedHero;
	
	private Weapon defaultWeapon;


	private Color teamColor;
	private String nickname;
	
	private boolean isReady;
	private boolean isPoisoned;
	private boolean isBurning;
	private int poisonTick;
	private int burnTick;
	
	private IShootStrategy shootStrategy;
	
	private int goldenBulletDamage;									//visitor
	private int normalBulletDamage;

	public Player() {}
	
	public Player(IShootStrategy shootStrategy) {
		
		defaultWeapon =  new DefaultWeapon(); // decorator
		
		this.shootStrategy = shootStrategy;
		x = Game.windowWidth/2 - 15;
		y = Game.windowHeight/2 - 15;
		velX = 0;
		velY = 0;
		size = 30;
		nickname = "";
		selectedHero = new HeroPortal().constructHero(new DragonSlayerBuilder());
		float hp = selectedHero.getHealth();
		float hpPrecentage = hp / 100;
		float hpFromHero = 100 * hpPrecentage;
		healthPoints = (int) hpFromHero;
		points = 0;
		shootingTimer = System.nanoTime();
		shootingDelay = 200;
		
		
		
		recovering = false;
		recoveryTimer = 0;
		burntTimer = 0;
		poisonedTimer = 0;
		strategyTimer = 0;
		
		teamColor = selectedHero.getArmor();
		
		isReady = false;
		shooting = false;
		isPoisoned = false;
		isBurning = false;
		poisonTick = 0;
		burnTick = 0;
		
		setBulletType("normal");                                                               //bridge
		goldenBulletDamage = 10;
		normalBulletDamage = 5;
	}
	
	public Player getClone() {
		try {
			return (Player) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this;
		}
	}
	
	public void loseLife(int damage) {
		float res = selectedHero.getDefense();
		float resPrecentage = res / 100;
		float resModifier = 1 - resPrecentage;
		float damageToReceive = damage * resModifier;
		healthPoints -= damageToReceive;
		recovering = true;
		recoveryTimer = System.nanoTime();
		
		
		//STRATEGY
		if (getRandomBoolean() && (healthPoints <= 100 && healthPoints > 70) && !isBurning && !isPoisoned) {
			Random random = new Random();
			int x = random.nextInt(2);
			
			if (x == 0) {
				setShootStrategy(new Diagonal());
				Game.UpdateShootingStrategy("DIAGONAL");
			} else if (x == 1) {
				setShootStrategy(new FrontBullet());
				Game.UpdateShootingStrategy("FRONT");
			}
			strategyTimer = System.nanoTime();
		} else if (healthPoints <= 70 && healthPoints > 50) {
			setShootStrategy(new Multishot());
			Game.UpdateShootingStrategy("MULTI");
		} else if (healthPoints <= 50) {
			setShootStrategy(new Machinegun());
			Game.UpdateShootingStrategy("MACHINE");
		}
	}
	
    private static boolean getRandomBoolean() {
        return Math.random() < 0.8;
    }
	
	public void Burning(int damage) {
		isBurning = true;
		burntTimer = System.nanoTime();
	}
	
	public void Poisoned(int damage) {
		isPoisoned = true;
		poisonedTimer = System.nanoTime();
	}
	
	public void update(String playerToUpdate) {
		if (x + velX <= 0) {
			this.x = 0;
		} else if ((x + velX) >= Game.windowWidth - size - (size / 2)) {
			this.x = Game.windowWidth - (size + (size / 2));
		} else {
			this.x = x + velX;
		}
		
		if (y + velY <= 0) {
			this.y = 0;
		} else if ((y + velY) >= Game.windowHeight - (2 * size) - 10) {
			this.y = Game.windowHeight - (size * 2) - 10;
		} else {
			this.y = y + velY;
		}
		
		if (shooting) {
			long elapsed = (System.nanoTime() - shootingTimer) / 1000000;
			shootStrategy.Shoot(this, elapsed, playerToUpdate);
		}
		
		long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
		if(elapsed > 2000) {
			recovering = false;
			recoveryTimer = 0;
		}
		long elapsedStrategy = (System.nanoTime() - strategyTimer) / 1000000;
		if (elapsedStrategy > 5000) {
			strategyTimer = 0;
			setShootStrategy(new NormalShot());
			Game.UpdateShootingStrategy("NORMAL");
		}
		
		if (isBurning) {
			long elapsedBurn = (System.nanoTime() - burntTimer) / 1000000;
			if (elapsedBurn > 3000 && burnTick < 2) {
				float res = selectedHero.getBurningResistance();
				float resPrecentage = res / 100;
				float resModifier = 1 - resPrecentage;
				float damageToReceive = 5 * resModifier;
				healthPoints -= damageToReceive;
				System.out.println("I'M BURNING, DMG: " + damageToReceive);
				burnTick++;
				burntTimer = System.nanoTime();
			} else if (burnTick >= 3) {
				burntTimer = 0;
				isBurning = false;
				burnTick = 0;
			}
		}
		
		if (isPoisoned) {
			long elapsedPoison = (System.nanoTime() - poisonedTimer) / 1000000;
			if (elapsedPoison > 3000 && poisonTick < 3) {
				float res = selectedHero.getPoisonResistance();
				float resPrecentage = res / 100;
				float resModifier = 1 - resPrecentage;
				float damageToReceive = 5 * resModifier;
				healthPoints -= damageToReceive;
				System.out.println("I'M POISONED, DMG: " + damageToReceive);
				poisonTick++;
				poisonedTimer = System.nanoTime();
			} else if (burnTick >= 3) {
				poisonedTimer = 0;
				isPoisoned = false;
				poisonTick = 0;
			}
		}
		

	}
	
	public void render(Graphics g) {
		g.setColor(teamColor);
		g.fillRect(x, y, size, size);
	}
	
	public long getShootingTimer() {
		return shootingTimer;
	}

	public void setShootingTimer(long shootingTimer) {
		this.shootingTimer = shootingTimer;
	}

	public long getShootingDelay() {
		return shootingDelay;
	}

	public void setShootingDelay(long shootingDelay) {
		this.shootingDelay = shootingDelay;
	}

	public long getBurntTimer() {
		return burntTimer;
	}

	public void setBurntTimer(long burntTimer) {
		this.burntTimer = burntTimer;
	}

	public long getPoisonedTimer() {
		return poisonedTimer;
	}

	public void setPoisonedTimer(long poisonedTimer) {
		this.poisonedTimer = poisonedTimer;
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}

	public void setPoisoned(boolean isPoisoned) {
		this.isPoisoned = isPoisoned;
	}

	public boolean isBurning() {
		return isBurning;
	}

	public void setBurning(boolean isBurning) {
		this.isBurning = isBurning;
	}

	public int getPoisonTick() {
		return poisonTick;
	}

	public void setPoisonTick(int poisonTick) {
		this.poisonTick = poisonTick;
	}

	public int getBurnTick() {
		return burnTick;
	}

	public void setBurnTick(int burnTick) {
		this.burnTick = burnTick;
	}

	public IShootStrategy getShootStrategy() {
		return shootStrategy;
	}

	public void setShootStrategy(IShootStrategy shootStrategy) {
		this.shootStrategy = shootStrategy;
	}
	
	public boolean isRecovering() {
		return recovering;
	}

	public void setRecovering(boolean recovering) {
		this.recovering = recovering;
	}

	public long getRecoveryTimer() {
		return recoveryTimer;
	}

	public void setRecoveryTimer(long recoveryTimer) {
		this.recoveryTimer = recoveryTimer;
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

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Color getTeamColor() {
		return teamColor;
	}

	public void setTeamColor(Color teamColor) {
		this.teamColor = teamColor;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getHealthPoints() {
		return healthPoints;
	}
	
	public void initHeroHealthPoints() {
		float hp = selectedHero.getHealth();
		float hpPrecentage = hp / 100;
		float hpFromHero = 100 * hpPrecentage;
		this.healthPoints = (int) hpFromHero;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	public Hero getSelectedHero() {
		return selectedHero;
	}

	public void setSelectedHero(Hero selectedHero) {
		this.selectedHero = selectedHero;
		System.out.println(selectedHero.toString());
	}
	
	public Weapon getDefaultWeapon() {
		return defaultWeapon;
	}

	public void setDefaultWeapon(Weapon defaultWeapon) {
		this.defaultWeapon = defaultWeapon;
	}

	public String getBulletType() {
		return bulletType;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setBulletType(String bulletType) {
		this.bulletType = bulletType;
	}

	public int getGoldenBulletDamage() {
		return goldenBulletDamage;
	}

	public void setGoldenBulletDamage(int goldenBulletDamage) {
		this.goldenBulletDamage = goldenBulletDamage;
	}

	public int getNormalBulletDamage() {
		return normalBulletDamage;
	}

	public void setNormalBulletDamage(int normalBulletDamage) {
		this.normalBulletDamage = normalBulletDamage;
	}
}