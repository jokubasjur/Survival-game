package Observer;

import java.awt.Color;
import java.util.ArrayList;

import AbstractFactory.Enemy;

public  class BossSubject extends Enemy {
	private ArrayList<IBossMinionObserver> minions = new ArrayList<>();
	
	public BossSubject() {
		super();
		
		setColor(Color.GREEN);
		setHealth(100);
		setR(20);
		setDamage(4);
		setSpeed(4);
		setType("normal");
	}
	
	@Override
	public void hit(int damage) {    //kai bossas pamusamas, jis pranesa savo minionams, kad jo hp pasikeite
		health -= damage;
		if (health <= 0) {
			setDead(true);
		}
		notifyMinions();
	}
	
	@Override
	public void setDead(boolean dead) {
		this.dead = dead;
		notifyMinions();
	}
	
	public void addMinion(IBossMinionObserver newMinion) {
		minions.add(newMinion);
	}
	
	public void removeMinion(IBossMinionObserver removeMinion) {
		minions.remove(removeMinion);
	}
	
	public void notifyMinions() {
		for(IBossMinionObserver minion : minions) {
			minion.updateMinion();
		}
	}
	
}
