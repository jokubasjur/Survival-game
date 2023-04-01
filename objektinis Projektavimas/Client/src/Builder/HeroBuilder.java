package Builder;

public interface HeroBuilder {
	
	public void buildHealthModifier();
    public void buildDefense();
    public void buildDamage();
    public void buildPoisonResistance();
    public void buildBurningResistance();
    public void buildHeadGear();
    public void buildArmor();
    
    public Hero getHero();
}