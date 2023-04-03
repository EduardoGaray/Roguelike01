package rl01.Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import asciiPanel.AsciiPanel;
import java.util.Random;

public class EntityFactory {
	private World world;

	  private Map<String, Color> potionColors;
	    private List<String> potionAppearances;
	 
	    public EntityFactory(World world){
	        this.world = world;
	  
	        setUpPotionAppearances();
	    }
	    

	public Creature newPlayer(List<String> messages, FieldOfView fov) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 50, 50, 10, 50, 0, 0, "player", 10, 0, 0, "Player", effects);
		world.addAtEmptyLocation(player, 0);
		System.out.println("Spawning player on level: 0");
		new PlayerAi(player, world, messages, fov);
		return player;
	}

	public Creature newFungus(int depth) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 10, 0, 0, 0, 0, "creature", 1, 0, 0, "Fungi", effects);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 15, 0, 0, 0, 0, "creature", 18, 0, 0, "Cave Bat", effects);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Creature newZombie(Creature player, int depth) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature zombie = new Creature(world, 'z', AsciiPanel.white, 50, 50, 10, 0, 0, 0, "creature", 5, 0, 0, "Zombie", effects);
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}

	public Creature newGoblin(Creature player, int depth) {
		List<Effect> effects = new ArrayList<Effect>();
		Creature goblin = new Creature(world, 'g', AsciiPanel.brightGreen, 66, 66, 5, 0, 0, 0, "creature", 10, 0, 0, "Goblin", effects);
		Random rand = new Random();
		int random = rand.nextInt(world.depth());
		random = rand.nextInt(world.depth());
		goblin.equip(randomWeapon(random));
		random = rand.nextInt(world.depth());
		goblin.equip(randomArmor(random));
		random = rand.nextInt(world.depth());
		world.addAtEmptyLocation(goblin, depth);
		new GoblinAi(goblin, player);
		return goblin;
	}

	public Item newRock(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item(',', AsciiPanel.yellow, "rock",writtenSpells, null );
		item.modifyThrownAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newVictoryItem(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear",writtenSpells, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newDagger(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item(')', AsciiPanel.white, "dagger",writtenSpells, null);
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item(')', AsciiPanel.brightWhite, "sword",writtenSpells, null);
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item(')', AsciiPanel.yellow, "staff",writtenSpells, null);
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newLightArmor(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item('[', AsciiPanel.green, "tunic",writtenSpells, null);
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newMediumArmor(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item('[', AsciiPanel.white, "chainmail",writtenSpells, null);
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newHeavyArmor(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item('[', AsciiPanel.brightWhite, "platemail",writtenSpells, null);
		item.modifyDefenseValue(6);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newEdibleWeapon(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item(')', AsciiPanel.yellow, "baguette",writtenSpells, null);
		item.modifyAttackValue(3);
		item.modifyFoodValue(50);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newBow(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
		Item item = new Item(')', AsciiPanel.yellow, "bow",writtenSpells, null);
		item.modifyAttackValue(1);
		item.modifyRangedAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newPotionOfHealth(int depth){
		List<Spell> writtenSpells = new ArrayList<Spell>();
		String appearance = potionAppearances.get(0);
		final Item item = new Item('!', potionColors.get(appearance), "health potion", writtenSpells, appearance);
	    item.setQuaffEffect(new Effect(1){
	        public void start(Creature creature){
	            if (creature.hp() == creature.maxHp())
	                return;
	                                
	            creature.modifyHp(15, appearance);
	            creature.doAction("look healthier");
	            creature.learnName(item);
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfPoison(int depth){
		List<Spell> writtenSpells = new ArrayList<Spell>();
		String appearance = potionAppearances.get(0);
	    final Item item = new Item('!', potionColors.get(appearance), "poison potion", writtenSpells, appearance);
	    item.setQuaffEffect(new Effect(20){
	        public void start(Creature creature){
	            creature.doAction("look sick");
	            creature.learnName(item);
	        }
	                        
	        public void update(Creature creature){
	            super.update(creature);
	            creature.modifyHp(-1, "Killed by poison.");
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfWarrior(int depth){
		List<Spell> writtenSpells = new ArrayList<Spell>();
		String appearance = potionAppearances.get(0);
	    final Item item = new Item('!', potionColors.get(appearance), "warrior's potion", writtenSpells, appearance);
	    item.setQuaffEffect(new Effect(20){
	        public void start(Creature creature){
	            creature.buffAttackValue(5);
	            creature.buffAttackValue(5);
	            creature.doAction("look stronger");
	            creature.learnName(item);
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
	
	
	public Item newWhiteMagesSpellbook(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
        Item item = new Item('+', AsciiPanel.brightWhite, "white mage's spellbook", writtenSpells, null);
        item.addWrittenSpell("minor heal", 4, new Effect(1){
            public void start(Creature creature){
                if (creature.hp() == creature.maxHp())
                    return;
                
                creature.modifyHp(20, "Killed by overeating.");
                creature.doAction("look healthier");
            }
        });
        
        item.addWrittenSpell("major heal", 8, new Effect(1){
            public void start(Creature creature){
                if (creature.hp() == creature.maxHp())
                    return;
                
                creature.modifyHp(50, "Killed by overeating.");
                creature.doAction("look healthier");
            }
        });
        
        item.addWrittenSpell("slow heal", 12, new Effect(50){
            public void update(Creature creature){
                super.update(creature);
                creature.modifyHp(2, "Killed by overeating.");
            }
        });

        item.addWrittenSpell("inner strength", 16, new Effect(50){
            public void start(Creature creature){
                creature.buffAttackValue(2);
                creature.modifyRegenHpPer1000(10);
                creature.modifyRegenManaPer1000(-10);
                creature.doAction("seem to glow with inner strength");
            }
            public void update(Creature creature){
                super.update(creature);
                if (Math.random() < 0.25)
                    creature.modifyHp(1, "Killed by debuff.");
            }
            public void end(Creature creature){
                creature.buffAttackValue(-2);
                creature.modifyRegenHpPer1000(-10);
                creature.modifyRegenManaPer1000(10);
            }
        });
        
        world.addAtEmptyLocation(item, depth);
        return item;
    }
	
	
	public Item newBlueMagesSpellbook(int depth) {
		List<Spell> writtenSpells = new ArrayList<Spell>();
        Item item = new Item('+', AsciiPanel.brightBlue, "blue mage's spellbook", writtenSpells, null );

        item.addWrittenSpell("blood to mana", 1, new Effect(1){
            public void start(Creature creature){
                int amount = Math.min(creature.hp() - 1, creature.maxMana() - creature.mana());
                creature.modifyHp(-amount, "Killed by spell.");
                creature.modifyMana(amount);
            }
        });
        
        item.addWrittenSpell("blink", 6, new Effect(1){
            public void start(Creature creature){
                creature.doAction("fade out");
                
                int mx = 0;
                int my = 0;
                
                do
                {
                    mx = (int)(Math.random() * 11) - 5;
                    my = (int)(Math.random() * 11) - 5;
                }
                while (!creature.canEnter(creature.x+mx, creature.y+my, creature.z)
                        && creature.canSee(creature.x+mx, creature.y+my, creature.z));
                
                creature.moveBy(creature, mx, my, 0);
                
                creature.doAction("fade in");
            }
        });
        
        item.addWrittenSpell("summon bats", 11, new Effect(1){
            public void start(Creature creature){
                for (int ox = -1; ox < 2; ox++){
                    for (int oy = -1; oy < 2; oy++){
                        int nx = creature.x + ox;
                        int ny = creature.y + oy;
                        if (ox == 0 && oy == 0 
                                || creature.creature(nx, ny, creature.z) != null)
                            continue;
                        
                        Creature bat = newBat(0);
                        
                        if (!bat.canEnter(nx, ny, creature.z)){
                            world.remove(bat);
                            continue;
                        }
                        
                        bat.x = nx;
                        bat.y = ny;
                        bat.z = creature.z;
                        
                        creature.summon(bat);
                    }
                }
            }
        });
        
        item.addWrittenSpell("detect creatures", 16, new Effect(75){
            public void start(Creature creature){
                creature.doAction("look far off into the distance");
                creature.modifyDetectCreatures(1);
            }
            public void end(Creature creature){
                creature.modifyDetectCreatures(-1);
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
	
	private void setUpPotionAppearances(){
        potionColors = new HashMap<String, Color>();
        potionColors.put("red potion", AsciiPanel.brightRed);
        potionColors.put("yellow potion", AsciiPanel.brightYellow);
        potionColors.put("green potion", AsciiPanel.brightGreen);
        potionColors.put("cyan potion", AsciiPanel.brightCyan);
        potionColors.put("blue potion", AsciiPanel.brightBlue);
        potionColors.put("magenta potion", AsciiPanel.brightMagenta);
        potionColors.put("dark potion", AsciiPanel.brightBlack);
        potionColors.put("grey potion", AsciiPanel.white);
        potionColors.put("light potion", AsciiPanel.brightWhite);

        potionAppearances = new ArrayList<String>(potionColors.keySet());
        Collections.shuffle(potionAppearances);
    }
	
}
