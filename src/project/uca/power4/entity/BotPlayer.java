package project.uca.power4.entity;

import project.uca.power4.ai.AIModule;
import project.uca.power4.components.Box;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;
import project.uca.power4.ui.GameInterface;

public class BotPlayer extends Player {

    private final AIModule ai;

    public BotPlayer(Grid grid, Token color) {
        super(grid, color);

        this.ai = new AIModule(this);
    }

    @Override
    public String getName() {
        return "AI";
    }

    @Override
    public Box playTurn(GameInterface ui) {
        Box box;
        int paradox = 0;
        /*
            set limit to 100 : If AI met a "paradox", it will stop at 100 iterations
         */
        while ((box=getGrid().putToken(getColor(), ai.action())) == null && paradox < 100) paradox++;

        if (paradox >= 99) {
            throw new RuntimeException("A paradox occurred with the AI system ! (is the grid full ?)");
        }
        return box;
    }

    @Override
    public boolean isAI() {
        return true;
    }
}
