package edu.ktu.signalrclient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import com.microsoft.signalr.Action2;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import AbstractFactory.Enemy;
import AbstractFactory.EnemyAbstractFactory;
import AbstractFactory.EnemyBurnFactory;
import Adapter.StringUI;
import Adapter.StringUIAdapter;
import Bridge.*;
import Builder.DragonSlayerBuilder;
import Builder.Hero;
import Builder.HeroBuilder;
import Builder.HeroPortal;
import Builder.PlagueDoctorBuilder;
import Command.Command;
import Command.CommandChangePlayerColor;
import Command.CommandHistory;
import Decorator.DamageEnhancement;
import Decorator.DefaultWeapon;
import Decorator.MoveSpeedEnhancement;
import Decorator.SpeedEnhancement;
import Facade.EnemyCreationFacade;
import Factory.EasyLevelCreator;
import Factory.ExtremeLevelCreator;
import Factory.GameLevel;
import Factory.HardLevelCreator;
import Factory.LevelCreator;
import Factory.NormalLevelCreator;
import Iterator.ObjectArray;
import Iterator.ObjectHashTable;
import Iterator.ObjectList;
import Mediator.BoostsMediator;
import Observer.BossSubject;
import Observer.ConcreteBossMinionObserver;
import Singleton.GameSettings;
import Strategy.Diagonal;
import Strategy.FrontBullet;
import Strategy.IShootStrategy;
import Strategy.Machinegun;
import Strategy.Multishot;
import Strategy.NormalShot;
import Visitor.BoostElement;
import Visitor.GoldenBulletDamageImprovement;
import Visitor.HighBoost;
import Visitor.ImprovementVisitor;
import Visitor.LowBoost;
import Visitor.MidBoost;
import Visitor.NormalBulletDamageImprovement;
import Visitor.PlayerHealthImprovement;
import io.reactivex.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener, Action2<String, String>{
	
	private static final long serialVersionUID = 1L;
	
	//SINGLETON, GAME SETTINGS
	private static GameSettings gameSettings;
	private static final int actionExecutionSpeed = gameSettings.getInstance().actionExecutionSpeed;
	
	//GAME WINDOW/MAP PROPERTIES
	public static int windowWidth = 1025;
	public static int windowHeight = 600;
	public static int spriteSize = 50;
	public static int gameMapSize = 25;
	
	private static Menu menu;
	
	//MAP
	private static SpriteLibrary spriteLibrary;
	private static GameMap gameMap;
	private static Tile[][] tiles;
	
	//Hero builder
	private static HeroBuilder builder = new DragonSlayerBuilder();
	private static HeroPortal director = new HeroPortal();
	
	//PLAYER AND TEAMMATE / SHOOTING STRATEGIES
	private static IShootStrategy shootStrategy = new NormalShot();
	private static ArrayList<String> shootStrategies;
	private static Player teamMatePlayer = new Player(shootStrategy);
	private static Player player = teamMatePlayer.getClone();
	private static boolean moved = false;
	
	
	
	//HUB AND ACTION PERFORMED TIMER
	public static HubConnection hubConnection;
	javax.swing.Timer tm = new javax.swing.Timer(actionExecutionSpeed, this); //actionPerformed timer.
	
	//BULLETS
	public static ObjectArray<BulletTypeAbstraction> bulletsList;						//bridge
	public static ObjectHashTable<BulletTypeAbstraction> teamMateBulletsList;				//bridge
	public static BulletModeInplementor currentBulletMode;											//bridge
	public static boolean isBulletTypeChanged;										//bridge
	public static int bulletModeChangeCount;										//bridge
	
	private static BoostsMediator boostsMediator;									//mediator
	private static LowBoost lowBoostObject;
	private static MidBoost midBoostObject;
	private static HighBoost highBoostObject;
	private static BoostElement boost;
	private static ImprovementVisitor improvement;
	private static boolean isBoostGained = false;
	private static int deadEnemyCounter = 0;
	
	private CommandHistory history = new CommandHistory();
	
	//ENEMIES
	public static ObjectList<Enemy> enemiesList;
	private static LevelCreator levelCreatorFactory;
	private static GameLevel currentGameLevel;
	private static boolean gameLevelIsSet = false;
	private static boolean shouldInstantiateGameLevels = true;
	
	//BUTTON ARRAYS
	private static ArrayList<Rectangle> buttonBounds;
	private static ArrayList<Rectangle> heroIconBounds;
	private static ArrayList<String> buttonNames;
	private static ArrayList<String> heroNames;
	
	//CONNECTION BOOLEAN
	private static boolean connected = false;
	private static boolean firstInit = true;
	private static int velModifier = 0;
	
	//BOSS
	private static boolean isBossLevel = false;									//Observer
	
	private StringUIAdapter stringUIAdapter;
	
	public enum STATE {
		MENU,
		GAME,
		HERO_SELECT
	};
	//STATE
	private static STATE State = STATE.MENU;
	
	public Game() {
		menu = new Menu(windowWidth, windowHeight);
		
		bulletsList = new ObjectArray<BulletTypeAbstraction>();				//bridge
		teamMateBulletsList = new ObjectHashTable<BulletTypeAbstraction>();		//bridge
		currentBulletMode = new BulletNormalMode();							//bridge
		isBulletTypeChanged = false;										//bridge
		bulletModeChangeCount = 0;											//bridge
		
		boostsMediator = new BoostsMediator();								//mediator
		lowBoostObject = new LowBoost(boostsMediator);
		midBoostObject = new MidBoost(boostsMediator);
		highBoostObject = new HighBoost(boostsMediator);
		boostsMediator.addBoost(lowBoostObject);
		boostsMediator.addBoost(midBoostObject);
		boostsMediator.addBoost(highBoostObject);
		
		stringUIAdapter = new StringUIAdapter(new StringUI(windowWidth, windowHeight));
		
		enemiesList = new ObjectList<Enemy>();
		shootStrategies = new ArrayList<String>(Arrays.asList("NORMAL", "DIAGONAL", "FRONT", "MACHINE", "MULTI"));
		spriteLibrary = new SpriteLibrary();
		gameMap = new GameMap(gameMapSize, spriteLibrary);
		tiles = gameMap.getTiles();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(new GridBagLayout());
		levelCreatorFactory = new EasyLevelCreator();
		currentGameLevel = levelCreatorFactory.createLevel();
		currentGameLevel.loadLevelResources();
		gameLevelIsSet = true;
		tm.start();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
	    //---------------------------------
		buttonBounds = menu.getButtonBounds();
		heroIconBounds = menu.getHeroIcons();
		buttonNames = menu.getButtonNames();
		heroNames = menu.getHeroNames();
		//---------------------------------
		
		SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
        		JFrame jFrame = new JFrame();
        		jFrame.setTitle("Death Rounds");
        		jFrame.setSize(windowWidth, windowHeight);
        		jFrame.setLocationRelativeTo(null);
        		jFrame.setVisible(true);
        		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		jFrame.add(game);
            }
        });
		//---------------------------------
		try {
			RunSignalR();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createMyBullet(int x, int y, int angle) {							//bridge
		if (player.getBulletType().equals("golden")) {
			bulletsList.add(new GoldenBullet(x, y, angle, currentBulletMode, player.getGoldenBulletDamage()));
		} else {
			bulletsList.add(new NormalBullet(x, y, angle, currentBulletMode, player.getNormalBulletDamage()));
		}
	}
	
	public static void createTeamMateBullet(int x, int y, int angle) {							//bridge
		if (teamMatePlayer.getBulletType().equals("golden")) {
			teamMateBulletsList.add(new GoldenBullet(x, y, angle, currentBulletMode, teamMatePlayer.getGoldenBulletDamage()));
		} else {
			teamMateBulletsList.add(new NormalBullet(x, y, angle, currentBulletMode, teamMatePlayer.getNormalBulletDamage()));
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        if (State == STATE.GAME && gameLevelIsSet) {
			
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[0].length; j++) {
					g.drawImage(tiles[i][j].getSprite(), (i * 40), (j * 40), null);
				}
			}
	        
			//RENDER PLAYER AND TEAMMATE
			player.render(g);
			
			stringUIAdapter.draw("NAME: " + player.getNickname(), 10f, g, "upperLeftConerUp");							//adapter
			stringUIAdapter.draw("HP: " + String.valueOf(player.getHealthPoints()), 20f, g, "upperLeftConerDown");		//adapter
			stringUIAdapter.draw(String.valueOf(player.getPoints()) + " PTS", 15f, g, "upperLeftCornerDownSecond");		//adapter
			stringUIAdapter.draw("MODE: " + currentBulletMode.getName(), 15f, g, "upperLeftCornerDownThird");
			
			if (!teamMatePlayer.getNickname().equals("")) {
				teamMatePlayer.render(g);
				stringUIAdapter.draw("NAME: " + teamMatePlayer.getNickname(), 10f, g, "upperRightConerUp");
				stringUIAdapter.draw("HP: " + String.valueOf(teamMatePlayer.getHealthPoints()), 20f, g, "upperRightConerDown");
				stringUIAdapter.draw(String.valueOf(teamMatePlayer.getPoints()) + " PTS", 15f, g, "upperRightCornerDownSecond");
			}
			
			//RENDER PLAYER BULLETS
			Iterator<BulletTypeAbstraction> iterator = bulletsList.getIterator();
			while (iterator.hasNext()) {
				BulletTypeAbstraction bullet = iterator.next();
				bullet.render(g);
			}
			
			//RENDER TEAMMATE BULLETS
			if (teamMatePlayer.getNickname() != "") {
				Iterator<BulletTypeAbstraction> iterator3 = teamMateBulletsList.getIterator();
				while (iterator3.hasNext()) {
					BulletTypeAbstraction bullet = iterator3.next();
					bullet.render(g);
				}
			}
			
			//RENDER ENEMIES IF BOTH PLAYERS ARE READY
			if (player.isReady() && teamMatePlayer.isReady()) {
				Iterator<Enemy> iterator2 = enemiesList.getIterator();
				while (iterator2.hasNext()) {
					Enemy enemy = iterator2.next();
					if (!enemy.isDead()) {
						enemy.update();
					}
				}
				
				iterator2 = enemiesList.getIterator();
				while (iterator2.hasNext()) {
					Enemy enemy = iterator2.next();
					if (!enemy.isDead()) {
						enemy.render(g);
					}
				}
			}
			
			//DRAW STRINGS
			if (currentGameLevel.getCurrRoundCount() == 0 && !player.isReady()) {
				stringUIAdapter.draw("PRESS ENTER TO START THE GAME!", 15f, g, "upperCenter");
			} else if (!teamMatePlayer.isReady() && player.isReady()) {
				stringUIAdapter.draw("WAIT FOR TEAMMATE...", 15f, g, "upperCenter");
			} else if (currentGameLevel.getCurrRoundCount() == 0) {
				stringUIAdapter.draw("WAIT FOR NEW LEVEL TO START...", 15f, g, "upperCenter");
			} else {
				stringUIAdapter.draw(currentGameLevel.getGameLevelName() + "- Round: " + (currentGameLevel.getCurrRoundCount()) + "/" + (currentGameLevel.getRoundCount()), 15f, g, "upperCenterUnder");
			}
			
		} else if (State == STATE.MENU) {
            menu.render(g, State);
		} else if (State == STATE.HERO_SELECT) {
		    menu.render(g, State);
		}
		
	}
	
    public static void RunSignalR() throws Exception{
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        //String url = "https://chatroom2019.azurewebsites.net/signalr/";
        String url = "http://localhost:5000/gamehub";
        //CONNECT TO HUB (SIGNAL R)
        hubConnection = HubConnectionBuilder.create(url).build();
        hubConnection.start().blockingAwait();
        
        //GET PLAYER CONNECTION ID
        String Id = hubConnection.getConnectionId();
        
        player.setNickname(Id);
        
        //RECEIVE MESSAGES / LOGS
        hubConnection.on("ReceiveMessage", (user, message) -> {
            ReceiveMessage(user, message);
            System.out.println(user + message);
        }, String.class, String.class);
        
        //UPDATE TEAMMATE POSITION
        hubConnection.on("ReceiveUpdateTeamMate", (user, posX, posY, posXVel, posYVel, healthPoints, isReady, isShooting) -> {
            ReceiveUpdateTeamMate(user, posX, posY, posXVel, posYVel, healthPoints, isReady, isShooting);
        }, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Boolean.class, Boolean.class);
        
        //RECEIVE PLAYER COLOR
        hubConnection.on("ReceivePlayerColor", (teamColor) -> {
        	ReceivePlayerColor(teamColor);
        }, Integer.class);
        //RECEIVE PLAYER SHOOTING STRATEGY
        hubConnection.on("ReceivePlayerShootingStrategy", (strategy) -> {
        	ReceivePlayerShootingStrategy(strategy);
        }, String.class);
		
        //RECEIVE ENEMY UPDATES ON DEATH
        hubConnection.on("ReceiveUpdateEnemy", (enemyName) -> {
        	ReceiveUpdateEnemy(enemyName);
        }, String.class);
        
        hubConnection.on("ReceiveUpdateEnemySpeed", (enemyName, enemySpeed) -> {							//Observer
        	ReceiveUpdateEnemySpeed(enemyName, enemySpeed);
        }, String.class, Double.class);
        
        hubConnection.on("ReceivePlayerBulletType", (bulletType) -> {							//bridge
        	ReceivePlayerBulletType(bulletType);
        }, String.class);
        
        hubConnection.on("ReceivePlayerPoints", (points) -> {
        	ReceivePlayerPoints(points);
        }, Integer.class);
        
        //CHECK IF REALLY CONNECTED
        if (!player.getNickname().equals("")) {
        	System.out.println("CONNECTED");
        	connected = true;
        }
        
        String message = "quitMessageHere";
        while (!message.equals("leave")){
        	message = reader.nextLine();
            hubConnection.send("SendMessage", player.getNickname(), message);
        }

        reader.close();
        hubConnection.stop();
    }
    
    //CHECK IF ALL ENEMIES HAVE BEEN KILLED
    public static int checkEnemiesDeadCount() {
    	int countDead = 0;
    	Iterator<Enemy> iterator = enemiesList.getIterator();
    	while (iterator.hasNext()) {
    		Enemy enemy = iterator.next();
    		if (enemy.isDead() && enemy.isDeadUpdated()) {
				countDead++;
			}
    	}
    	
    	return countDead;
    }
    
    //UPDATE CURR LEVEL -> EITHER LOAD ANOTHER ONE OR LOAD NEXT ROUND
    public static void updateCurrentLevel() {
    	
    		shouldInstantiateGameLevels = false;
    		UpdatePlayerColor(player.getTeamColor().getRGB());
    		
    		Timer getEnemyListTimer = new Timer();
        	getEnemyListTimer.schedule(new TimerTask() {
                   @Override
                   public void run() {
                	   
                	   player.setDefaultWeapon(new DefaultWeapon());
                	   
                   	if ((currentGameLevel.getCurrRoundCount() >= (currentGameLevel.getRoundCount()))) {
                    	if ((enemiesList.size() == 0) || (checkEnemiesDeadCount() == currentGameLevel.getEnemiesAddedTotal())) {
                    		gameLevelIsSet = false;
                    		isBossLevel = false;											//Observer
                       		if (currentGameLevel.getGameLevelName() == "EASY_LVL") {
                       			levelCreatorFactory = new NormalLevelCreator();
                       			currentGameLevel = levelCreatorFactory.createLevel();
                       			currentGameLevel.loadLevelResources();
                       			enemiesList.clear();
                       			gameLevelIsSet = true;
                       			
                       		} else if (currentGameLevel.getGameLevelName() == "NORMAL_LVL") {
                       			levelCreatorFactory = new HardLevelCreator();
                       			currentGameLevel = levelCreatorFactory.createLevel();
                       			currentGameLevel.loadLevelResources();
                       			enemiesList.clear();
                       			gameLevelIsSet = true;
                       			
                       		} else if (currentGameLevel.getGameLevelName() == "HARD_LVL") {
                       			levelCreatorFactory = new ExtremeLevelCreator();
                       			currentGameLevel = levelCreatorFactory.createLevel();
                       			currentGameLevel.loadLevelResources();
                       			enemiesList.clear();
                       			gameLevelIsSet = true;
                        			
                        	} else if (currentGameLevel.getGameLevelName() == "EXTREME_LVL") {
                        		System.out.println("BOSS LEVEL");									//Observer
                        		
                        		isBossLevel = true;
                           		enemiesList.clear();
                        		BossSubject boss = new BossSubject();
                        		boss.setName("Boss1");
                        		enemiesList.add(boss);
                        		for (int i = 0; i < 5; i++) {
                        			ConcreteBossMinionObserver minion = new ConcreteBossMinionObserver(boss);
                        			boss.addMinion(minion);
                        			enemiesList.add(minion);
                        		}
                        		enemiesList.add(EnemyCreationFacade.CreateBurnGroundEnemy());
                        		currentGameLevel.setEnemiesAddedTotal(7);
                        		gameLevelIsSet = true;
                        	} else {
                        		System.out.println("ERROR FINDING CURRENT GAME LEVEL");
                        	}
                    	}
                    } else {
                        enemiesList.addAll(currentGameLevel.getSingleRoundEnemies());
                        currentGameLevel.setCurrRoundCount(currentGameLevel.getCurrRoundCount() + 1);
                        
                        //decorator
                        
                        for (int i = 0; i < 3; i++) {
							if(getRandomBoolean()) {
								switch (i) {
								case 0: {
									player.setDefaultWeapon(new SpeedEnhancement(player.getDefaultWeapon()));	
									player.setShootingDelay(player.getShootingDelay() + player.getDefaultWeapon().getSpeed());
								}
								case 1: {
									player.setDefaultWeapon(new DamageEnhancement(player.getDefaultWeapon()));								
								}
								case 2: {
									player.setDefaultWeapon(new MoveSpeedEnhancement(player.getDefaultWeapon()));
									velModifier = player.getDefaultWeapon().getMoveSpeed();
									
								}
								}
							}
						}
                    }
                  	
                 }
               }, 0, 10000);
        	shouldInstantiateGameLevels = false;
    }
    
    public static boolean getRandomBoolean() {
        return Math.random() < 0.25;
    }
    
    public static void ReceiveUpdateEnemySpeed(String enemyName, double enemySpeed) {				//Observer
    	Iterator<Enemy> iterator = enemiesList.getIterator();
    	while (iterator.hasNext()) {
    		Enemy enemy = iterator.next();
    		if ((enemy.getName().contains(enemyName))
					&& (enemy.getName().length() == enemyName.length())) {
				enemy.setSpeed(enemySpeed);
				break;
			}
    	}
    }
    
    public static void ReceivePlayerBulletType(String bulletType) {									//bridge
    	if (!teamMatePlayer.getNickname().equals("")) {
    		teamMatePlayer.setBulletType(bulletType);
    	}
    }
    
    public static void ReceivePlayerPoints(int points) {
    	if (!teamMatePlayer.getNickname().equals("")) {
    		teamMatePlayer.setPoints(points);
    	}
    }
    
	@Override
	public void invoke(String name, String message) {
		
	}
	
	//RECEIVE HUB METHODS
	public static void ReceivePlayerShootingStrategy(String strategy) {
		
		if (!teamMatePlayer.getNickname().equals("")) {
			switch(strategy) 
	        { //"NORMAL", "DIAGONAL", "FRONT", "MACHINE", "MULTI"
	            case "NORMAL": 
	                teamMatePlayer.setShootStrategy(new NormalShot()); 
	                break; 
	            case "DIAGONAL": 
	            	teamMatePlayer.setShootStrategy(new Diagonal()); 
	                break; 
	            case "FRONT": 
	            	teamMatePlayer.setShootStrategy(new FrontBullet()); 
	                break;
	            case "MACHINE": 
	            	teamMatePlayer.setShootStrategy(new Machinegun()); 
	                break; 
	            case "MULTI": 
	            	teamMatePlayer.setShootStrategy(new Multishot()); 
	                break; 
	            default: 
	            	teamMatePlayer.setShootStrategy(new NormalShot());  
	        } 
		}
	}
	
	public static void ReceiveUpdateEnemy(String enemyName) {
	    Iterator<Enemy> iterator = enemiesList.getIterator();
	    while (iterator.hasNext()) {
	    	Enemy enemy = iterator.next();
	    	if ((enemy.getName().contains(enemyName))
					&& (enemy.getName().length() == enemyName.length())) {
	    		enemy.setDead(true);
	    		enemy.setDeadUpdated(true);
				System.out.println(enemy.getName());
				break;
			}
	    }
	}
	
	public static void ReceiveMessage(String user, String message) {
		String msg = user + ": " + message;
		System.out.println(msg);
	}
	
	public static void ReceivePlayerColor(int teamColor) {
		teamMatePlayer.setTeamColor(new Color(teamColor));
	}
	
	public static void ReceiveUpdateTeamMate(String user, int posX, int posY, int posXVel, int posYVel, int healthPoints, boolean isReady, boolean isShooting) {
		teamMatePlayer.setNickname(user);
		teamMatePlayer.setX(posX + posXVel);
		teamMatePlayer.setY(posY + posYVel);
		teamMatePlayer.setHealthPoints(healthPoints);
		teamMatePlayer.setReady(isReady);
		teamMatePlayer.setShooting(isShooting);
	}
	
	//CALL HUB METHODS, TO UPDATE.
	public static void UpdateEnemy(String enemyName) {
		hubConnection.send("UpdateEnemy", enemyName);
	}
	
	public static void UpdatePlayerColor(int teamColor) {
		hubConnection.send("UpdatePlayerColor", teamColor);
	}
	
	public static void UpdateTeamMate(String user, int posX, int posY, int posXVel, int posYVel, int healthPoints, boolean isReady, boolean isShooting) {
		hubConnection.send("UpdateTeamMate", user, posX, posY, posXVel, posYVel, healthPoints, isReady, isShooting);
	}
    
	public static void UpdateShootingStrategy(String strategy) {
		if (!teamMatePlayer.getNickname().equals("")) {
			hubConnection.send("UpdateShootingStrategy", strategy);	
		}
	}
	
	public static void UpdatePoints(int points) {
		if (!teamMatePlayer.getNickname().equals("")) {
			hubConnection.send("UpdatePoints", points);
		}
	}
	
	public static void SendMessage(String user, String message) {
		hubConnection.send("SendMessage", user, message);
	}
	
	//HANDLING ALL PERFORMED ACTIONS
	@Override
	public void actionPerformed(ActionEvent e) {
			
			//IF PLAYER READY UPDATE
			if (connected && player.isReady()) {
				player.update("Player");
				UpdateTeamMate(player.getNickname(), player.getX(), player.getY(), player.getVelX(), player.getVelY(), player.getHealthPoints(), player.isReady(), player.isShooting());
				UpdatePlayerColor(player.getTeamColor().getRGB());
			}
			//IF TEAMMATE READY UPDATE
			if (!teamMatePlayer.getNickname().equals("") && teamMatePlayer.isReady()) {
				teamMatePlayer.update("TeamMate");
			}
			
			//IF BOTH READY - LOAD FIRST LEVEL
			if (player.isReady() && teamMatePlayer.isReady() && shouldInstantiateGameLevels) { // STARTS INITIAL LEVEL FOR BOTH PLAYERS AT THE SAME TIME.
				System.out.println("BOTH READY");
				updateCurrentLevel();
			}
			
			//CHECK IF BULLETS OUT OF BOUNDS - REMOVE (PLAYER)
			Iterator<BulletTypeAbstraction> iterator = bulletsList.getIterator();
			while (iterator.hasNext()) {
				BulletTypeAbstraction bullet = iterator.next();
				boolean remove = bullet.update();
				if (remove) {
					iterator.remove();
				}
			}
			
			//CHECK IF BULLETS OUT OF BOUNDS - REMOVE (TEAMMATE)
			Iterator<BulletTypeAbstraction> iterator3 = teamMateBulletsList.getIterator();
			while (iterator3.hasNext()) {
				BulletTypeAbstraction teammateBullet = iterator3.next();
				boolean remove = teammateBullet.update();
				if (remove) {
					iterator3.remove();
				}
			}
			
			//CHECK IF BULLET HIT ENEMY - IF SO DMG ENEMY, REMOVE BULLET.
			iterator = bulletsList.getIterator();
			while (iterator.hasNext()) {
				BulletTypeAbstraction bullet = iterator.next();									//bridge
				
				double bX = bullet.getX();
				double bY = bullet.getY();
				double bR = bullet.getR();
				
				Iterator<Enemy> iterator2 = enemiesList.getIterator();
				while (iterator2.hasNext()) {
					Enemy enemy = iterator2.next();
					
					if (!enemy.isDead()) {
						double eX = enemy.getX();
						double eY = enemy.getY();
						double eR = enemy.getRad();
						
						double dx = bX - eX;
						double dy = bY - eY;
						double dist = Math.sqrt(dx * dx + dy * dy);
						double radSum = Math.pow((bR + eR), 2);
						double precise = radSum / 2 - radSum / 4;
						double preciseToDefend = (radSum * 2);
						
						/* Strategy for Enemy
						if (dist <= preciseToDefend) {
							enemy.Defend();
						}*/
						//CHECK COLLISION
						if(dist <= precise) {							
							enemy.hit(bullet.dealDamage(enemy.getType()));					//bridge
							iterator.remove();
							break;
						}
					}
				}
			}
			
			//IF ENEMY IS DEAD - BUT NOT UPDATED, SET DEADUPDATED, UPDATE ENEMY.
			Iterator<Enemy> iterator2 = enemiesList.getIterator();
			while (iterator2.hasNext()) {
				Enemy enemy = iterator2.next();
				if(enemy.isDead() && !enemy.isDeadUpdated()) {
					enemy.setDeadUpdated(true);
					UpdateEnemy(enemy.getName());
					player.setPoints(player.getPoints() + enemy.getPointsValue());
					UpdatePoints(player.getPoints());
					deadEnemyCounter++;
				}
			}
			
			//WHEN BOSS LEVEL IS ON, UPTADE ENEMIES SPEED												//Observer
			if (isBossLevel) {
				iterator2 = enemiesList.getIterator();
				while (iterator2.hasNext()) {
					Enemy enemy = iterator2.next();
					hubConnection.send("UpdateEnemySpeed", enemy.getName(), enemy.getSpeed());
				}
			}
			
			//AFTER BULLET TYPE CHANGED, UPDATE TEAMMATE ABOUT IT
			if (isBulletTypeChanged) {																		//bridge
				hubConnection.send("UpdatePlayerBulletType", player.getBulletType());
				isBulletTypeChanged = false;
			}
			
			//WHEN PLAYER KILLS 3 ENEMYS HE GAIN BOOST
			if (deadEnemyCounter >= 2) {																	//visitor
				deadEnemyCounter = 0;
				generateRandomBoost();
				boost.boost(improvement);
				System.out.println("BOOST ACTIVATED");
			}			
			
			//AFTER HIT - PLAYER GOES TO RECOVER - CANT ME DAMAGED, ONLY MY DoT
			if (!player.isRecovering()) {
				Rectangle boundsPlayer = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
				iterator2 = enemiesList.getIterator();
				while (iterator2.hasNext()) {
					Enemy enemy = iterator2.next();
					Ellipse2D.Double elipDouble = new Ellipse2D.Double(enemy.getX(), enemy.getY(), enemy.getR(), enemy.getR());
					
					if(elipDouble.intersects(boundsPlayer)) {
						enemy.damagePlayer(player);
					}
				}
			}
			repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (State == STATE.GAME) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				player.setVelY(-3 - velModifier);
				moved = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				player.setVelY(3 + velModifier);
				moved = true; 
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				player.setVelX(-3 - velModifier);
				//player.setVelY(0);
				moved = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				player.setVelX(3 + velModifier);
				moved = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				 player.setShooting(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				player.setReady(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_Q) {										//bridge
				if (player.getBulletType().equals("normal")) {
					player.setBulletType("golden");
				} else {
					player.setBulletType("normal");
				}
				isBulletTypeChanged = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {									//bridge
				bulletModeChangeCount++;
				int modeNumber = bulletModeChangeCount % 4;
				switch (modeNumber) {
					case 0:
						currentBulletMode = new BulletNormalMode();
						break;
					case 1:
						currentBulletMode = new BulletFlyingMode();
						break;
					case 2:
						currentBulletMode = new BulletGroundMode();
						break;
					case 3:
						currentBulletMode = new BulletUndergroundMode();
						break;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_6) {
				executeCommand(new CommandChangePlayerColor(player));
				UpdatePlayerColor(player.getTeamColor().getRGB());
			}
			if (e.getKeyCode() == KeyEvent.VK_7) {
				undo();
				UpdatePlayerColor(player.getTeamColor().getRGB());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (State == STATE.GAME) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				player.setVelY(0);
				moved = false;
				break;
			case KeyEvent.VK_S: 
				player.setVelY(0);
				moved = false;
				break;
			case KeyEvent.VK_A: 
				player.setVelX(0);
				moved = false;
				break;
			case KeyEvent.VK_D: 
				player.setVelX(0);
				moved = false;
				break;
			case KeyEvent.VK_SPACE:
				player.setShooting(false);
				break;
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("X: " + e.getXOnScreen() + "; Y: " + e.getYOnScreen());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	//CHECK IF MOUSE BUTTON (X, Y) -> CLICKED ONE OF THE BUTTONS.
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if (State != STATE.GAME && State == STATE.MENU) {
			int x = e.getX();
	        int y = e.getY();
	        buttonBounds = menu.getButtonBounds();
	        for (int i = 0; i < buttonBounds.size(); i++) {
	        	
	        	int bX = (int) (buttonBounds.get(i).getX());
	        	int bW = (int) buttonBounds.get(i).getWidth();
	        	int bY = (int) (buttonBounds.get(i).getY());
	        	int bH = (int) (buttonBounds.get(i).getHeight());
	        	
	        	
	        	if (((x >= bX) && (x <= bX + bW)) && ((y >= bY) && (y <= bY + bH))) {
	        		if (buttonNames.get(i).equals("PLAY")) {
	        			player.initHeroHealthPoints();
	        			player.setTeamColor(player.getSelectedHero().getArmor());
	        			State = STATE.GAME;
	        			break;
	        		} else if (buttonNames.get(i).equals("SELECT HERO")) {
	        			State = STATE.HERO_SELECT;
	        			break;
	        		} else if (buttonNames.get(i).equals("EXIT")) {
	        			System.exit(1);
	        		}
	        	}
			}
		}

		if (State == STATE.HERO_SELECT) {
			int x = e.getX();
	        int y = e.getY();
	        for (int i = 0; i < heroIconBounds.size(); i++) {
	        	int bX = (int) (heroIconBounds.get(i).getX());
	        	int bW = (int) heroIconBounds.get(i).getWidth();
	        	int bY = (int) (heroIconBounds.get(i).getY());
	        	int bH = (int) (heroIconBounds.get(i).getHeight());
	        	
	        	int X = (int) buttonBounds.get(buttonBounds.size() - 1).getX();
	        	int W = (int) buttonBounds.get(buttonBounds.size() - 1).getWidth();
	        	int Y = (int) buttonBounds.get(buttonBounds.size() - 1).getY();
	        	int H = (int) buttonBounds.get(buttonBounds.size() - 1).getHeight();
	        	
	        	
	        	
	        	if (((x >= bX) && (x <= bX + bW)) && ((y >= bY) && (y <= bY + bH))) {
	        		if (heroNames.get(i).equals("DRAGON SLAYER")) {
	        			builder = new DragonSlayerBuilder();
	        			player.setSelectedHero(director.constructHero(builder));
	        			break;
	        		} else if (heroNames.get(i).equals("PLAGUE DOCTOR")) {
	        			builder = new PlagueDoctorBuilder();
	        			player.setSelectedHero(director.constructHero(builder));
	        			break;
	        		}
	        	}
	        	
	        	//CHECK BACK BUTTON CLICKED
	        	if (((x >= X) && (x <= X + W)) && ((y >= Y) && (y <= Y + H))) {
        			State = STATE.MENU;
        			break;
        		}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void executeCommand(Command command) {
    	command.execute();
        history.push(command);
    }
 
    private void undo() {
        if (history.isEmpty()) return;
 
        Command command = history.pop();
        if (command != null) {
            command.undo();
        }
    }
    
    //visitor
    private void generateRandomBoost() {
    	int range = lowBoostObject.getAccessRate() + midBoostObject.getAccessRate() + highBoostObject.getAccessRate();
    	int lowBoostLastRangePosition = lowBoostObject.getAccessRate();
    	int midBoostLastRangePosition = lowBoostObject.getAccessRate() + midBoostObject.getAccessRate();
    	String message = "Gained ";
    	
    	int randomNum = ThreadLocalRandom.current().nextInt(1, range + 1);
    	if (randomNum <= lowBoostLastRangePosition) {
    		boost = lowBoostObject;
    		message += "LOW boost: ";
    	} else if (randomNum <= midBoostLastRangePosition) {
    		boost = midBoostObject;
    		message += "MID boost: ";
    	} else {
    		boost = highBoostObject;
    		message += "HIGH boost: ";
    	}
    	
    	randomNum = ThreadLocalRandom.current().nextInt(1, 4);
    	switch (randomNum) {
    		case 1:
    			improvement = new PlayerHealthImprovement(player);
    			message += "HEALTH";
    			break;
    		case 2:
    			improvement = new NormalBulletDamageImprovement(player);
    			message += "NORMAL BULLET DAMAGE";
    			break;
    		case 3:
    			improvement = new GoldenBulletDamageImprovement(player);
    			message += "GOLDEN BULLET DAMAGE";
    			break;
    	}
    	
    	System.out.println(message);
    }
	
}
