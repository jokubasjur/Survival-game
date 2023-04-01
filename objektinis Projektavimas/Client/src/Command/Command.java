package Command;
 
import java.awt.Color;
 
import edu.ktu.signalrclient.Player;
 
 
public abstract class Command {
 
	public Player player;
    private Color backup;
 
    Command(Player player) {
        this.player = player;
    }
 
    void backup() {
        backup = player.getTeamColor();
    }
 
    public void undo() {
        player.setTeamColor(backup);
    }
 
    public abstract void execute();
}
