package Command;
 
import java.awt.Color;
 
import edu.ktu.signalrclient.Player;
 
public class CommandChangePlayerColor extends Command {
 
    public CommandChangePlayerColor(Player player) {
        super(player);
    }
 
	@Override
	public void execute() {
		backup();
		player.setTeamColor(new Color((int)(Math.random() * 0x1000000)));
	}
}