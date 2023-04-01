package Bridge;

public abstract class BulletModeInplementor {
	protected String name;
	
	public abstract int dealDamageGround();
	public abstract int dealDamageFlying();
	public abstract int dealDamageUnderground();
	
	public String getName() {
		return name;
	}
	
}