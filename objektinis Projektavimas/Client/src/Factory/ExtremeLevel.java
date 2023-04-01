package Factory;

import java.util.ArrayList;

import AbstractFactory.Enemy;
import Facade.EnemyCreationFacade;
import Observer.BossSubject;
import Observer.ConcreteBossMinionObserver;
import edu.ktu.signalrclient.Game;

public class ExtremeLevel extends GameLevel {

	public ExtremeLevel() {
		super();
		setRoundCount(6);
		setEnemyCountPerRound(3);
		setGameLevelName("EXTREME_LVL");
	}
}
