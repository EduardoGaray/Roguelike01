package rl01.Main;

import java.util.List;

import asciiPanel.AsciiPanel;
import java.util.Random;

public class EntityFactory {
	private World world;

	public EntityFactory(World world) {
		this.world = world;
	}

	public Creature newPlayer(List<String> messages) {
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5, "player", 9, "Player");
		world.addAtEmptyLocation(player, 0);
		System.out.println("Spawning player on level: 0");
		new PlayerAi(player, messages);
		return player;
	}

	public Creature newFungus() {
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0, "creature", 9, "Fungi");
		Random rand = new Random();
		int randomDepth = rand.nextInt(world.depth());
		world.addAtEmptyLocation(fungus, randomDepth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat() {
		Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 5, 0, "creature", 9, "Cave Bat");
		Random rand = new Random();
		int randomDepth = rand.nextInt(world.depth());
		world.addAtEmptyLocation(bat, randomDepth);
		new BatAi(bat);
		return bat;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newDagger(int depth) {
		Item item = new Item(')', AsciiPanel.white, "dagger");
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		Item item = new Item(')', AsciiPanel.brightWhite, "sword");
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "staff");
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newLightArmor(int depth) {
		Item item = new Item('[', AsciiPanel.green, "tunic");
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newMediumArmor(int depth) {
		Item item = new Item('[', AsciiPanel.white, "chainmail");
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newHeavyArmor(int depth) {
		Item item = new Item('[', AsciiPanel.brightWhite, "platemail");
		item.modifyDefenseValue(6);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item randomWeapon(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newDagger(depth);
		case 1:
			return newSword(depth);
		default:
			return newStaff(depth);
		}
	}

	public Item randomArmor(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newLightArmor(depth);
		case 1:
			return newMediumArmor(depth);
		default:
			return newHeavyArmor(depth);
		}
	}

	public Item newEdibleWeapon(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "baguette");
		item.modifyAttackValue(3);
		item.modifyFoodValue(50);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

}
