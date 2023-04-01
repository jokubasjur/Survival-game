package edu.ktu.signalrclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Adapter.*;
import edu.ktu.signalrclient.Game.STATE;

public class Menu {
	
	//WINWODW
	private int windowWidth;
	private int windowHeight;
	
	//BUTTON
	private int buttonWidth;
	private int buttonHeight;
	private Rectangle button;
	private int marginBottom;
	
	//HERO ICONS / NAMES
	private Rectangle icon;
	private ArrayList<Rectangle> heroIcons;
	private ArrayList<String> heroNames;
	private ArrayList<Color> heroIconColors;
	private int marginLeft;
	
	//BUTTON NAMES/BORDERS
	private ArrayList<String> buttonNames;
	private ArrayList<Rectangle> buttonBounds;
	private boolean firstInit = false;
	private boolean firstInitIcon = false;
	
	//FONT LIB
	private FontLibrary fontLibrary;
	
	//FONT SIZES
	private static float buttonFontSize = 30f;
	private static float headerFontSize = 60f;
	
	private StringUIAdapter stringUIAdapter;
	private ButtonUIAdapter buttonUIAdapter;
	
	public Menu(int windowWidth, int windowHeight) {
		
		//WINDOW
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		stringUIAdapter = new StringUIAdapter(new StringUI(windowWidth, windowHeight));
		buttonUIAdapter = new ButtonUIAdapter(new MyButtonUI(windowWidth, windowHeight));
		
		//BUTTON
		buttonWidth = (windowWidth / 4);
		buttonHeight = windowHeight / 10;
		button = new Rectangle(buttonWidth, buttonHeight);
		marginBottom = 100;
		
		//HERO ICONS / NAMES
		icon = new Rectangle(100 ,100);
		heroNames = new ArrayList<String>(List.of("DRAGON SLAYER", "PLAGUE DOCTOR"));
		heroIconColors = new ArrayList<Color>(List.of(new Color(230, 126, 34), new Color(30, 130, 76)));
		heroIcons = new ArrayList<Rectangle>();
		marginLeft = 100;
		
		//BUTTON NAME / BOUND LISTS
		buttonNames = new ArrayList<String>(List.of("PLAY", "SELECT HERO", "EXIT", "BACK"));
		buttonBounds = new ArrayList<Rectangle>();
		
        //FONT LIB
        fontLibrary = new FontLibrary();
        
        //INIT
		firstInit = true;
		firstInitIcon = true;
	}	
	
	
	public ArrayList<String> getHeroNames() {
		return heroNames;
	}


	public ArrayList<Rectangle> getHeroIcons() {
		return heroIcons;
	}


	public void render(Graphics g, STATE State) {
		
		g.setFont(fontLibrary.getSizedFont(headerFontSize));
		g.setColor(Color.BLACK);
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.WHITE);
        
		if (State == Game.STATE.MENU) {
			stringUIAdapter.draw("Death Rounds", headerFontSize, g, "center");
			/*for (int i = 0; i < buttonNames.size() - 1; i++) {
				drawMenuButton(buttonNames.get(i), button, i, g);
			}*/
			buttonUIAdapter.draw(buttonNames.get(0), 30f, g, "upper");
			buttonUIAdapter.draw(buttonNames.get(1), 30f, g, "center");
			buttonUIAdapter.draw(buttonNames.get(2), 30f, g, "bottom");
			if (firstInit) {
				buttonBounds = MyButtonUI.getButtonBounds();
				//System.out.println(buttonBounds);
			}
			firstInit = false;
		} else if (State == Game.STATE.HERO_SELECT) {
			stringUIAdapter.draw("Select Hero", headerFontSize, g, "center");
			for (int i = 0; i < heroNames.size(); i++) {
				g.setColor(heroIconColors.get(i));
				drawHeroIcon(heroNames.get(i), icon, i, g);
			}
			firstInitIcon = false;
			g.setColor(Color.WHITE);
			buttonUIAdapter.draw(buttonNames.get(3), 30f, g, "bottom");
		}
		
		
	}
	
	public void drawHeroIcon(String s, Rectangle rec, int count, Graphics g) {
		
		int borderX =  0;
		int countMod = 1;
		if (count % 2 != 0) {
			countMod = 0;
		}
		int posX = 0;
		if (count % 2 == 0) {
			borderX = (int) (calculateCenterVertical(rec) - (marginLeft + 50) * (count + countMod));
			posX = (int) (calculateCenterVertical(rec) - (marginLeft + 50) * (count + countMod));
		} else {
			borderX = (int) (calculateCenterVertical(rec) + (marginLeft + 50) * (count + countMod));
			posX = (int) (calculateCenterVertical(rec) + (marginLeft + 50) * (count + countMod));
		}
		int posY = (int) ((windowHeight / 3));
		
		g.setFont(fontLibrary.getSizedFont(10));
		g.drawString(s, posX, posY);
		
		int borderY = (int) ((windowHeight / 2) - marginBottom / 2);
		g.fillRect(borderX, borderY, rec.width, rec.height);
		
		//ON FIRST INIT - ADD ALL BUTTON BOUNDS - FOR MOUSE CLICKS IN GAME CLASS.
		if (firstInitIcon) {
			heroIcons.add(new Rectangle(borderX, borderY, rec.width, rec.height));
		}
	}
	
	
	
	private int calculateCenterVertical(Rectangle2D element) {
		return (int) ((windowWidth / 2) - (element.getWidth() / 2));
	}


	public ArrayList<String> getButtonNames() {
		return buttonNames;
	}


	public ArrayList<Rectangle> getButtonBounds() {
		return buttonBounds;
	}
	
	
}
