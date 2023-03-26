package rl01.Screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import rl01.Main.Creature;
import rl01.Main.CreatureFactory;
import rl01.Main.World;
import rl01.Main.WorldBuilder;

public class PlayScreen implements Screen {

	private World world;
	private Creature player;
	private int screenWidth;
	private int screenHeight;

	public void displayOutput(AsciiPanel terminal) {
		terminal.write("You are having fun.", 1, 1);
		terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);

		int left = getPlayerX();
		int top = getPlayerY();

		displayTiles(terminal, left, top);
		

	}

	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			return new LoseScreen();
		case KeyEvent.VK_ENTER:
			return new WinScreen();
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_H:
			moveBy(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_L:
			moveBy(1, 0);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_K:
			moveBy(0, -1);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_J:
			moveBy(0, 1);
			break;
		case KeyEvent.VK_Y:
			moveBy(-1, -1);
			break;
		case KeyEvent.VK_U:
			moveBy(1, -1);
			break;
		case KeyEvent.VK_B:
			moveBy(-1, 1);
			break;
		case KeyEvent.VK_N:
			moveBy(1, 1);
			break;
		}

		return this;
	}

	public PlayScreen() {
		screenWidth = 80;
		screenHeight = 21;
		createWorld();
		
		CreatureFactory creatureFactory = new CreatureFactory(world);
		createCreatures(creatureFactory);

	}

	private void createWorld() {
		world = new WorldBuilder(90, 31).makeCaves().build();
	}

	public int getPlayerX() {
		return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
	}

	public int getPlayerY() {
		return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;

				Creature creature = world.creature(wx, wy);
				if (creature != null)
					terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
				else
					terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));

			}
		}
	}

	private void moveBy(int mx, int my) {
		player.x = Math.max(0, Math.min(player.x + mx, world.width() - 1));
		player.y = Math.max(0, Math.min(player.y + my, world.height() - 1));
	}
	
	private void createCreatures(CreatureFactory creatureFactory){
	    player = creatureFactory.newPlayer();
	  
	    for (int i = 0; i < 8; i++){
	        creatureFactory.newFungus();
	    }
	}

}
