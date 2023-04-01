package edu.ktu.signalrclient;

import java.util.Arrays;


public class GameMap {
	private Tile[][] tiles;
	
	public GameMap(int size, SpriteLibrary spriteLibrary) {
		tiles = new Tile[size][size];
		initializeTiles(spriteLibrary);
	}

	private void initializeTiles(SpriteLibrary spriteLibrary) {
		for(Tile[] row: tiles) {
			Arrays.fill(row, new Tile(spriteLibrary));
		}
		
	}
	
	public Tile[][] getTiles(){
		return tiles;
	}
}
