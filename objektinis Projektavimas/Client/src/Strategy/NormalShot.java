package Strategy;

import edu.ktu.signalrclient.Game;
import edu.ktu.signalrclient.Player;

public class NormalShot implements IShootStrategy {
	
	@Override
	public void Shoot(Player player, long elapsed, String playerToUpdate) {
		if (elapsed > player.getShootingDelay()) {
			if (playerToUpdate == "Player") {
				Game.createMyBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, 270);				
			} else if (playerToUpdate == "TeamMate") {
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, 270);
			}
			player.setShootingTimer(System.nanoTime());
		}
		
	}
}
