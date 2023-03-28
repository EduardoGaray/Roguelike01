package rl01.Main;

public class CreatureAi {
	protected Creature creature;

	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	public void onEnter(int x, int y, int z, Tile tile){
	    if (tile.isGround()){
	        creature.x = x;
	        creature.y = y;
	        creature.z = z;
	    } else if (tile.isDiggable()) {
	        creature.dig(x, y, z);
	    }
	}

	public void onUpdate() { }

	public void onNotify(String format) { }

	public boolean canSee(int wx, int wy, int wz) {
        if (creature.z != wz)
            return false;
    
        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
            return false;
    
        for (Point p : new Line(creature.x, creature.y, wx, wy)){
            if (creature.tile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
                continue;
        
            return false;
        }
    
        return true;
    }
	
}