package chess.model;

import java.awt.*;
import java.util.*;
import java.util.List;

import static chess.model.Color.BLACK;
import static chess.model.Color.WHITE;

/**
 * Is responsible for finding legal moves
 */
public class Movement {
    private Map<Point, Piece> boardMap = new HashMap<>();

    public void setBoardMap(Map<Point, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    /**
     * Creates a list of current legal moves for a given piece (and its position)
     *
     * @param pieceToMove
     * @param markedPoint current position for the piece
     * @return current legal moves
     */
    public List<Point> pieceMoveDelegation(Piece pieceToMove, Point markedPoint) {
        List<Point> legalMoves = new ArrayList<>();
        switch (pieceToMove.getPieceType()) {
            case ROOK:
                legalMoves.addAll(legalMovesRook(pieceToMove, markedPoint));
                break;
            case BISHOP:
                legalMoves.addAll(legalMovesBishop(pieceToMove, markedPoint));
                break;
            case KNIGHT:
                legalMoves.addAll(legalMovesKnight(pieceToMove, markedPoint));
                break;
            case QUEEN:
                legalMoves.addAll(legalMovesQueen(pieceToMove, markedPoint));
                break;
            case KING:
                legalMoves.addAll(legalMovesKing(pieceToMove, markedPoint));
                break;
            case PAWN:
                if (pieceToMove.getColor() == WHITE) {
                    legalMoves.addAll(legalMovesWhitePawn(pieceToMove, markedPoint));
                } else if (pieceToMove.getColor() == BLACK) {
                    legalMoves.addAll(legalMovesBlackPawn(pieceToMove, markedPoint));
                }
                break;
        }
        return legalMoves;
    }

    private List<Point> legalMovesRook(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        points.addAll(up(pieceToMove, markedPoint));
        points.addAll(down(pieceToMove, markedPoint));
        points.addAll(left(pieceToMove, markedPoint));
        points.addAll(right(pieceToMove, markedPoint));

        return points;
    }

    private List<Point> legalMovesBishop(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        points.addAll(upLeft(pieceToMove, markedPoint));
        points.addAll(upRight(pieceToMove, markedPoint));
        points.addAll(downRight(pieceToMove, markedPoint));
        points.addAll(downLeft(pieceToMove, markedPoint));

        return points;
    }

    private List<Point> legalMovesKnight(Piece pieceToMove, Point markedPoint) {
        int x = markedPoint.x;
        int y = markedPoint.y;

        List<Point> points = Arrays.asList(
        new Point(x + 1, y - 2),
        new Point(x + 2, y - 1),
        new Point(x + 2, y + 1),
        new Point(x + 1, y + 2),
        new Point(x - 1, y + 2),
        new Point(x - 2, y + 1),
        new Point(x - 2, y - 1),
        new Point(x - 1, y - 2)
        );

        //Ohhh my
        points.removeIf(p -> p.x > 7 || p.x < 0 || p.y > 7 || p.y < 0);
        points.removeIf(p -> boardMap.get(p) != null && boardMap.get(p).getColor() == pieceToMove.getColor());

        return points;
    }

    private List<Point> legalMovesWhitePawn(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        //Need to check if the pawn has moved already, because if not then it can move 2 steps

        List<Point> legalMovesUp = up(pieceToMove, markedPoint);
        if (legalMovesUp.size() != 0 && boardMap.get(legalMovesUp.get(0)) == null) points.add(legalMovesUp.get(0));

        //Check if the square to the right and up has a black piece on it, and if so add it to
        //the list of legal points
        List<Point> legalMovesUpRight = upRight(pieceToMove, markedPoint);
        if (legalMovesUpRight.size() != 0 && boardMap.get(legalMovesUpRight.get(0)) != null) {
            if (boardMap.get(legalMovesUpRight.get(0)).getColor() == BLACK) {
                points.add(legalMovesUpRight.get(0));
            }
        }

        //same for left and up
        List<Point> legalMovesUpLeft = upLeft(pieceToMove, markedPoint);
        if (legalMovesUpLeft.size() != 0 && boardMap.get(legalMovesUpLeft.get(0)) != null) {
            if (boardMap.get(legalMovesUpLeft.get(0)).getColor() == BLACK) {
                points.add(legalMovesUpLeft.get(0));
            }
        }

        return points;
    }

    private List<Point> legalMovesBlackPawn(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        //TODO Need to check if the pawn has moved already, because if not then it can move 2 steps

        List<Point> legalMovesDown = down(pieceToMove, markedPoint);
        if (legalMovesDown.size() != 0 && boardMap.get(legalMovesDown.get(0)) == null)
            points.add(legalMovesDown.get(0));

        List<Point> legalMovesDownRight = downRight(pieceToMove, markedPoint);
        if (legalMovesDownRight.size() != 0 && boardMap.get(legalMovesDownRight.get(0)) != null) {
            if (boardMap.get(legalMovesDownRight.get(0)).getColor() == WHITE) {
                points.add(legalMovesDownRight.get(0));
            }
        }
        List<Point> legalMovesDownLeft = downLeft(pieceToMove, markedPoint);
        if (legalMovesDownLeft.size() != 0 && boardMap.get(legalMovesDownLeft.get(0)) != null) {
            if (boardMap.get(legalMovesDownLeft.get(0)).getColor() == WHITE) {
                points.add(legalMovesDownLeft.get(0));
            }
        }

        return points;
    }

    private List<Point> legalMovesKing(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        if (up(pieceToMove, markedPoint).size() != 0) points.add(up(pieceToMove, markedPoint).get(0));
        if (right(pieceToMove, markedPoint).size() != 0) points.add(right(pieceToMove, markedPoint).get(0));
        if (down(pieceToMove, markedPoint).size() != 0) points.add(down(pieceToMove, markedPoint).get(0));
        if (left(pieceToMove, markedPoint).size() != 0) points.add(left(pieceToMove, markedPoint).get(0));

        if (upLeft(pieceToMove, markedPoint).size() != 0) points.add(upLeft(pieceToMove, markedPoint).get(0));
        if (upRight(pieceToMove, markedPoint).size() != 0) points.add(upRight(pieceToMove, markedPoint).get(0));
        if (downRight(pieceToMove, markedPoint).size() != 0) points.add(downRight(pieceToMove, markedPoint).get(0));
        if (downLeft(pieceToMove, markedPoint).size() != 0) points.add(downLeft(pieceToMove, markedPoint).get(0));

        return points;
    }

    public List<Point> legalMovesQueen(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();
        points.addAll(up(pieceToMove, markedPoint));
        points.addAll(down(pieceToMove, markedPoint));
        points.addAll(left(pieceToMove, markedPoint));
        points.addAll(right(pieceToMove, markedPoint));

        points.addAll(upLeft(pieceToMove, markedPoint));
        points.addAll(upRight(pieceToMove, markedPoint));
        points.addAll(downRight(pieceToMove, markedPoint));
        points.addAll(downLeft(pieceToMove, markedPoint));
        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the upwards direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> up(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        for (int i = markedPoint.y - 1; i >= 0; i--) {
            Point p = new Point(markedPoint.x, i);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }
        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the downwards direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> down(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        for (int i = markedPoint.y + 1; i < 8; i++) {
            Point p = new Point(markedPoint.x, i);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) to the left
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> left(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        for (int i = markedPoint.x - 1; i >= 0; i--) {
            Point p = new Point(i, markedPoint.y);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) to the right
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> right(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        for (int i = markedPoint.x + 1; i < 8; i++) {
            Point p = new Point(i, markedPoint.y);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the diagonal up and left direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> upLeft(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for (int i = 0; i < 8; i++) {
            p.x--;
            p.y--;
            if (p.x >= 0 && p.y >= 0) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the diagonal up and right direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> upRight(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for (int i = 0; i < 8; i++) {
            p.x++;
            p.y--;
            if (p.x < 8 && p.y >= 0) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the diagonal down and right direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> downRight(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for (int i = 0; i < 8; i++) {
            p.x++;
            p.y++;
            if (p.x < 8 && p.y < 8) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }

    /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the diagonal down and left direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private List<Point> downLeft(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for (int i = 0; i < 8; i++) {
            p.x--;
            p.y++;
            if (p.x >= 0 && p.y < 8) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }
}
