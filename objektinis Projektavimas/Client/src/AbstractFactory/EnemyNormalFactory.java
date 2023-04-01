package AbstractFactory;

public class EnemyNormalFactory extends EnemyAbstractFactory {

	@Override
	public GroundEnemy createGroundEnemy() {
		// TODO Auto-generated method stub
		return new NormalGroundEnemy();
	}

	@Override
	public UndergroundEnemy createUndergroundEnemy() {
		// TODO Auto-generated method stub
		return new NormalUndergroundEnemy();
	}

	@Override
	public FlyingEnemy createFlyingEnemy() {
		// TODO Auto-generated method stub
		return new NormalFlyingEnemy();
	}
	
}
