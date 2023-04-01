package Decorator;

public class DefaultWeapon implements Weapon {

	@Override
	public String getDesc() {
		
		return "Basic handgun";
	}

	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public long getSpeed() {
		return 0;
	}

	@Override
	public int getMoveSpeed() {
		return 1;
	}
	
	
}
