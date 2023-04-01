package Adapter;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.geom.Rectangle2D;
 
import edu.ktu.signalrclient.FontLibrary;
import edu.ktu.signalrclient.Game;
 
public class StringUI {	
 
	//FONT LIB
	private FontLibrary fontLibrary;
 
	//WINWODW
	private int windowWidth;
	private int windowHeight;
 
	public StringUI(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		fontLibrary = new FontLibrary();
	}
 
	public void drawCenter(String s, Graphics g){
		g.setColor(Color.WHITE);
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = calculateCenterVertical(txtSize);
		int posY = (int) ((windowHeight - txtSize.getHeight()) / 8 + g.getFontMetrics().getAscent());
		g.drawString(s, posX, posY);
	}
 
	public void drawUpperLeftCornerUpper(String s, float fontSize, Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(fontLibrary.getSizedFont(fontSize));
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = (int) (windowWidth - (windowWidth - 40));
		int posY = (int) (txtSize.getHeight() * 3);
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperLeftCornerDown(String s, float fontSize, Graphics g) {
		g.setFont(fontLibrary.getSizedFont(fontSize));
		g.setColor(Color.RED);
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = (int) (windowWidth - (windowWidth - 40));
		int posY = (int) (txtSize.getHeight() * 3);
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperLeftCornerDownSecond(String s, float fontSize, Graphics g) {
		g.setFont(fontLibrary.getSizedFont(fontSize));
		g.setColor(Color.GREEN);
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = (int) (windowWidth - (windowWidth - 40));
		int posY = (int) (txtSize.getHeight() * 5 + 5);
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperLeftCornerDownThird(String s, float fontSize, Graphics g) {
		g.setFont(fontLibrary.getSizedFont(fontSize));
		g.setColor(Color.YELLOW);
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = (int) (windowWidth - (windowWidth - 40));
		int posY = (int) (txtSize.getHeight() * 6 + 8);
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperRightCornerUpper(String s, float fontSize, Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(fontLibrary.getSizedFont(fontSize));
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = (int) (windowWidth - txtSize.getWidth() - 40);
		int posY = (int) (txtSize.getHeight() * 3);
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperRightCornerDown(String s, float fontSize, Graphics g) {
		g.setFont(fontLibrary.getSizedFont(fontSize));
		g.setColor(Color.RED);
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = (int) (windowWidth - (txtSize.getWidth() / 3) - txtSize.getWidth() - 15);
		int posY = (int) (txtSize.getHeight() * 3);
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperRightCornerDownSecond(String s, float fontSize, Graphics g) {
		g.setFont(fontLibrary.getSizedFont(fontSize));
		g.setColor(Color.GREEN);
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = (int) (windowWidth - (txtSize.getWidth() / 3) - txtSize.getWidth() - 23);
		int posY = (int) (txtSize.getHeight() * 5 + 5);
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperCenter(String s, float fontSize, Graphics g) {
		g.setColor(Color.RED);
		g.setFont(fontLibrary.getSizedFont(fontSize));
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = calculateCenterVertical(txtSize);
		int posY = (int) ((windowHeight - txtSize.getHeight()) / 18 + g.getFontMetrics().getAscent());
		g.drawString(s, posX, posY);
	}
	
	public void drawUpperCenterUnder(String s, float fontSize, Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(fontLibrary.getSizedFont(fontSize));
		Rectangle2D txtSize = g.getFontMetrics().getStringBounds(s, g);
		int posX = calculateCenterVertical(txtSize);
		int posY = (int) ((windowHeight - txtSize.getHeight()) / 18 + g.getFontMetrics().getAscent());
		g.drawString(s, posX, posY);
	}
 
	private int calculateCenterVertical(Rectangle2D element) {
		return (int) ((windowWidth / 2) - (element.getWidth() / 2));
	}
}