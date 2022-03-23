package project.uca.power4.components;

import java.util.EnumMap;

public class Box {
    private final EnumMap<Direction, Box> neighbour;
    private Token token;

    public Box() {
        neighbour = new EnumMap<>(Direction.class);
        token = Token.Empty;
    }

    public Box getNeighbour(Direction direction) {
        return neighbour.get(direction);
    }

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
            return getNeighbour(direction).check(direction, token, 1);
        }
    }

    private boolean check(Direction direction, Token token, int iter) {
        if (iter >= 4)
            return true;
        else if (this.token != token || getNeighbour(direction) == null) {
            return false;
        } else {
            return getNeighbour(direction).check(direction, token, iter+1);
        }
    }

    public boolean check(OptionalDirection direction) {
        if (token == Token.Empty || getNeighbour(direction) == null) {
            return false;
        } else {
            return getNeighbour(direction).check(direction, token, 1);
        }
    }

    private boolean check(OptionalDirection direction, Token token, int iter) {
        if (iter >= 4)
            return true;
        else if (this.token != token || getNeighbour(direction) == null) {
            return false;
        } else {
            return getNeighbour(direction).check(direction, token, iter+1);
        }
    }

    void setNeighbour(Box box, Direction which) {
        neighbour.put(which, box);
    }

    @Override
    public String toString() {
        return token.name();
    }
}
