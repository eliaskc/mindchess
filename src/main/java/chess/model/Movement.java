package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chess.model.Color.BLACK;
import static chess.model.Color.WHITE;
import static chess.model.PieceType.PAWN;

/**
 * Is responisble for finding legal moves
 */
public class Movement {
    private Map<Point, Piece> boardMap = new HashMap<>();
    private final List<Point> points = new ArrayList<>(); // Holds points which are valid to move to
    private List<Ply> plies = new ArrayList<>();
    private final List<Point> castlingPoints = new ArrayList<>();
    private final List<Point> enPassantPoints = new ArrayList<>();

    public void setBoardMap(Map<Point, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    public void setPlies(List<Ply> plies) {
        this.plies = plies;
    }

    public List<Point> getCastlingPoints() {
        return castlingPoints;
    }

    public List<Point> getEnPassantPoints() {
        return enPassantPoints;
    }

    public List<Point> pieceMoveDelegation(Piece pieceToMove, Point markedPoint) {
        points.clear();
        switch (pieceToMove.getPieceType()) {
            case ROOK -> legalMovesRook(pieceToMove, markedPoint);

            case BISHOP -> legalMovesBishop(pieceToMove, markedPoint);

            case KNIGHT -> legalMovesKnight(pieceToMove, markedPoint);

            case QUEEN -> legalMovesQueen(pieceToMove, markedPoint);

            case KING -> legalMovesKing(pieceToMove, markedPoint);

            case PAWN -> legalMovesPawn(pieceToMove, markedPoint);
        }
        return new ArrayList<>(points);
    }

    private void legalMovesPawn(Piece pieceToMove, Point markedPoint) {
        int x = markedPoint.x;
        int y = markedPoint.y;

        if (pieceToMove.getColor() == WHITE) {

            if (isUnoccupied(new Point(x, y - 1))) {
                addPoint(new Point(x, y - 1), pieceToMove);

                if (isUnoccupied(new Point(x, y - 2)) && !pieceHasMoved(pieceToMove)) {
                    addPoint(new Point(x, y - 2), pieceToMove);
                }
            }

            if (isOccupied(new Point(x + 1, y - 1))) addPoint(new Point(x + 1, y - 1), pieceToMove);
            if (isOccupied(new Point(x - 1, y - 1))) addPoint(new Point(x - 1, y - 1), pieceToMove);

        } else if (pieceToMove.getColor() == BLACK) {

            if (isUnoccupied(new Point(x, y + 1))) {
                addPoint(new Point(x, y + 1), pieceToMove);

                if (isUnoccupied(new Point(x, y + 2)) && !pieceHasMoved(pieceToMove)) {
                    addPoint(new Point(x, y + 2), pieceToMove);
                }
            }

            if (isOccupied(new Point(x + 1, y + 1))) addPoint(new Point(x + 1, y + 1), pieceToMove);
            if (isOccupied(new Point(x - 1, y + 1))) addPoint(new Point(x - 1, y + 1), pieceToMove);
        }

        enPassantPoints.clear();
        checkEnPassant(pieceToMove, markedPoint);
        points.addAll(enPassantPoints);
    }

    private void legalMovesRook(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove, markedPoint, 7);
        down(pieceToMove, markedPoint, 7);
        left(pieceToMove, markedPoint, 7);
        right(pieceToMove, markedPoint, 7);
    }

    private void legalMovesBishop(Piece pieceToMove, Point markedPoint) {
        upLeft(pieceToMove, markedPoint, 7);
        upRight(pieceToMove, markedPoint, 7);
        downRight(pieceToMove, markedPoint, 7);
        downLeft(pieceToMove, markedPoint, 7);
    }

    private void legalMovesKnight(Piece pieceToMove, Point markedPoint) {
        int x = markedPoint.x;
        int y = markedPoint.y;

        addPoint(new Point(x + 1, y - 2), pieceToMove);
        addPoint(new Point(x + 2, y - 1), pieceToMove);
        addPoint(new Point(x + 2, y + 1), pieceToMove);
        addPoint(new Point(x + 1, y + 2), pieceToMove);
        addPoint(new Point(x - 1, y + 2), pieceToMove);
        addPoint(new Point(x - 2, y + 1), pieceToMove);
        addPoint(new Point(x - 2, y - 1), pieceToMove);
        addPoint(new Point(x - 1, y - 2), pieceToMove);
    }

    private void legalMovesKing(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove, markedPoint, 1);
        right(pieceToMove, markedPoint, 1);
        down(pieceToMove, markedPoint, 1);
        left(pieceToMove, markedPoint, 1);

        upLeft(pieceToMove, markedPoint, 1);
        upRight(pieceToMove, markedPoint, 1);
        downLeft(pieceToMove, markedPoint, 1);
        downRight(pieceToMove, markedPoint, 1);

