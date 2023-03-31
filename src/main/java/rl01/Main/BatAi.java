package rl01.Main;

import java.util.List;

public class BatAi extends CreatureAi {
	
	private List<String> messages;

    public BatAi(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }

    public void onUpdate(){
    	//this means the bat will move twice for every time you move, a time > move management system should be considered
        wander();
        //wander();
    }
    
    public void onNotify(String message) {
		messages.add(message);
	}
    
}
