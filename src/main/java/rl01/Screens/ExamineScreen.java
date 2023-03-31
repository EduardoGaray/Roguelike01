package rl01.Screens;

import asciiPanel.AsciiPanel;
import rl01.Main.Creature;
import rl01.Main.Item;

public class ExamineScreen extends InventoryBasedScreen {

    public ExamineScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "examine";
    }

    protected boolean isAcceptable(Item item) {
        return true;
    }

    protected Screen use(Item item) {
        String article = "aeiou".contains(item.name().subSequence(0, 1)) ? "an " : "a ";
        player.notify("It's " + article + item.name() + "." + item.details());
        return null;
    }

	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		
	}
}

