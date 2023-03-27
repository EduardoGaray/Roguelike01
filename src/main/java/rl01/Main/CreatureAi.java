package rl01.Main;

public class CreatureAi {
	protected Creature creature;

	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	public void onEnter(int x, int y, Tile tile){
	    if (tile.isGround()){
	        creature.x = x;
	        creature.y = y;
	    } else if (tile.isDiggable()) {
	        creature.dig(x, y);
	    }
	}

	public void onUpdate() { }

	public void onNotify(String format) { }
	
}