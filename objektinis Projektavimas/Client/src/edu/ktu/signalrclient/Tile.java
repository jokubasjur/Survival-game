package edu.ktu.signalrclient;

import java.awt.Image;

public class Tile {
	private Image sprite;
	
	public Tile(SpriteLibrary spriteLibrary) {
		this.sprite = spriteLibrary.getTiles("default");
	}
	
	public Image getSprite() {
		return sprite;
	}
}
