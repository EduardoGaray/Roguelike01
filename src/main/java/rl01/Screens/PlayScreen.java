package rl01.Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import rl01.Main.Creature;
import rl01.Main.EntityFactory;
import rl01.Main.FieldOfView;
import rl01.Main.Item;
import rl01.Main.PlayerAi;
import rl01.Main.Tile;
import rl01.Main.World;
import rl01.Main.WorldBuilder;

public class PlayScreen implements Screen {

	private World world;
	private Creature player;
	private FieldOfView fov;
	private int screenWidth;
	private int screenHeight;

	private Screen subscreen;

	private List<String> messages;

	public void displayOutput(AsciiPanel terminal) {

		int left = getPlayerX();
		int top = getPlayerY();

		displayTiles(terminal, left, top);
		displayMessages(terminal, messages);

		terminal.write(player.glyph(), player.x - left, player.y - top, player.color());

		String stats = String.format(" %3d/%3d hp %8s", player.hp(), player.maxHp(), hunger());
		terminal.write(stats, 1, 21);
		String level = String.format(" Floor: %3d", player.z + 1);
		terminal.write(level, 1, 22);
		if (subscreen != null)
			subscreen.displayOutput(terminal);

	}

	private String hunger() {
		if (player.food() < player.maxFood() * 0.1)
			return "Starving";
		else if (player.food() < player.maxFood() * 0.2)
			return "Hungry";
		else if (player.food() > player.maxFood() * 0.9)
			return "Stuffed";
		else if (player.food() > player.maxFood() * 0.8)
			return "Full";
		else
			return "";
	}

	public Screen respondToUserInput(KeyEvent key) {
		int left = getPlayerX();
		int top = getPlayerY();
		int level = player.level();
		if (player.level() > level)
			subscreen = new LevelUpScreen(player, player.level() - level);
		if (subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
		} else {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.moveBy(player, -1, 0, 0);
				break;
			case KeyEvent.VK_RIGHT:
				player.moveBy(player, 1, 0, 0);
				break;
			case KeyEvent.VK_UP:
				player.moveBy(player, 0, -1, 0);
				break;
			case KeyEvent.VK_DOWN:
				player.moveBy(player, 0, 1, 0);
				break;
			case KeyEvent.VK_Y:
				player.moveBy(player, -1, -1, 0);
				break;
			case KeyEvent.VK_U:
				player.moveBy(player, 1, -1, 0);
				break;
			case KeyEvent.VK_B:
				player.moveBy(player, -1, 1, 0);
				break;
			case KeyEvent.VK_N:
				player.moveBy(player, 1, 1, 0);
				break;
			case KeyEvent.VK_D:
				subscreen = new DropScreen(player);
				break;
			case KeyEvent.VK_E:
				subscreen = new EatScreen(player);
				break;
			case KeyEvent.VK_W:
				subscreen = new EquipScreen(player);
				break;
			case KeyEvent.VK_H:
				subscreen = new HelpScreen();
				break;
			case KeyEvent.VK_T:
				subscreen = new ThrowScreen(player, player.x - left, player.y - top);
				break;
			case KeyEvent.VK_X:
				subscreen = new ExamineScreen(player);
				break;
			case KeyEvent.VK_L:
				subscreen = new LookScreen(player, "Look Around", player.x - left, player.y - top);
				break;
			case KeyEvent.VK_G:
				player.pickup();
				break;
			case KeyEvent.VK_0:
				if (userIsTryingToExit())
					return userExits();
				else
					player.moveBy(player, 0, 0, -1);
				break;
			case KeyEvent.VK_1:
				player.moveBy(player, 0, 0, 1);
				break;
			case KeyEvent.VK_F:
		        if (player.weapon() == null || player.weapon().rangedAttackValue() == 0)
		         player.notify("You don't have a ranged weapon equiped.");
		        else
		         subscreen = new FireWeaponScreen(player,player.x - left,player.y - top); 
		        break;
			}
		}

		if (subscreen == null)
			world.update();
		System.out.println("Player x: " + player.maxHp());
		System.out.println("Player y: " + player.hp());

		if (player.hp() < 1)
			return new LoseScreen();

		return this;
	}

	public PlayScreen() {
		screenWidth = 80;
		screenHeight = 21;
		messages = new ArrayList<String>();
		createWorld();
		fov = new FieldOfView(world);
		EntityFactory creatureFactory = new EntityFactory(world);
		createCreatures(creatureFactory);
		createItems(creatureFactory);
	}

	private void createWorld() {
		world = new WorldBuilder(90, 31, 1).makeCaves().build();
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
		fov.update(player.x, player.y, player.z, player.visionRadius());
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;

				if (player.canSee(wx, wy, player.z))
					terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
				else
					terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);
			}
		}
	}

	private void createCreatures(EntityFactory creatureFactory) {
		player = creatureFactory.newPlayer(messages, fov);

		for (int i = 0; i < 10; i++) {
			creatureFactory.newFungus();
		}

		for (int i = 0; i < 20; i++) {
			creatureFactory.newBat();
		}

		for (int i = 0; i < 5; i++) {
			creatureFactory.newZombie(player);
		}
		
		for (int i = 0; i < 3; i++) {
			creatureFactory.newGoblin(player);
		}
	}

	private void createItems(EntityFactory factory) {
		for (int z = 0; z < world.depth(); z++) {
			for (int i = 0; i < world.width() * world.height() / 20; i++) {
				factory.newRock(z);
			}
		}
		
		factory.newVictoryItem(world.depth() - 1);
		factory.newEdibleWeapon(world.depth() - 1);
		factory.randomArmor(world.depth() - 1);
		factory.randomWeapon(world.depth() - 1);
		factory.randomPotion(world.depth() - 1);
	}

	private boolean userIsTryingToExit() {
		return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
	}

	private Screen userExits() {
		for (Item item : player.inventory().getItems()) {
			if (item != null && item.name().equals("teddy bear"))
				return new WinScreen();
		}
		return new LoseScreen();
	}

}
