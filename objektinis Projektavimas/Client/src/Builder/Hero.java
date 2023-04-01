package Builder;

import java.awt.Color;

public abstract class Hero implements Cloneable{
	
	protected int health;
    protected int defense;
    protected int damage;
    protected int burningResistance;
    protected int poisonResistance;
    protected String headGear;
    protected Color armor;
    
    
 
    public Color getArmor() {
		return armor;
	}

	public void setArmor(Color armor) {
		this.armor = armor;
	}

	public String getHeadGear() {
		return headGear;
	}

	public void setHeadGear(String headGear) {
		this.headGear = headGear;
	}

	public int getHealth() {
        return health;
    }
 
    public void setHealth(int health) {
        this.health = health;
    }
    
    public int getDefense() {
        return defense;
    }
 
    public void setDefense(int armor) {
        this.defense = armor;
    }
    
    public int getDamage() {
        return damage;
    }
 
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public int getBurningResistance() {
        return burningResistance;
    }
 
    public void setBurningResistance(int burningResistance) {
        this.burningResistance = burningResistance;
    }
    
    public int getPoisonResistance() {
        return poisonResistance;
    }
 
    public void setPoisonResistance(int poisonResistance) {
        this.poisonResistance = poisonResistance;
    }
 
}
