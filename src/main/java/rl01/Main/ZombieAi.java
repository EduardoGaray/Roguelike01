package rl01.Main;

import java.util.List;

public class ZombieAi extends CreatureAi {
	
	private Creature player;
	 private List<String> messages;

	public ZombieAi(Creature creature, Creature player, List<String> messages) {
		super(creature);
		this.player = player;
		this.messages = messages;
	}

	public void onUpdate() {
		if (Math.random() < 0.2)
			return;

		if (creature.canSee(player.x, player.y, player.z))
			hunt(player);
		else
			wander();
	}

	public void hunt(Creature target) {
		List<Point> points = new Path(creature, target.x, target.y).points();

		int mx = points.get(0).x - creature.x;
		int my = points.get(0).y - creature.y;

		creature.moveBy(mx, my, 0);
	}
	
	public void onNotify(String message) {
		messages.add(message);
	}

}