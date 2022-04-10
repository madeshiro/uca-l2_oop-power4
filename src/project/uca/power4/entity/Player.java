package project.uca.power4.entity;

import project.uca.power4.components.Box;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;
import project.uca.power4.ui.GameInterface;

public abstract class Player {
    private final Token color;
    private Grid grid;

    /**
     * Create a new player, assign the current playing grid and its token colour.
     * @param grid The grid with which the player will interact
     * @param color The player token's colour
     */
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

    /**
     * Put a token into the grid at the given column
     * @param column The column (between 1 and 7) where to put the player token.
     * @return The box where the token has been putted, Null if an error occurred when trying to put the token.
     */
    public Box putToken(int column) {
        return grid.putToken(color, column);
    }

    /**
     * Get player's name
     * @return the player name
     */
    public abstract String getName();

    /**
     * Request the player to put its token.
     * @param ui the user interface with which the user should interact (in case of a living entity)
     * @return the box where the token has been placed. Null if the player request to quit.
     */
    public abstract Box playTurn(GameInterface ui);

    /**
     * Get if the player is actually an AI.
     * @return a boolean, True if the player is non-living.
     */
    public abstract boolean isAI();
}
