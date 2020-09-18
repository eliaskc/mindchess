package chess.model;

import java.awt.*;

/**
 * Class Square represents a point on the chess board
 */
public class Square {
    Point coordinates = new Point(0, 0);

    public Square(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
    }

    public int getCoordinatesX() {
        return coordinates.x;
    }

    public int getCoordinatesY() {
        return coordinates.y;
    }
}
