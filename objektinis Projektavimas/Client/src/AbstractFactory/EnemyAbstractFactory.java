package AbstractFactory;

public abstract class EnemyAbstractFactory {
	public abstract GroundEnemy createGroundEnemy();
	
	public abstract UndergroundEnemy createUndergroundEnemy();
	
	public abstract FlyingEnemy createFlyingEnemy();
	
	public Enemy createEnemy(String enemyType) {
		
		Enemy enemy = null;
		if (enemyType == "GROUND") {
			enemy = createGroundEnemy();
		} else if (enemyType == "UNDERGROUND") {
			enemy = createUndergroundEnemy();
		} else if (enemyType == "FLYING") {
			enemy = createFlyingEnemy();
		}
		
		return enemy;
	}
}
