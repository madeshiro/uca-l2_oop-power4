package project.uca.power4.entity;

import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;

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
    public void playTurn() {

    }
}
