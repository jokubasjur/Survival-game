package Bridge;

public class BulletUndergroundMode extends BulletModeInplementor{
	
	public BulletUndergroundMode() {
		name = "Underground";
	}
	
	@Override
	public int dealDamageGround() {
		return 1;
	}

	@Override
	public int dealDamageFlying() {
		return 1;
	}

	@Override
	public int dealDamageUnderground() {
		return 2;
	}
}
