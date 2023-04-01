package Strategy;

import edu.ktu.signalrclient.Game;
import edu.ktu.signalrclient.Player;

public class Diagonal implements IShootStrategy {
	
	@Override
	public void Shoot(Player player, long elapsed, String playerToUpdate) {
		int angleOne = 225;
		int angleTwo = 270;
		int angleThree = 315;
		if (elapsed > player.getShootingDelay()) {
			if (playerToUpdate == "Player") {
				Game.createMyBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, angleOne);
				Game.createMyBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, angleTwo);
				Game.createMyBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, angleThree);
			} else if (playerToUpdate == "TeamMate") {
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, angleOne);
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, angleTwo);
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, angleThree);
			}
			player.setShootingTimer(System.nanoTime());
		}
		
	}
}
