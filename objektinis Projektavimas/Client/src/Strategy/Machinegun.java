package Strategy;

import edu.ktu.signalrclient.Game;
import edu.ktu.signalrclient.Player;

public class Machinegun implements IShootStrategy {
	
	@Override
	public void Shoot(Player player, long elapsed, String playerToUpdate) {
		long delayPrevious = player.getShootingDelay();
		player.setShootingDelay(player.getShootingDelay() - player.getShootingDelay() / 2);
		if (elapsed > player.getShootingDelay()) {
			if (playerToUpdate == "Player") {
				Game.createMyBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, 270);
			} else if (playerToUpdate == "TeamMate") {
				Game.createTeamMateBullet(player.getX() + (player.getSize() / 2), player.getY() - player.getSize()/2, 270);
			}
			player.setShootingTimer(System.nanoTime());
		}
		player.setShootingDelay(delayPrevious);
	}
}
