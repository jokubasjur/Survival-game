package Observer;

import AbstractFactory.Enemy;
import edu.ktu.signalrclient.Game;

public class ConcreteBossMinionObserver extends Enemy implements IBossMinionObserver{
	private BossSubject boss;
	private int bossHealth;
	private static int minionNr = 1;  //naudojamas sudarant miniono name ir vieta zaidime
	
	public ConcreteBossMinionObserver(BossSubject boss) {
		super();
		
		this.boss = boss;
		bossHealth = boss.getHealth();
		
		setColor(boss.getColor());
		
		String name = boss.getName() + minionNr;
		minionNr++;
		setName(name);
		setSpeed(5);
		setHealth(1000);
		setType("normal");
		
		int x = 200 * minionNr;
		while (x > Game.windowWidth) {
			x = x - Game.windowWidth;
		}
		setX(x);
	}

	public void updateMinion() {
		if (!isDead()) {
			bossHealth = boss.getHealth();
			changeMinionParams();
		}
	}
	
	private void changeMinionParams() {
		double currentMinionSpeed = getSpeed();
		if (bossHealth <= 0 || boss.isDead()) {				//Jei bosui nelieka hp, minionas zuva
			setDead(true);
			setDeadUpdated(true);
		} else {
			if (bossHealth <= 30) {							//Jei bosui lieka 30 ar maziau hp, padideja jo miniono speedas +3
				setSpeed(currentMinionSpeed + 3);
			} else {
				if (bossHealth <= 70) {						//Jei bosui lieka nuo 70 iki 40 hp, padideja jo miniono speedas +2
					setSpeed(currentMinionSpeed + 2);
				}
			}
		}
	}
}
