package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

/**
 * Is responisble for finding legal moves
 */
public class Movement {
    public Movement(Map<Point, Piece> boardMap, List<Ply> plies) {
        this.boardMap = boardMap;
        this.plies = plies;
    }

    public Movement() {
    }

    private Map<Point, Piece> boardMap = new HashMap<>();
    private final List<Point> legalPoints = new ArrayList<>(); // Holds points which are valid to move to
    private List<Ply> plies = new ArrayList<>();

    public void setBoardMap(Map<Point, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    public List<Point> fetchLegalMoves(Piece pieceToMove, Point selectedPoint) {
        legalPoints.clear();

        switch (pieceToMove.getPieceType()) {
            case ROOK -> legalMovesRook(pieceToMove, selectedPoint);

            case BISHOP -> legalMovesBishop(pieceToMove, selectedPoint);

            case KNIGHT -> legalMovesKnight(pieceToMove, selectedPoint);

            case QUEEN -> legalMovesQueen(pieceToMove, selectedPoint);

            case KING -> legalMovesKing(pieceToMove, selectedPoint);

            case PAWN -> legalMovesPawn(pieceToMove, selectedPoint);
        }
        return new ArrayList<>(legalPoints);
    }

    private void legalMovesPawn(Piece pieceToMove, Point selectedPoint) {
        int x = selectedPoint.x;
        int y = selectedPoint.y;

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

        addPoints(getEnPassantPoints(pieceToMove,selectedPoint));
    }

    private void legalMovesRook(Piece pieceToMove, Point selectedPoint) {
        up(pieceToMove, selectedPoint, 7);
        down(pieceToMove, selectedPoint, 7);
        left(pieceToMove, selectedPoint, 7);
        right(pieceToMove, selectedPoint, 7);
    }

    private void legalMovesBishop(Piece pieceToMove, Point selectedPoint) {
        upLeft(pieceToMove, selectedPoint, 7);
        upRight(pieceToMove, selectedPoint, 7);
        downRight(pieceToMove, selectedPoint, 7);
        downLeft(pieceToMove, selectedPoint, 7);
    }

    private void legalMovesKnight(Piece pieceToMove, Point selectedPoint) {
        int x = selectedPoint.x;
        int y = selectedPoint.y;

        addPoint(new Point(x + 1, y - 2), pieceToMove);
        addPoint(new Point(x + 2, y - 1), pieceToMove);
        addPoint(new Point(x + 2, y + 1), pieceToMove);
        addPoint(new Point(x + 1, y + 2), pieceToMove);
        addPoint(new Point(x - 1, y + 2), pieceToMove);
        addPoint(new Point(x - 2, y + 1), pieceToMove);
        addPoint(new Point(x - 2, y - 1), pieceToMove);
        addPoint(new Point(x - 1, y - 2), pieceToMove);
    }

    private void legalMovesKing(Piece pieceToMove, Point selectedPoint) {
        up(pieceToMove, selectedPoint, 1);
        right(pieceToMove, selectedPoint, 1);
        down(pieceToMove, selectedPoint, 1);
        left(pieceToMove, selectedPoint, 1);

        upLeft(pieceToMove, selectedPoint, 1);
        upRight(pieceToMove, selectedPoint, 1);
        downLeft(pieceToMove, selectedPoint, 1);
        downRight(pieceToMove, selectedPoint, 1);

        addPoints(getCastlingPoints(pieceToMove, selectedPoint));
        List<Point> opponentMoves = getOpponentLegalPoints(pieceToMove.getColor());
        legalPoints.removeIf(p -> opponentMoves.contains(p));
    }

    private void legalMovesQueen(Piece pieceToMove, Point selectedPoint) {
        up(pieceToMove, selectedPoint, 7);
        down(pieceToMove, selectedPoint, 7);
        left(pieceToMove, selectedPoint, 7);
        right(pieceToMove, selectedPoint, 7);

        upLeft(pieceToMove, selectedPoint, 7);
        upRight(pieceToMove, selectedPoint, 7);
        downRight(pieceToMove, selectedPoint, 7);
        downLeft(pieceToMove, selectedPoint, 7);
    }

    private void up(Piece pieceToMove, Point selectedPoint, int iterations) {
        for (int i = selectedPoint.y - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(selectedPoint.x, i);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void down(Piece pieceToMove, Point selectedPoint, int iterations) {
        for (int i = selectedPoint.y + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(selectedPoint.x, i);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void left(Piece pieceToMove, Point selectedPoint, int iterations) {
        for (int i = selectedPoint.x - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(i, selectedPoint.y);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void right(Piece pieceToMove, Point selectedPoint, int iterations) {
        for (int i = selectedPoint.x + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(i, selectedPoint.y);
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void upLeft(Piece pieceToMove, Point selectedPoint, int iterations) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y--;
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void upRight(Piece pieceToMove, Point selectedPoint, int iterations) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y--;
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void downRight(Piece pieceToMove, Point selectedPoint, int iterations) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y++;
            if (addPoint(p, pieceToMove)) break;
        }
    }

    private void downLeft(Piece pieceToMove, Point selectedPoint, int iterations) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);

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
                legalPoints.add(new Point(p.x, p.y));
                breakLoop = false;
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                legalPoints.add(new Point(p.x, p.y));
                breakLoop = true;
            } else {
                breakLoop = true;
            }
        } else {
            breakLoop = true;
        }
        return breakLoop;
    }


    private void addPoints(List<Point> points){
        this.legalPoints.addAll(points);
    }

    private boolean isUnoccupied(Point p) {
        return !boardMap.containsKey(p);
    }

    private boolean isOccupied(Point p) {
        return boardMap.containsKey(p);
    }

    public List<Point> getCastlingPoints(Piece pieceToMove, Point selectedPoint) {
        List<Point> castlingPoints = new ArrayList<>();
        if (!pieceHasMoved(pieceToMove)) {
            if (checkRightCastling(selectedPoint)) {
                castlingPoints.add(new Point(selectedPoint.x + 2, selectedPoint.y));
            }
            if (checkLeftCastling(selectedPoint)) {
                castlingPoints.add(new Point(selectedPoint.x - 2, selectedPoint.y));
            }
        }
        return castlingPoints;
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
     * @param selectedPoint
     * @return
     */
    private boolean checkRightCastling(Point selectedPoint) {
        for (int i = selectedPoint.x + 1; i <= selectedPoint.x + 2; i++) {
            if (isOccupied(new Point(i, selectedPoint.y))) {
                return false;
            }
        }

        Point p = new Point(selectedPoint.x + 3, selectedPoint.y);
        if (isOccupied(p)) {
            Piece piece = boardMap.get(p);
            return piece.getPieceType() == PieceType.ROOK && !pieceHasMoved(piece) && piece.getColor() == boardMap.get(selectedPoint).getColor();
        }
        return false;
    }

    /**
     * Checks that the conditions for castling to the left are filled
     *
     * @param selectedPoint
     * @return
     */
    private boolean checkLeftCastling(Point selectedPoint) {
        for (int i = selectedPoint.x - 1; i >= selectedPoint.x - 3; i--) {
            if (isOccupied(new Point(i, selectedPoint.y))) {
                return false;
            }
        }

        Point p = new Point(selectedPoint.x - 4, selectedPoint.y);
        if (isOccupied(p)) {
            Piece piece = boardMap.get(p);
            return piece.getPieceType() == PieceType.ROOK && !pieceHasMoved(piece) && piece.getColor() == boardMap.get(selectedPoint).getColor();
        }
        return false;
    }

    public List<Point> getEnPassantPoints(Piece pieceToMove, Point selectedPoint) {
        List<Point> enPassantPoints = new ArrayList<>();
        if (plies.size() == 0) return enPassantPoints;

        Ply lastPly = plies.get(plies.size() - 1);
        Piece lastMovedPiece = lastPly.movedPiece;

        if (lastMovedPiece.getPieceType() == PAWN && lastMovedPiece.getColor() != pieceToMove.getColor()) {
            if (Math.abs(lastPly.movedFrom.y - lastPly.movedTo.y) == 2) {
                if (lastPly.movedTo.x == selectedPoint.x + 1 || lastPly.movedTo.x == selectedPoint.x - 1) {
                    if (lastPly.movedTo.y == selectedPoint.y) {
                        if (lastMovedPiece.getColor() == BLACK) {
                            enPassantPoints.add(new Point(lastPly.movedTo.x, lastPly.movedTo.y - 1));
                        } else if (lastMovedPiece.getColor() == WHITE) {
                            enPassantPoints.add(new Point(lastPly.movedTo.x, lastPly.movedTo.y + 1));
                        }
                    }
                }
            }
        }
        return enPassantPoints;
    }

    private List<Point> getOpponentLegalPoints(ChessColor color){
        List<Point> opponentMoves = new ArrayList<>();

        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            if(!(entry.getValue().getColor().equals(color))){
                fetchLegalMoves(boardMap.get(entry.getKey()), entry.getKey());
                opponentMoves.addAll(legalPoints);
            }
        }

        return opponentMoves;
    }
}
