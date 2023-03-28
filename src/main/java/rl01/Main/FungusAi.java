package rl01.Main;

public class FungusAi extends CreatureAi {
	
	 private CreatureFactory factory;
	 private int spreadcount;
	 
    public FungusAi(Creature creature, CreatureFactory factory) {
    	 super(creature);
         this.factory = factory;
    }
    
    public void onUpdate(){
        if (spreadcount < 5 && Math.random() < 0.02)
            spread();
    }
 
    private void spread(){
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;
        int z = 0;
  
        if (!creature.canEnter(x, y, z))
            return;
  
        Creature child = factory.newFungus();
        child.x = x;
        child.y = y;
        spreadcount++;
        creature.doAction("spawn a child");
    }   
}