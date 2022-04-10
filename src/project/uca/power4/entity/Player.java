package project.uca.power4.entity;

import project.uca.power4.components.Box;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;
import project.uca.power4.ui.GameInterface;

public abstract class Player {
    private final Token color;
    private Grid grid;

    public Player(Grid grid, Token color) {
        this.color = color;
        this.grid = grid;
    }

    public Token getColor() {
        return color;
    }

    public Grid getGrid() {
        return grid;
    }

    public void changeGrid(Grid grid) {
        this.grid = grid;
    }

    public Box putToken(int column) {
        return grid.putToken(color, column);
    }

    public abstract String getName();
    public abstract Box playTurn(GameInterface ui);
    public abstract boolean isAI();
}
