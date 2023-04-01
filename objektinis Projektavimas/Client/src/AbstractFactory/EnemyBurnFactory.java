package AbstractFactory;

public class EnemyBurnFactory extends EnemyAbstractFactory {

	@Override
	public GroundEnemy createGroundEnemy() {
		// TODO Auto-generated method stub
		return new BurnGroundEnemy();
	}

	@Override
	public UndergroundEnemy createUndergroundEnemy() {
		// TODO Auto-generated method stub
		return new BurnUndergroundEnemy();
	}

	@Override
	public FlyingEnemy createFlyingEnemy() {
		// TODO Auto-generated method stub
		return new BurnFlyingEnemy();
	}
	
}
