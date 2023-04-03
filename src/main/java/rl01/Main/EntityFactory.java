package rl01.Main;

import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import java.util.Random;

public class EntityFactory {
	private World world;

	public EntityFactory(World world) {
		this.world = world;
	}

	public Creature newPlayer(List<String> messages, FieldOfView fov) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 50, 50, 10, 50, "player", 10, "Player", effects);
		world.addAtEmptyLocation(player, 0);
		System.out.println("Spawning player on level: 0");
		new PlayerAi(player, world, messages, fov);
		return player;
	}

	public Creature newFungus() {
		List<Effect> effects = new ArrayList<Effect>();
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 10, 0, 0, "creature", 1, "Fungi", effects);
		Random rand = new Random();
		int randomDepth = rand.nextInt(world.depth());
		world.addAtEmptyLocation(fungus, randomDepth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat() {
		List<Effect> effects = new ArrayList<Effect>();
		Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 15, 0, 0, "creature", 18, "Cave Bat", effects);
		Random rand = new Random();
		int randomDepth = rand.nextInt(world.depth());
		world.addAtEmptyLocation(bat, randomDepth);
		new BatAi(bat);
		return bat;
	}

	public Creature newZombie(Creature player) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature zombie = new Creature(world, 'z', AsciiPanel.white, 50, 50, 10, 0, "creature", 5, "Zombie", effects);
		Random rand = new Random();
		int randomDepth = rand.nextInt(world.depth());
		world.addAtEmptyLocation(zombie, randomDepth);
		new ZombieAi(zombie, player);
		return zombie;
	}

	public Creature newGoblin(Creature player) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature goblin = new Creature(world, 'g', AsciiPanel.brightGreen, 66, 66, 5, 0, "creature", 10, "Goblin", effects);
		Random rand = new Random();
		int randomDepth = rand.nextInt(world.depth());
		randomDepth = rand.nextInt(world.depth());
		goblin.equip(randomWeapon(randomDepth));
		randomDepth = rand.nextInt(world.depth());
		goblin.equip(randomArmor(randomDepth));
		randomDepth = rand.nextInt(world.depth());
		world.addAtEmptyLocation(goblin, randomDepth);
		new GoblinAi(goblin, player);
		return goblin;
	}

	public Item newRock(int depth) {
		Item item = new Item(',', AsciiPanel.yellow, "rock");
		item.modifyThrownAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
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

	public Item newEdibleWeapon(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "baguette");
		item.modifyAttackValue(3);
		item.modifyFoodValue(50);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newBow(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "bow");
		item.modifyAttackValue(1);
		item.modifyRangedAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newPotionOfHealth(int depth){
	    Item item = new Item('!', AsciiPanel.white, "health potion");
	    item.setQuaffEffect(new Effect(1){
	        public void start(Creature creature){
	            if (creature.hp() == creature.maxHp())
	                return;
	                                
	            creature.modifyHp(15);
	            creature.doAction("look healthier");
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfPoison(int depth){
	    Item item = new Item('!', AsciiPanel.white, "poison potion");
	    item.setQuaffEffect(new Effect(20){
	        public void start(Creature creature){
	            creature.doAction("look sick");
	        }
	                        
	        public void update(Creature creature){
	            super.update(creature);
	            creature.modifyHp(-1);
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfWarrior(int depth){
	    Item item = new Item('!', AsciiPanel.white, "warrior's potion");
	    item.setQuaffEffect(new Effect(20){
	        public void start(Creature creature){
	            creature.buffAttackValue(5);
	            creature.buffAttackValue(5);
	            creature.doAction("look stronger");
	        }
	        public void end(Creature creature){
	            creature.buffAttackValue(-5);
	            creature.buffAttackValue(-5);
	            creature.doAction("look less strong");
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item randomPotion(int depth){
        switch ((int)(Math.random() * 3)){
        case 0: return newPotionOfHealth(depth);
        case 1: return newPotionOfPoison(depth);
        default: return newPotionOfWarrior(depth);
        }
}

	public Item randomWeapon(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newDagger(depth);
		case 1:
			return newSword(depth);
		case 2:
			return newBow(depth);
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
	
}
