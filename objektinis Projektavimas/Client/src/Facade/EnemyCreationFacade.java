package Facade;

import AbstractFactory.*;

public class EnemyCreationFacade {
	
	private static EnemyAbstractFactory factory;

	public static Enemy CreatePoisonGroundEnemy() {
		factory = new EnemyPoisonFactory();
		return factory.createEnemy("GROUND");
	}
	
	public static Enemy CreatePoisonFlyingEnemy() {
		factory = new EnemyPoisonFactory();
		return factory.createEnemy("FLYING");
	}
	
	public static Enemy CreatePoisonUndergroundEnemy() {
		factory = new EnemyPoisonFactory();
		return factory.createEnemy("UNDERGROUND");
	}
	
	public static Enemy CreateBurnGroundEnemy() {
		factory = new EnemyBurnFactory();
		return factory.createEnemy("GROUND");
	}
	
	public static Enemy CreateBurnFlyingEnemy() {
		factory = new EnemyBurnFactory();
		return factory.createEnemy("FLYING");
	}
	
	public static Enemy CreateBurnUndergroundEnemy() {
		factory = new EnemyBurnFactory();
		return factory.createEnemy("UNDERGROUND");
	}
	
	public static Enemy CreateNormalGroundEnemy() {
		factory = new EnemyNormalFactory();
		return factory.createEnemy("GROUND");
	}
	
	public static Enemy CreateNormalFlyingEnemy() {
		factory = new EnemyNormalFactory();
		return factory.createEnemy("FLYING");
	}
	
	public static Enemy CreateNormalUndergroundEnemy() {
		factory = new EnemyNormalFactory();
		return factory.createEnemy("UNDERGROUND");
	}
}
