package Factory;

import java.util.ArrayList;

import AbstractFactory.Enemy;
import Facade.EnemyCreationFacade;
import Observer.BossSubject;
import Observer.ConcreteBossMinionObserver;
import edu.ktu.signalrclient.Game;

public class HardLevel extends GameLevel {
	
	public HardLevel() {
		super();
		
		setRoundCount(5);
		setEnemyCountPerRound(2);
		setGameLevelName("HARD_LVL");
	}
}
