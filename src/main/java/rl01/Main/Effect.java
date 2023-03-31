package rl01.Main;

public class Effect {
    protected int duration;
    
    public boolean isDone() { return duration < 1; }
    
    public Effect(int duration){
            this.duration = duration;
    }
    
    public void update(Creature creature){
            duration--;
    }
    
    public void start(Creature creature){
            
    }
    
    public void end(Creature creature){
            
    }
}
