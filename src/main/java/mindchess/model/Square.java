package mindchess.model;

import java.util.Objects;

import static mindchess.model.SquareType.NORMAL;

/**
 * Represent a square on the chess board with an x and y coordinate. The square has a enum type SquareType which represents
 * what kind of square it currently is.
 */
public class Square {
    private int x;
    private int y;
    private SquareType squareType = NORMAL;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Square(int x, int y, SquareType squareType) {
        this.x = x;
        this.y = y;
        this.squareType = squareType;
    }

    public int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    public SquareType getSquareType() {
        return squareType;
    }

    void setSquareType(SquareType squareType) {
        this.squareType = squareType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return x == square.x &&
                y == square.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
