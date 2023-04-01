package Builder;

import java.awt.Color;

public class PlagueDoctorBuilder implements HeroBuilder {
private Hero hero;
	
	public PlagueDoctorBuilder() {
		
		hero = new PlagueDoctor();
	}
	
	
	@Override
	public void buildHealthModifier() {
		hero.setHealth(140);;
	}

	@Override
	public void buildArmor() {
		hero.setArmor(new Color(30, 130, 76));
	}

	@Override
	public void buildDamage() {
		hero.setDamage(120);
		
	}
	

	@Override
	public void buildPoisonResistance() {
		hero.setPoisonResistance(80);
	}


	@Override
	public void buildBurningResistance() {
		hero.setBurningResistance(0);
	}
	
	@Override
	public void buildHeadGear() {
		hero.setHeadGear("Plague Mask");
	}


	@Override
	public void buildDefense() {
		hero.setDefense(20);
	}

	@Override
	public Hero getHero() {
		return hero;
	}

}
