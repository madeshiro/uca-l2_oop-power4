package project.uca.power4.entity;

import project.uca.power4.ai.AIModule;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;

public class BotPlayer extends Player {

    private AIModule ai;

    public BotPlayer(Grid grid, Token color) {
        super(grid, color);

        this.ai = new AIModule(this);
    }

    @Override
    public String getName() {
        return "AI";
    }

    @Override
    public void playTurn() {

    }
}
