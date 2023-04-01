package Adapter;
 
import java.awt.Graphics;
 
public class ButtonUIAdapter implements ElementUI{
 
	private MyButtonUI adaptee;
 
	public ButtonUIAdapter(MyButtonUI buttonui) {
		this.adaptee = buttonui;
	}
 
	public void draw(String text, float fontSize, Graphics g, String type) {
		switch (type) {
		case "upper":
			adaptee.drawMenuButtonUpper(text, fontSize, g);
			break;
		case "center":
			adaptee.drawMenuButtonCenter(text, fontSize, g);
			break;
		case "bottom":
			adaptee.drawMenuButtonBottom(text, fontSize, g);
			break;
		}
	}
 
}