package edu.ktu.signalrclient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {
	private Map<String, Image> tiles;
	
	
	public SpriteLibrary() {
		tiles = new HashMap<>();
		loadSpritesFromDisk();
	}
	private void loadSpritesFromDisk() {
		loadTiles();
	}

	private void loadTiles() {
		BufferedImage image = new BufferedImage(Game.spriteSize, Game.spriteSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = image.createGraphics();
		graphics2d.setColor(new Color(179, 213, 214, 30));
		graphics2d.drawRect(0, 0, Game.spriteSize, Game.spriteSize);
		graphics2d.dispose();
		
		tiles.put("default", image);
	}
	
	public Image getTiles(String name) {
		return tiles.get(name);
	}
}
