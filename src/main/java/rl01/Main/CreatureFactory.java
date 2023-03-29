package rl01.Main;

import java.util.List;

import asciiPanel.AsciiPanel;
import java.util.Random;

public class CreatureFactory {
	private World world;

	public CreatureFactory(World world) {
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages){
	    Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5, "player", 9);
	    world.addAtEmptyLocation(player,0);
	    System.out.println("Spawning player on level: 0");
	    new PlayerAi(player, messages);
	    return player;
	}
	
	public Creature newFungus(){
		 Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0, "creature", 9);
		 Random rand = new Random();
		 int randomDepth = rand.nextInt(world.depth());
		 System.out.println("Spawning fungus on level: " + randomDepth);
		 world.addAtEmptyLocation(fungus,randomDepth);
		 new FungusAi(fungus, this);
		 return fungus;
	}
}
