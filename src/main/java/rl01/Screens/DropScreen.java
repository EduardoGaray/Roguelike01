package rl01.Screens;

import asciiPanel.AsciiPanel;
import rl01.Main.Creature;
import rl01.Main.Item;

public class DropScreen extends InventoryBasedScreen {

    public DropScreen(Creature player) {
        super(player);
    }

	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		
	}

	protected String getVerb() {
        return "drop";
    }

	protected boolean isAcceptable(Item item) {
        return true;
    }

	protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
}
