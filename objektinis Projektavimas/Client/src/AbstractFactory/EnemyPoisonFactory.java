package AbstractFactory;

public class EnemyPoisonFactory extends EnemyAbstractFactory {
	
	@Override
	public GroundEnemy createGroundEnemy() {
		// TODO Auto-generated method stub
		return new PoisonGroundEnemy();
	}

	@Override
	public UndergroundEnemy createUndergroundEnemy() {
		// TODO Auto-generated method stub
		return new PoisonUndergroundEnemy();
	}

	@Override
	public FlyingEnemy createFlyingEnemy() {
		// TODO Auto-generated method stub
		return new PoisonFlyingEnemy();
	}

}
