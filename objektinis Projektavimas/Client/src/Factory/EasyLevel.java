package Factory;

import java.util.ArrayList;

import AbstractFactory.Enemy;
import Facade.EnemyCreationFacade;
import Observer.BossSubject;
import Observer.ConcreteBossMinionObserver;
import edu.ktu.signalrclient.Game;

public class EasyLevel extends GameLevel {
	
	public EasyLevel() {
		super();
		
		setRoundCount(3);
		setEnemyCountPerRound(2);
		setGameLevelName("EASY_LVL");
	}
}
