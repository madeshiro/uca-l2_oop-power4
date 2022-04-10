package project.uca.power4.entity;

import project.uca.power4.components.Box;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;
import project.uca.power4.ui.GameInterface;

public class LivingPlayer extends Player {
    private final String name;

    public LivingPlayer(Grid grid, Token color, String name) {
        super(grid, color);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Box playTurn(GameInterface ui) {
        Box box = null;
        while (box == null) {
            GameInterface.Action action = ui.waitPlayer(this);
            if (action == GameInterface.Action.Quit) {
                return null;
            } else {
                box = putToken(action.getColumn());
            }
        }

        return box;
    }

    @Override
    public boolean isAI() {
        return false;
    }
}
