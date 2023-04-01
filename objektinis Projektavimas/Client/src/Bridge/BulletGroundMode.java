package Bridge;

public class BulletGroundMode extends BulletModeInplementor{
	
	public BulletGroundMode() {
		name = "Ground";
	}
	
	@Override
	public int dealDamageGround() {
		return 2;
	}

	@Override
	public int dealDamageFlying() {
		return 1;
	}

	@Override
	public int dealDamageUnderground() {
		return 1;
	}
}
