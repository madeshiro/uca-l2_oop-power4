package project.uca.power4.components;

public final class Grid {
    private Box[] availableBoxes;
    private Box cornerDownLeft;

    private int occupiedBoxes;

    private String printToken(Token token) {
        switch (token) {
            case Yellow:
                return "Y";
            case Red:
                return "R";
            case Empty: default:
                return "E";
        }
    }

    public Grid() {
        initMatrix();
    }

    public void initMatrix() {
        cornerDownLeft = new Box();
        availableBoxes = new Box[7];
        availableBoxes[0] = cornerDownLeft;
        occupiedBoxes  = 0;

        Box lefty = cornerDownLeft;
        for (int i = 1; i < 6; i++) {
            Box box = new Box();
            box.setNeighbour(lefty, Direction.DOWN);
            lefty.setNeighbour(box, Direction.UP);
            lefty = box;
        }

        for (int col = 1; col < 7; col++) {
            Box downty = new Box();
            lefty = availableBoxes[col-1];
            downty.setNeighbour(lefty, Direction.LEFT);
            lefty.setNeighbour(downty, Direction.RIGHT);

            availableBoxes[col] = downty;

            lefty = lefty.getNeighbour(Direction.UP);
            while (lefty != null) {
                Box box = new Box();
                box.setNeighbour(downty, Direction.DOWN);
                box.setNeighbour(lefty, Direction.LEFT);

                lefty.setNeighbour(box, Direction.RIGHT);
                downty.setNeighbour(box, Direction.UP);

                lefty = lefty.getNeighbour(Direction.UP);
                downty = box;
            }
        }
    }

    public Box getLinkedMatrix() {
        return cornerDownLeft;
    }

    public Box getAvailableBoxFrom(int column) throws IllegalArgumentException {
        if (column < 1 || column > 7) {
            throw new IllegalArgumentException("Column number must be between 1 and 7 !");
        }

        return availableBoxes[column-1];
    }

    public Box putToken(Token token, int column) throws IllegalArgumentException {
        if (this.isFull()) return null;

        Box box = getAvailableBoxFrom(column);
        if (box != null) {
            box.setToken(token);
            availableBoxes[column-1] = box.getNeighbour(Direction.UP);
            occupiedBoxes++;
            return box;
        } else {
            System.out.println("RETURN NULL");
            return null;
        }
    }

    public boolean isFull() {
        return occupiedBoxes >= 42;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Box lefty = this.getLinkedMatrix();
        while (lefty != null) {
            Box row = lefty;
            StringBuilder rowBuffer = new StringBuilder();
            while (row != null) {
                rowBuffer.append(printToken(row.getToken()));
                row = row.getNeighbour(Direction.RIGHT);
            }
            rowBuffer.append('\n');
            buffer.insert(0, rowBuffer);
            lefty = lefty.getNeighbour(Direction.UP);
        }

        return buffer.toString();
    }
}
