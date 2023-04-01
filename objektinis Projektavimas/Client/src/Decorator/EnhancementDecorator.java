package Decorator;

public abstract class EnhancementDecorator implements Weapon {
	
	protected Weapon tempWeapon;
	
	public EnhancementDecorator(Weapon weapon) {
		tempWeapon = weapon;
	}
	
	
	public String getDesc() {
		
		return tempWeapon.getDesc();
	}

	public int getDamage() {
		return tempWeapon.getDamage();
	}

	public long getSpeed() {
		return tempWeapon.getSpeed();
	}
	
	public int getMoveSpeeed() {
		return tempWeapon.getMoveSpeed();
	}

}
