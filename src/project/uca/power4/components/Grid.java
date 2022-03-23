package project.uca.power4.components;

public final class Grid {
    private Box[] availableBoxes;
    private Box cornerDownLeft;

    private int occupiedBoxes;

    public Grid() {
        initMatrix();
    }

    public void reset() {
        initMatrix();
    }

    public void initMatrix() {
        Box[][] boxes = new Box[6][7];
        availableBoxes = new Box[7];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                boxes[i][j] = new Box();
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i > 0)
                    boxes[i][j].setNeighbour(boxes[i-1][j], Direction.DOWN);
                if (j > 0)
                    boxes[i][j].setNeighbour(boxes[i][j-1], Direction.LEFT);
                if (i < 5)
                    boxes[i][j].setNeighbour(boxes[i+1][j], Direction.UP);
                if (j < 6)
                    boxes[i][j].setNeighbour(boxes[i][j+1], Direction.RIGHT);
            }
        }

        for (int i = 0; i < 7; i++) {
            availableBoxes[i] = boxes[0][i];
        }

        cornerDownLeft = boxes[0][0];
        occupiedBoxes = 0;
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

    public boolean putToken(Token token, int column) throws IllegalArgumentException {
        if (this.isFull()) return false;

        Box box = getAvailableBoxFrom(column);
        if (box != null) {
            box.setToken(token);
            occupiedBoxes++;
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull() {
        return occupiedBoxes == 42;
    }
}
