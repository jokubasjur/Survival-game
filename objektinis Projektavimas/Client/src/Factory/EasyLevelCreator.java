package Factory;

public class EasyLevelCreator extends LevelCreator {

	public GameLevel createLevel() {
		return new EasyLevel();
	}

}