        castlingPoints.clear();
        checkCastling(pieceToMove, markedPoint);
        points.addAll(castlingPoints);
    }

    public void legalMovesQueen(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove, markedPoint, 7);
        down(pieceToMove, markedPoint, 7);
        left(pieceToMove, markedPoint, 7);
        right(pieceToMove, markedPoint, 7);

        upLeft(pieceToMove, markedPoint, 7);
        upRight(pieceToMove, markedPoint, 7);
        downRight(pieceToMove, markedPoint, 7);
        downLeft(pieceToMove, markedPoint, 7);
    }

    private void up(Piece pieceToMove, Point markedPoint, int iterations) {
        for (int i = markedPoint.y - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(markedPoint.x, i);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void down(Piece pieceToMove, Point markedPoint, int iterations) {
        for (int i = markedPoint.y + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(markedPoint.x, i);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void left(Piece pieceToMove, Point markedPoint, int iterations) {
        for (int i = markedPoint.x - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(i, markedPoint.y);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void right(Piece pieceToMove, Point markedPoint, int iterations) {
        for (int i = markedPoint.x + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(i, markedPoint.y);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void upLeft(Piece pieceToMove, Point markedPoint, int iterations) {
        Point p = new Point(markedPoint.x, markedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y--;
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void upRight(Piece pieceToMove, Point markedPoint, int iterations) {
        Point p = new Point(markedPoint.x, markedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y--;
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void downRight(Piece pieceToMove, Point markedPoint, int iterations) {
        Point p = new Point(markedPoint.x, markedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y++;
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void downLeft(Piece pieceToMove, Point markedPoint, int iterations) {
        Point p = new Point(markedPoint.x, markedPoint.y);

        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y++;
            if (addPoint(p, pieceToMove)) break;
        }
    }

    /**
     * Adds point to the list of legal moves if the point is inside the board AND:
     * - the point is empty
     * - the point has a piece of the opposite color
     *
     * @param p           point to move to
     * @param pieceToMove
     * @return returns boolean that breaks the loop where the method was called, if a point has been added
     */
    private boolean addPoint(Point p, Piece pieceToMove) {
        boolean breakLoop;      //Used just to be extra clear, instead of return false or true
        if (p.x >= 0 && p.x < 8 && p.y >= 0 && p.y < 8) {
            if (boardMap.get(p) == null) {
                points.add(new Point(p.x, p.y));
                breakLoop = false;
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(new Point(p.x, p.y));
                breakLoop = true;
            } else {
                breakLoop = true;
            }
        } else {
            breakLoop = true;
        }
        return breakLoop;
    }

    private boolean isUnoccupied(Point p) {
        return boardMap.get(p) == null;
    }

    private boolean isOccupied(Point p) {
        return boardMap.get(p) != null;
    }

    private void checkCastling(Piece pieceToMove, Point markedPoint) {
        if (!pieceHasMoved(pieceToMove)) {
            if (checkRightCastling(markedPoint)) {
                castlingPoints.add(new Point(markedPoint.x + 2, markedPoint.y));
            }
            if (checkLeftCastling(markedPoint)) {
                castlingPoints.add(new Point(markedPoint.x - 2, markedPoint.y));
            }
        }
    }

    /**
     * Checks if a piece has moved
     *
     * @param piece
     * @return boolean
     */
    private boolean pieceHasMoved(Piece piece) {
        for (Ply p : plies) {
            if (p.getMovedPiece() == piece) return true;
        }
        return false;
    }

    /**
     * Checks that the conditions for castling to the right are filled
     *
     * @param markedPoint
     * @return
     */
    private boolean checkRightCastling(Point markedPoint) {
        for (int i = markedPoint.x + 1; i < markedPoint.x + 2; i++) {
            if (isOccupied(new Point(i, markedPoint.y))) {
                return false;
            }
        }

        Point p = new Point(markedPoint.x + 3, markedPoint.y);
        if (isOccupied(p)) {
            Piece piece = boardMap.get(p);
            return piece.getPieceType() == PieceType.ROOK && !pieceHasMoved(piece) && piece.getColor() == boardMap.get(markedPoint).getColor();
        }
        return false;
    }

    /**
     * Checks that the conditions for castling to the left are filled
     *
     * @param markedPoint
     * @return
     */
    private boolean checkLeftCastling(Point markedPoint) {
        for (int i = markedPoint.x - 1; i > markedPoint.x - 3; i--) {
            if (isOccupied(new Point(i, markedPoint.y))) {
                return false;
            }
        }

        Point p = new Point(markedPoint.x - 4, markedPoint.y);
        if (isOccupied(p)) {
            Piece piece = boardMap.get(p);
            return piece.getPieceType() == PieceType.ROOK && !pieceHasMoved(piece) && piece.getColor() == boardMap.get(markedPoint).getColor();
        }
        return false;
    }

    private void checkEnPassant(Piece pieceToMove, Point markedPoint) {
        if (plies.size() == 0) return;

        Ply lastPly = plies.get(plies.size() - 1);
        Piece lastMovedPiece = lastPly.movedPiece;

        if (lastMovedPiece.getPieceType() == PAWN && lastMovedPiece.getColor() != pieceToMove.getColor()) {
            if (Math.abs(lastPly.movedFrom.y - lastPly.movedTo.y) == 2) {
                if (lastPly.movedTo.x == markedPoint.x + 1 || lastPly.movedTo.x == markedPoint.x - 1) {
                    if (lastPly.movedTo.y == markedPoint.y) {
                        if (lastMovedPiece.getColor() == BLACK) {
                            enPassantPoints.add(new Point(lastPly.movedTo.x, lastPly.movedTo.y - 1));
                        } else if (lastMovedPiece.getColor() == WHITE) {
                            enPassantPoints.add(new Point(lastPly.movedTo.x, lastPly.movedTo.y + 1));
                        }
                    }
                }
            }
        }
    }
}
