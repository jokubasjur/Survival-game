package Bridge;

public class BulletNormalMode extends BulletModeInplementor{
	
	public BulletNormalMode() {
		name = "Normal";
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
		return 1;
	}
}
