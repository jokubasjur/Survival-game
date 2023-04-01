package Adapter;
 
import java.awt.Graphics;
 
import edu.ktu.signalrclient.Game.STATE;
 
public class StringUIAdapter implements ElementUI {
 
 
	private StringUI adaptee;
 
	public StringUIAdapter(StringUI stringui) {
		this.adaptee = stringui;
	}
 
	public void draw(String text, float fontSize, Graphics g, String type) {
		switch (type) {
			case "center":
				adaptee.drawCenter(text, g);
				break;
			case "upperLeftConerUp":
				adaptee.drawUpperLeftCornerUpper(text, fontSize, g);
				break;
			case "upperLeftConerDown":
				adaptee.drawUpperLeftCornerDown(text, fontSize, g);
				break;
			case "upperLeftCornerDownSecond": // POINTS
				adaptee.drawUpperLeftCornerDownSecond(text, fontSize, g);
				break;
			case "upperLeftCornerDownThird": // BULLET MODE
				adaptee.drawUpperLeftCornerDownThird(text, fontSize, g);
				break;
			case "upperRightConerUp":
				adaptee.drawUpperRightCornerUpper(text, fontSize, g);
				break;
			case "upperRightConerDown":
				adaptee.drawUpperRightCornerDown(text, fontSize, g);
				break;
			case "upperRightCornerDownSecond": // TEAMMATE POINTS
				adaptee.drawUpperRightCornerDownSecond(text, fontSize, g);
				break;
			case "upperCenter":
				adaptee.drawUpperCenter(text, fontSize, g);
				break;
			case "upperCenterUnder":
				adaptee.drawUpperCenterUnder(text, fontSize, g);
				break;
		}
	}
}