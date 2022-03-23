package project.uca.power4.components;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

enum OptionalDirection {
    UP_RIGHT(Direction.UP, Direction.RIGHT),
    UP_LEFT(Direction.UP, Direction.LEFT),
    DOWN_RIGHT(Direction.DOWN, Direction.RIGHT),
    DOWN_LEFT(Direction.DOWN, Direction.LEFT);


    final Direction[] searchDirection;
    OptionalDirection(Direction d1, Direction d2) {
        searchDirection = new Direction[]{d1, d2};
    }

    public Direction d1() {
        return searchDirection[0];
    }

    public Direction d2() {
        return searchDirection[1];
    }
}
