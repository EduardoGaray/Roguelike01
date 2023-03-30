package rl01.Main;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {

	FLOOR((char) 250, AsciiPanel.yellow, false), WALL((char) 177, AsciiPanel.yellow, true), BOUNDS('x', AsciiPanel.brightBlack, true),
	STAIRS_DOWN('>', AsciiPanel.white, false),STAIRS_UP('<', AsciiPanel.white, false), UNKNOWN(' ', AsciiPanel.white, false);

	private char glyph;

	public char glyph() {
		return glyph;
	}

	private Color color;

	public Color color() {
		return color;
	}

	Tile(char glyph, Color color, boolean impassable) {
		this.glyph = glyph;
		this.color = color;
		this.impassable = impassable;
	}
	
	private boolean impassable;
	public boolean impassable() {
		return impassable;
	}
	
	public boolean isDiggable() {
	    return this == Tile.WALL;
	}
	
	//Warning if the tile is unknow is considered ground too, this causes problems with fungi spread, needs a solution
	public boolean isGround() {
	    return this != WALL && this != BOUNDS && this != UNKNOWN;
	}
	
}
