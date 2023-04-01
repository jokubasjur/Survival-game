package edu.ktu.signalrclient;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLibrary {
	
	private File fontFile;
	private Font font;
	private Font sizedFont;
	
	public FontLibrary() {
		loadFontFromFile("Retro Gaming.ttf");
	}
	
	private void loadFontFromFile(String fileName) {
		font = null;
		fontFile = new File("Retro Gaming.ttf");
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Font getSizedFont(float fontSize) {
		sizedFont = font.deriveFont(fontSize);
		return sizedFont;
	}
}
