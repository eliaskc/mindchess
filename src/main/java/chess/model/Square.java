package chess.model;

import java.awt.*;

public class Square {
    Point coordinates = new Point(0, 0);

    public Square(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
    }
}
