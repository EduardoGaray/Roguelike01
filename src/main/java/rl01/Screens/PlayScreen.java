package rl01.Screens;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

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
	
	private List<String> messages;

	public void displayOutput(AsciiPanel terminal) {

		int left = getPlayerX();
		int top = getPlayerY();

		displayTiles(terminal, left, top);
		displayMessages(terminal, messages);

		terminal.write(player.glyph(), player.x - left, player.y - top, player.color());

		terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);

		String stats = String.format(" %3d/%3d hp", player.hp(), player.maxHp());
		terminal.write(stats, 1, 21);
		String level = String.format(" Floor: %3d", player.z+1);
		terminal.write(level, 1, 22);
		
	}

	

	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE: return new LoseScreen();
		case KeyEvent.VK_ENTER: return new WinScreen();
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_H: player.moveBy(-1, 0, 0); break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_L: player.moveBy(1, 0, 0); break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_K: player.moveBy(0, -1, 0); break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_J: player.moveBy(0, 1, 0); break;
		case KeyEvent.VK_Y: player.moveBy(-1, -1, 0); break;
		case KeyEvent.VK_U: player.moveBy(1, -1, 0); break;
		case KeyEvent.VK_B: player.moveBy(-1, 1, 0); break;
		case KeyEvent.VK_N: player.moveBy(1, 1, 0); break;
		case KeyEvent.VK_ADD: player.moveBy( 0, 0, 1); break;
		case KeyEvent.VK_SUBTRACT : player.moveBy( 0, 0, -1); break;
		} 
		world.update();
		return this;
	}
	
	public PlayScreen(){
		screenWidth = 80;
		screenHeight = 21;
		messages = new ArrayList<String>();
		createWorld();

		CreatureFactory creatureFactory = new CreatureFactory(world);
		createCreatures(creatureFactory);
	}

	private void createWorld() {
		world = new WorldBuilder(90, 31, 3).makeCaves().build();
	}

	public int getPlayerX() {
		return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
	}

	public int getPlayerY() {
		return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
	}
		
	private void displayMessages(AsciiPanel terminal, List<String> messages) {
		int top = screenHeight - messages.size();
		for (int i = 0; i < messages.size(); i++) {
			terminal.writeCenter(messages.get(i), top + i);
		}
		messages.clear();
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		// display all the tiles, then loop through creatures and display them if in
		// range
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;
				int wz = player.z;
				terminal.write(world.glyph(wx, wy, wz), x, y, world.color(wx, wy, wz));
			}
		} ///// Up until this point we have just displayed the world tiles, now we will
			///// loop through creatures, check if in bounds, and display

		for (Creature c : world.creatures) {
			if ((c.x >= left && c.x < left + screenWidth) && (c.y >= top && c.y < top + screenHeight) && c.z == player.z) {
				terminal.write(c.glyph(), c.x - left, c.y - top, c.color());
			}
		}
	}

	private void createCreatures(CreatureFactory creatureFactory){
	    player = creatureFactory.newPlayer(messages);
	    
	    for (int i = 0; i < 8; i++){
	        creatureFactory.newFungus();
	    }
	}

}
