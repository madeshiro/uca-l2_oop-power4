package project.uca.power4.components;

import java.util.EnumMap;

public class Box {
    private final EnumMap<Direction, Box> neighbour;
    private Token token;

    public Box() {
        neighbour = new EnumMap<>(Direction.class);
        token = Token.Empty;
    }

    /**
     * Get the direct neighbour of the box
     * @param direction the position of the neighbour to get
     * @return the neighbour if exists, null otherwise.
     */
    public Box getNeighbour(Direction direction) {
        return neighbour.get(direction);
    }

    /**
     * Get the neighbour in a diagonal direction from the box
     * @param diagonal the position of the neighbour to get
     * @return the neighbour if exists, null otherwise.
     */
    public Box getNeighbour(OptionalDirection diagonal) {
        assert diagonal != null;

        Box up = neighbour.get(diagonal.d1());
        return up == null ? null : up.getNeighbour(diagonal.d2());
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Check the alignment of tokens to tell if the power 4 has been realised.
     * @return True if 4 tokens of the same colour are aligned in any direction.
     */
    public boolean checkAlignment() {
        for (Direction direction : Direction.values()) {
            if (check(direction))
                return true;
        }

        for (OptionalDirection direction : OptionalDirection.values()) {
            if (check(direction))
                return true;
        }

        return false;
    }

    public boolean check(Direction direction) {
        if (token == Token.Empty || getNeighbour(direction) == null) {
            return false;
        } else {
            return getNeighbour(direction).check(direction, token, 2);
        }
    }

    private boolean check(Direction direction, Token token, int iter) {
        if (token == this.token) {
            if (iter >= 4) {
                return true;
            } else if (getNeighbour(direction) != null) {
                return getNeighbour(direction).check(direction, token, iter + 1);
            } else {
                return false;
            }
        }

        return false;
    }

    public boolean check(OptionalDirection direction) {
        if (token == Token.Empty || getNeighbour(direction) == null) {
            return false;
        } else {
            return getNeighbour(direction).check(direction, token, 2);
        }
    }

    private boolean check(OptionalDirection direction, Token token, int iter) {
        if (token == this.token) {
            if (iter >= 4) {
                return true;
            } else if (getNeighbour(direction) != null) {
                return getNeighbour(direction).check(direction, token, iter + 1);
            } else {
                return false;
            }
        }

        return false;
    }

    void setNeighbour(Box box, Direction which) {
        neighbour.put(which, box);
    }

    @Override
    public String toString() {
        return token.name();
    }
}
