package project.uca.power4.components;

public final class Grid {
    private Box[] availableBoxes;
    private Box cornerDownLeft;

    private int occupiedBoxes;

    /**
     * Create a new grid
     */
    public Grid() {
        initMatrix();
    }

    /**
     * Init the grid
     */
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

    /**
     * Get the corner down left of the chained matrix.
     * @return the corner down left
     */
    public Box getLinkedMatrix() {
        return cornerDownLeft;
    }

    /**
     * Get the box available of the designated column
     * @param column the column to get the available box from
     * @return the box if exists, null otherwise.
     * @throws IllegalArgumentException if the column number if out of bound.
     */
    public Box getAvailableBoxFrom(int column) throws IllegalArgumentException {
        if (column < 1 || column > 7) {
            throw new IllegalArgumentException("Column number must be between 1 and 7 !");
        }

        return availableBoxes[column-1];
    }

    /**
     * Put a token at the designed column
     * @param token the token to put in the grid
     * @param column the column where to put it
     * @return the box where the token has been putted, Null if impossible
     * @throws IllegalArgumentException if the column number is out of bound.
     */
    public Box putToken(Token token, int column) throws IllegalArgumentException {
        if (this.isFull()) return null;

        Box box = getAvailableBoxFrom(column);
        if (box != null) {
            box.setToken(token);
            availableBoxes[column-1] = box.getNeighbour(Direction.UP);
            occupiedBoxes++;
            return box;
        } else {
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
                rowBuffer.append(row.getToken().getInitial());
                row = row.getNeighbour(Direction.RIGHT);
            }
            rowBuffer.append('\n');
            buffer.insert(0, rowBuffer);
            lefty = lefty.getNeighbour(Direction.UP);
        }

        return buffer.toString();
    }
}
