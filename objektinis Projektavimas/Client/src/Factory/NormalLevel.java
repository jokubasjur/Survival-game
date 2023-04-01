package Factory;

import java.util.ArrayList;

import AbstractFactory.Enemy;
import Facade.EnemyCreationFacade;
import Observer.BossSubject;
import Observer.ConcreteBossMinionObserver;
import edu.ktu.signalrclient.Game;

public class NormalLevel extends GameLevel {

	public NormalLevel() {
		super();
		
		setRoundCount(4);
		setEnemyCountPerRound(2);
		setGameLevelName("NORMAL_LVL");
	}
}
