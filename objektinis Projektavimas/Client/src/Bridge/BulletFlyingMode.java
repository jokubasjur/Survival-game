package Bridge;

public class BulletFlyingMode extends BulletModeInplementor{
	
	public BulletFlyingMode() {
		name = "Flying";
	}
	
	@Override
	public int dealDamageGround() {
		return 1;
	}

	@Override
	public int dealDamageFlying() {
		return 2;
	}

	@Override
	public int dealDamageUnderground() {
		return 1;
	}
}
