package Adapter;
 
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import edu.ktu.signalrclient.FontLibrary;
import edu.ktu.signalrclient.Game;
import edu.ktu.signalrclient.Menu;
 
public class MyButtonUI {
 
	private final Rectangle button = new Rectangle(1025 / 4, 600 / 10);
 
	//WINWODW
	private int windowWidth;
	private int windowHeight;
	
	private FontLibrary fontLibrary;
 
	//BUTTON NAMES/BORDERS
	private static ArrayList<String> buttonNames;
	private static ArrayList<Rectangle> buttonBounds;
 
	public MyButtonUI(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		fontLibrary = new FontLibrary();
 
		//BUTTON NAME / BOUND LISTS
		buttonNames = new ArrayList<String>(List.of("PLAY", "SELECT HERO", "EXIT", "BACK"));
		buttonBounds = new ArrayList<Rectangle>();
	}
	// 0 100 200
 
 
	public void drawMenuButtonUpper(String s, float fontSize, Graphics g) {		
		g.setFont(fontLibrary.getSizedFont(fontSize));
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = calculateCenterVertical(txtSize);
		int posY = (int) ((windowHeight / 3) + g.getFontMetrics().getAscent());
		g.drawString(s, posX, posY);
		int borderX = calculateCenterVertical(button);
		int borderY = (int) ((windowHeight / 3) - g.getFontMetrics().getDescent());
		g.drawRect(borderX, borderY, button.width, button.height);
		buttonBounds.add(new Rectangle(borderX, borderY, button.width, button.height));
	}
 
	public void drawMenuButtonCenter(String s, float fontSize, Graphics g) {		
		g.setFont(fontLibrary.getSizedFont(fontSize));
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = calculateCenterVertical(txtSize);
		int posY = (int) ((windowHeight / 3) + g.getFontMetrics().getAscent() + 100);
		g.drawString(s, posX, posY);
		int borderX = calculateCenterVertical(button);
		int borderY = (int) ((windowHeight / 3) - g.getFontMetrics().getDescent() + 100);
		g.drawRect(borderX, borderY, button.width, button.height);
		buttonBounds.add(new Rectangle(borderX, borderY, button.width, button.height));
	}
	
	public void drawMenuButtonBottom(String s, float fontSize, Graphics g) {		
		g.setFont(fontLibrary.getSizedFont(fontSize));
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = calculateCenterVertical(txtSize);
		int posY = (int) ((windowHeight / 3) + g.getFontMetrics().getAscent() + 200);
		g.drawString(s, posX, posY);
		int borderX = calculateCenterVertical(button);
		int borderY = (int) ((windowHeight / 3) - g.getFontMetrics().getDescent() + 200);
		g.drawRect(borderX, borderY, button.width, button.height);
		buttonBounds.add(new Rectangle(borderX, borderY, button.width, button.height));
	}
	
	private int calculateCenterVertical(Rectangle2D element) {
		return (int) ((windowWidth / 2) - (element.getWidth() / 2));
	}
 
	public static ArrayList<String> getButtonNames() {
		return buttonNames;
	}
 
 
	public static ArrayList<Rectangle> getButtonBounds() {
		return buttonBounds;
	}
}