package project.uca.power4.components;

import java.util.EnumMap;

public class Box {
    private EnumMap<Direction, Box> neighbour;
    private Token token;

    public Box() {
        neighbour = new EnumMap<Direction, Box>(Direction.class);
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

    void setNeighbour(Box box, Direction which) {
        neighbour.put(which, box);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
