package Decorator;

public class SpeedEnhancement extends EnhancementDecorator {

	public SpeedEnhancement(Weapon weapon) {
		super(weapon);
		System.out.println("Added speed enchantment");
	}
	
	
	public String getDesc() {
		
		return tempWeapon.getDesc() + ", with increased speed ";
	}

	public int getDamage() {
		return tempWeapon.getDamage();
	}

	public long getSpeed() {
		return tempWeapon.getSpeed() - 50;
	}


	@Override
	public int getMoveSpeed() {
		return tempWeapon.getMoveSpeed();
	}
	
	
}
