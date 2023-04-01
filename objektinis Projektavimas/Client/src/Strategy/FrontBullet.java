package Strategy;

import edu.ktu.signalrclient.Game;
import edu.ktu.signalrclient.Player;

public class FrontBullet implements IShootStrategy {
	
	@Override
	public void Shoot(Player player, long elapsed, String playerToUpdate) {
		int angle = 270;
		if (elapsed > player.getShootingDelay()) {
			if (playerToUpdate == "Player") {
				Game.createMyBullet(player.getX() + (player.getSize() / 2) - (player.getSize()/2), player.getY() - player.getSize()/2, angle);
				Game.createMyBullet(player.getX() + (player.getSize() / 2) + (player.getSize()/2), player.getY() - player.getSize()/2, angle);
			} else if (playerToUpdate == "TeamMate") {
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2) - (player.getSize()/2), player.getY() - player.getSize()/2, angle);
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2) + (player.getSize()/2), player.getY() - player.getSize()/2, angle);
			}
			player.setShootingTimer(System.nanoTime());
		}
		
	}
}
