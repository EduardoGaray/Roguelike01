package rl01.Main;

import java.util.Map;

public class CreatureAi {
	protected Creature creature;

	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}
	
	public void onEnter(int x, int y, int z, Tile tile){
	    if (tile.isGround() && creature.canEnter(x, y, z)){
	         creature.x = x;
	         creature.y = y;
	         creature.z = z;
	    } else {
	         //creature.doAction("bump into a wall");
	    }
	}
	
	public void wander(){
	    int mx = (int)(Math.random() * 3) - 1;
	    int my = (int)(Math.random() * 3) - 1;
	    
	    Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);
	    
	    if (other != null && other.glyph() == creature.glyph() && !other.canEnter(creature.x + mx, creature.y + my, creature.z))
	        return;
	    else
	        creature.moveBy(creature, mx, my, 0);
	}

	public void onUpdate() { }

	public void onNotify(String format) { }

	public boolean canSee(int wx, int wy, int wz) {
        if (creature.z != wz)
            return false;
    
        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
            return false;
    
        for (Point p : new Line(creature.x, creature.y, wx, wy)){
            if (creature.realTile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
                continue;
        
            return false;
        }
    
        return true;
    }

	public void onGainLevel() {
	    new LevelUpController().autoLevelUp(creature);
	  }

	public Tile rememberedTile(int wx, int wy, int wz) {
        return Tile.UNKNOWN;
    }
	
	private Map<String, String> itemNames;

    public String getName(Item item){
        String name = itemNames.get(item.name());
        return name == null ? item.appearance() : name;
    }
 
    public void setName(Item item, String name){
        itemNames.put(item.name(), name);
    }
    
    public void discussItemName(Item item, Creature other){
        creature.doAction(item, "say \"%ss are %ss\"", item.appearance(), item.name());
        other.learnName(item);
    }

}