package Builder;

public class HeroPortal {
	public Hero constructHero(HeroBuilder builder) {
		builder.buildHealthModifier();
		builder.buildArmor();
		builder.buildHeadGear();
		builder.buildDamage();
		builder.buildDefense();
		builder.buildPoisonResistance();
		builder.buildBurningResistance();
        return builder.getHero();
    }
}
