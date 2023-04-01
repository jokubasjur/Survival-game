package Adapter;
 
import java.awt.Graphics;
 
import edu.ktu.signalrclient.Game.STATE;
 
public interface ElementUI {
 
	void draw(String text, float textSize, Graphics g, String type);
}