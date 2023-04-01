package Strategy;

import edu.ktu.signalrclient.Game;
import edu.ktu.signalrclient.Player;

public class Multishot implements IShootStrategy {
	
	@Override
	public void Shoot(Player player, long elapsed, String playerToUpdate) {
		player.setShootingDelay(player.getShootingDelay() - 50);
		if (elapsed > player.getShootingDelay()) {
			if (playerToUpdate == "Player") {
				Game.createMyBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2 - player.getSize(), 270);
				Game.createMyBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, 270);
			} else if (playerToUpdate == "TeamMate") {
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2 - player.getSize()/3, 270);
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, 270);
			}
			player.setShootingTimer(System.nanoTime());
		}
		player.setShootingDelay(player.getShootingDelay() + 50);
	}
}
