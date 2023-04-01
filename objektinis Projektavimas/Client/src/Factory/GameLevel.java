package Factory;

import java.util.ArrayList;
import java.util.List;

import AbstractFactory.Enemy;
import AbstractFactory.EnemyAbstractFactory;
import AbstractFactory.EnemyBurnFactory;
import AbstractFactory.EnemyNormalFactory;
import AbstractFactory.EnemyPoisonFactory;
import Facade.EnemyCreationFacade;

public abstract class GameLevel {
	private String gameLevelName;
	private int roundCount;
	private int enemyCountPerRound;
	private int enemyCodeCounter;
	private int factoryCodeCounter;
	private int currRoundCount;
	private boolean levelCompleted;
	private int enemiesAddedTotal;
	private ArrayList<String> enemyCodes;
	private ArrayList<String> factoryCodes;
	private ArrayList<ArrayList<Enemy>> levelEnemies;
	
	
	public GameLevel() {
		//Just default params
		this.roundCount = 1;
		this.enemyCountPerRound = 3;
		this.levelCompleted = false;
		this.enemyCodes = new ArrayList<String>(List.of("GROUND", "FLYING", "UNDERGROUND"));
		this.factoryCodes = new ArrayList<String>(List.of("POISON", "BURNING", "NORMAL"));
		this.levelEnemies = new ArrayList<ArrayList<Enemy>>();
		this.gameLevelName = "default";
		this.enemyCodeCounter = 0;
		this.factoryCodeCounter = 0;
		this.currRoundCount = 0;
		this.enemiesAddedTotal = 0;
	}
	
	public void loadLevelResources() {
		
		Enemy enemy = null;
		ArrayList<Enemy> singleRoundEnemyList = new ArrayList<>();
		
		for (int i = 0; i < roundCount; i++) {
			
			singleRoundEnemyList = new ArrayList<>();
			
			for (int j = 0; j < enemyCountPerRound; j++) {
				enemyCodeCounter = enemyCodeCounter % enemyCodes.size(); // LOOP THROUGH ENEMY TYPES
				factoryCodeCounter = factoryCodeCounter % factoryCodes.size(); // LOOP THROUGH FACTORY TYPES: POISON/BURNING
				
				if (factoryCodes.get(factoryCodeCounter).equals(factoryCodes.get(0))) { // POISON
					switch (enemyCodes.get(enemyCodeCounter)) {
						case "GROUND":
							enemy = EnemyCreationFacade.CreatePoisonGroundEnemy();
							break;
						case "FLYING":
							enemy = EnemyCreationFacade.CreatePoisonFlyingEnemy();
							break;
						case "UNDERGROUND":
							enemy = EnemyCreationFacade.CreatePoisonUndergroundEnemy();
							break;
					}
				} else if (factoryCodes.get(factoryCodeCounter).equals(factoryCodes.get(1))) { // BURNING
					switch (enemyCodes.get(enemyCodeCounter)) {
						case "GROUND":
							enemy = EnemyCreationFacade.CreateBurnGroundEnemy();
							break;
						case "FLYING":
							enemy = EnemyCreationFacade.CreateBurnFlyingEnemy();
							break;
						case "UNDERGROUND":
							enemy = EnemyCreationFacade.CreateBurnUndergroundEnemy();
							break;
					}
				} else { //NORMAL
					switch (enemyCodes.get(enemyCodeCounter)) {
						case "GROUND":
							enemy = EnemyCreationFacade.CreateNormalGroundEnemy();
							break;
						case "FLYING":
							enemy = EnemyCreationFacade.CreateNormalFlyingEnemy();
							break;
						case "UNDERGROUND":
							enemy = EnemyCreationFacade.CreateNormalUndergroundEnemy();
							break;
					}
				}
				//ADD ENEMY TO ENEMY ROUND LIST (IF NOT NULL).
				if (enemy != null) {
					enemy.setName(factoryCodes.get(factoryCodeCounter) + "-" + enemyCodes.get(enemyCodeCounter) + "-" + enemiesAddedTotal);
					singleRoundEnemyList.add(enemy);
				}
				//INCREMENT
				factoryCodeCounter++;
				if (factoryCodeCounter == 3) {
					enemyCodeCounter++;
				}
				//COUNT TOTAL AMOUNT OF ENEMIES ADDED SO FAR.
				enemiesAddedTotal++;
			}
			
			//ADD ROUND ENEMY LIST TO LEVEL LIST. IF NOT EMPTY.
			if (singleRoundEnemyList.size() != 0) {
				levelEnemies.add(singleRoundEnemyList);
			}
			
			enemyCountPerRound++; // INCREASE AMOUNT OF ENEMIES GENERATED NEXT ROUND.
		}
	}
	
	public ArrayList<Enemy> getSingleRoundEnemies(){
		return levelEnemies.get(currRoundCount);
	}

	public boolean isLevelCompleted() {
		return levelCompleted;
	}

	public void setLevelCompleted(boolean levelCompleted) {
		this.levelCompleted = levelCompleted;
	}

	public int getEnemiesAddedTotal() {
		return enemiesAddedTotal;
	}

	public void setEnemiesAddedTotal(int enemiesAddedTotal) {
		this.enemiesAddedTotal = enemiesAddedTotal;
	}

	public int getEnemyCountPerRound() {
		return enemyCountPerRound;
	}

	public void setEnemyCountPerRound(int enemyCountPerRound) {
		this.enemyCountPerRound = enemyCountPerRound;
	}

	public int getEnemyCodeCounter() {
		return enemyCodeCounter;
	}

	public void setEnemyCodeCounter(int enemyCodeCounter) {
		this.enemyCodeCounter = enemyCodeCounter;
	}

	public int getFactoryCodeCounter() {
		return factoryCodeCounter;
	}

	public void setFactoryCodeCounter(int factoryCodeCounter) {
		this.factoryCodeCounter = factoryCodeCounter;
	}

	public int getRoundCount() {
		return roundCount;
	}


	public void setRoundCount(int roundCount) {
		this.roundCount = roundCount;
	}


	public ArrayList<String> getEnemyCodes() {
		return enemyCodes;
	}


	public void setEnemyCodes(ArrayList<String> enemyCodes) {
		this.enemyCodes = enemyCodes;
	}


	public ArrayList<String> getFactoryCodes() {
		return factoryCodes;
	}


	public void setFactoryCodes(ArrayList<String> factoryCodes) {
		this.factoryCodes = factoryCodes;
	}


	public ArrayList<ArrayList<Enemy>> getLevelEnemies() {
		return levelEnemies;
	}


	public void setLevelEnemies(ArrayList<ArrayList<Enemy>> levelEnemies) {
		this.levelEnemies = levelEnemies;
	}

	public String getGameLevelName() {
		return gameLevelName;
	}

	public void setGameLevelName(String gameLevelName) {
		this.gameLevelName = gameLevelName;
	}

	public int getCurrRoundCount() {
		return currRoundCount;
	}

	public void setCurrRoundCount(int currRoundCount) {
		this.currRoundCount = currRoundCount;
	}
	
	
}
