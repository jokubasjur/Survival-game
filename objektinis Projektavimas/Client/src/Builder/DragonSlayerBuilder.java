package Builder;

import java.awt.Color;

public class DragonSlayerBuilder implements HeroBuilder {
	
	private Hero hero;
	
	public DragonSlayerBuilder() {
		
		hero = new DragonSlayer();
	}
	

	@Override
	public void buildHealthModifier() {
		hero.setHealth(160);
	}

	@Override
	public void buildArmor() {
		hero.setArmor(new Color(230, 126, 34));
	}
	
	@Override
	public void buildDamage() {
		hero.setDamage(100);
		
	}

	@Override
	public void buildPoisonResistance() {
		hero.setPoisonResistance(0);
	}



	@Override
	public void buildBurningResistance() {
		hero.setBurningResistance(80);
	}

	@Override
	public void buildHeadGear() {
		hero.setHeadGear("Scale Helmet");
	}


	@Override
	public void buildDefense() {
		hero.setDefense(50);
	}

	
	@Override
	public Hero getHero() {
		return hero;
	}	
}
