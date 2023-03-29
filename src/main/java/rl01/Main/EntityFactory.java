package rl01.Main;

import java.util.List;

import asciiPanel.AsciiPanel;
import java.util.Random;

public class EntityFactory {
	private World world;

	public EntityFactory(World world) {
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages){
	    Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5, "player", 9, "asfagag");
	    world.addAtEmptyLocation(player,0);
	    System.out.println("Spawning player on level: 0");
	    new PlayerAi(player, messages);
	    return player;
	}
	
	public Creature newFungus(){
		 Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0, "creature", 1, "Fungi");
		 Random rand = new Random();
		 int randomDepth = rand.nextInt(world.depth());
		 world.addAtEmptyLocation(fungus,randomDepth);
		 new FungusAi(fungus, this);
		 return fungus;
	}
	
	public Creature newBat(){
	    Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 5, 0, "creature", 20, "Cave Bat");
	    Random rand = new Random();
		int randomDepth = rand.nextInt(world.depth());
	    world.addAtEmptyLocation(bat, randomDepth);
	    new BatAi(bat);
	    return bat;
	}
	
	public Item newRock(int depth){
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }
	
	public Item newVictoryItem(int depth){
        Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
        world.addAtEmptyLocation(item, depth);
        return item;
    }

}
