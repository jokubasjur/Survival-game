package Decorator;

public class DamageEnhancement extends EnhancementDecorator {

	public DamageEnhancement(Weapon weapon) {
		super(weapon);
		System.out.println("Added damage enchantment");

	}
	
	public String getDesc() {
		
		return tempWeapon.getDesc() + ", with increased damage ";
	}

	public int getDamage() {
		return tempWeapon.getDamage() + 1;
	}

	public long getSpeed() {
		return tempWeapon.getSpeed();
	}

	@Override
	public int getMoveSpeed() {
		return tempWeapon.getMoveSpeed();
	}

}
