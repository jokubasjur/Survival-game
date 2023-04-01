package Factory;

public class ExtremeLevelCreator extends LevelCreator {

	public GameLevel createLevel() {
		return new ExtremeLevel();
	}

}
