package Strategy;

import edu.ktu.signalrclient.Player;

public interface IShootStrategy {
	
	void Shoot(Player player, long elapsed, String playerToUpdate);
}
