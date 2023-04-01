package Decorator;

public class MoveSpeedEnhancement extends EnhancementDecorator {

	public MoveSpeedEnhancement(Weapon weapon) {
		super(weapon);
		System.out.println("Added movement speed enchantment");
	}
	
	
	public String getDesc() {
		
		return tempWeapon.getDesc() + ", with increased movement speed ";
	}

	public int getDamage() {
		return tempWeapon.getDamage();
	}

	public long getSpeed() {
		return tempWeapon.getSpeed();
	}
	
	public int getMoveSpeed() {
		return tempWeapon.getMoveSpeed() + 1;
	}
	

}
