package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.KING;
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
    private List<Ply> plies = new ArrayList<>();

    boolean checkingOpponentLegalPointsInProgress = false;

    public void setBoardMap(Map<Point, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    public List<Point> fetchLegalMoves(Piece pieceToMove, Point selectedPoint) {
        List<Point> legalPoints = new ArrayList<>(); // Holds points which are valid to move to

        switch (pieceToMove.getPieceType()) {
            case ROOK -> legalMovesRook(pieceToMove, selectedPoint, legalPoints);

            case BISHOP -> legalMovesBishop(pieceToMove, selectedPoint, legalPoints);

            case KNIGHT -> legalMovesKnight(pieceToMove, selectedPoint, legalPoints);

            case QUEEN -> legalMovesQueen(pieceToMove, selectedPoint, legalPoints);

            case KING -> legalMovesKing(pieceToMove, selectedPoint, legalPoints);

            case PAWN -> legalMovesPawn(pieceToMove, selectedPoint, legalPoints);
        }
        return legalPoints;
    }

    private void legalMovesPawn(Piece pieceToMove, Point selectedPoint, List<Point> legalPoints) {
        int x = selectedPoint.x;
        int y = selectedPoint.y;

        if (pieceToMove.getColor() == WHITE) {
            if (isUnoccupied(new Point(x, y - 1))) {
                up(pieceToMove, selectedPoint, 1, legalPoints);

                if (isUnoccupied(new Point(x, y - 2)) && !pieceHasMoved(pieceToMove)) {
                    addPoint(new Point(x, y - 2), pieceToMove, legalPoints);
                }
            }
            if (isOccupied(new Point(x + 1, y - 1))) upRight(pieceToMove, selectedPoint, 1, legalPoints);
            if (isOccupied(new Point(x - 1, y - 1))) upLeft(pieceToMove, selectedPoint, 1, legalPoints);
        } else if (pieceToMove.getColor() == BLACK) {
            if (isUnoccupied(new Point(x, y + 1))) {
                down(pieceToMove, selectedPoint, 1, legalPoints);

                if (isUnoccupied(new Point(x, y + 2)) && !pieceHasMoved(pieceToMove)) {
                    addPoint(new Point(x, y + 2), pieceToMove, legalPoints);
                }
            }
            if (isOccupied(new Point(x + 1, y + 1))) downRight(pieceToMove, selectedPoint, 1, legalPoints);
            if (isOccupied(new Point(x - 1, y + 1))) downLeft(pieceToMove, selectedPoint, 1, legalPoints);
        }

        legalPoints.addAll(getEnPassantPoints(pieceToMove,selectedPoint));
    }

    private void legalMovesRook(Piece pieceToMove, Point selectedPoint, List<Point> legalPoints) {
        up(pieceToMove, selectedPoint, 7, legalPoints);
        down(pieceToMove, selectedPoint, 7, legalPoints);
        left(pieceToMove, selectedPoint, 7, legalPoints);
        right(pieceToMove, selectedPoint, 7, legalPoints);
    }

    private void legalMovesBishop(Piece pieceToMove, Point selectedPoint, List<Point> legalPoints) {
        upLeft(pieceToMove, selectedPoint, 7, legalPoints);
        upRight(pieceToMove, selectedPoint, 7, legalPoints);
        downRight(pieceToMove, selectedPoint, 7, legalPoints);
        downLeft(pieceToMove, selectedPoint, 7, legalPoints);
    }

    private void legalMovesKnight(Piece pieceToMove, Point selectedPoint, List<Point> legalPoints) {
        int x = selectedPoint.x;
        int y = selectedPoint.y;

        addPoint(new Point(x + 1, y - 2), pieceToMove, legalPoints);
        addPoint(new Point(x + 2, y - 1), pieceToMove, legalPoints);
        addPoint(new Point(x + 2, y + 1), pieceToMove, legalPoints);
        addPoint(new Point(x + 1, y + 2), pieceToMove, legalPoints);
        addPoint(new Point(x - 1, y + 2), pieceToMove, legalPoints);
        addPoint(new Point(x - 2, y + 1), pieceToMove, legalPoints);
        addPoint(new Point(x - 2, y - 1), pieceToMove, legalPoints);
        addPoint(new Point(x - 1, y - 2), pieceToMove, legalPoints);
    }

    private void legalMovesKing(Piece pieceToMove, Point selectedPoint, List<Point> legalPoints) {
        up(pieceToMove, selectedPoint, 1, legalPoints);
        right(pieceToMove, selectedPoint, 1, legalPoints);
        down(pieceToMove, selectedPoint, 1, legalPoints);
        left(pieceToMove, selectedPoint, 1, legalPoints);

        upLeft(pieceToMove, selectedPoint, 1, legalPoints);
        upRight(pieceToMove, selectedPoint, 1, legalPoints);
        downLeft(pieceToMove, selectedPoint, 1, legalPoints);
        downRight(pieceToMove, selectedPoint, 1, legalPoints);

        legalPoints.addAll(getCastlingPoints(pieceToMove, selectedPoint));
        if (!checkingOpponentLegalPointsInProgress) {
            List<Point> opponentLegalPoints = fetchOpponentLegalPoints(pieceToMove.getColor());
            legalPoints.removeIf(p -> opponentLegalPoints.contains(p));
        }
    }

    private void legalMovesQueen(Piece pieceToMove, Point selectedPoint, List<Point> legalPoints) {
        up(pieceToMove, selectedPoint, 7, legalPoints);
        down(pieceToMove, selectedPoint, 7, legalPoints);
        left(pieceToMove, selectedPoint, 7, legalPoints);
        right(pieceToMove, selectedPoint, 7, legalPoints);

        upLeft(pieceToMove, selectedPoint, 7, legalPoints);
        upRight(pieceToMove, selectedPoint, 7, legalPoints);
        downRight(pieceToMove, selectedPoint, 7, legalPoints);
        downLeft(pieceToMove, selectedPoint, 7, legalPoints);
    }

    private void up(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.y - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(selectedPoint.x, i);
            if (addPoint(p, pieceToMove, legalPoints)) break;
        }
    }

    private void down(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.y + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(selectedPoint.x, i);
            if (addPoint(p, pieceToMove, legalPoints)) break;
        }
    }

    private void left(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.x - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(i, selectedPoint.y);
            if (addPoint(p, pieceToMove, legalPoints)) break;
        }
    }

    private void right(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.x + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(i, selectedPoint.y);
            if (addPoint(p, pieceToMove, legalPoints)) break;
        }
    }

    private void upLeft(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y--;
            if (addPoint(p, pieceToMove, legalPoints)) break;
        }
    }

    private void upRight(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y--;
            if (addPoint(p, pieceToMove, legalPoints)) break;
        }
    }

    private void downRight(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y++;
            if (addPoint(p, pieceToMove, legalPoints)) break;
        }
    }

    private void downLeft(Piece pieceToMove, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);

        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y++;
            if (addPoint(p, pieceToMove, legalPoints)) break;
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
    private boolean addPoint(Point p, Piece pieceToMove, List<Point> listToAddTo) {
        boolean breakLoop;      //Used just to be extra clear, instead of return false or true
        if (p.x >= 0 && p.x < 8 && p.y >= 0 && p.y < 8) {
            if (boardMap.get(p) == null) {
                listToAddTo.add(new Point(p.x, p.y));
                breakLoop = false;
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                listToAddTo.add(new Point(p.x, p.y));
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
        return !boardMap.containsKey(p);
    }

    private boolean isOccupied(Point p) {
        return boardMap.containsKey(p);
    }

    List<Point> getCastlingPoints(Piece pieceToMove, Point selectedPoint) {
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

    List<Point> getEnPassantPoints(Piece pieceToMove, Point selectedPoint) {
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

    private List<Point> fetchOpponentLegalPoints(ChessColor color){
        List<Point> opponentLegalPoints = new ArrayList<>();
        checkingOpponentLegalPointsInProgress = true;

        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            if(!(entry.getValue().getColor().equals(color))){
                opponentLegalPoints.addAll(fetchLegalMoves(boardMap.get(entry.getKey()), entry.getKey()));
            }
        }

        checkingOpponentLegalPointsInProgress = false;
        return opponentLegalPoints;
    }

    boolean isKingInCheck(Point kingPoint) {
        return fetchOpponentLegalPoints(boardMap.get(kingPoint).getColor()).contains(kingPoint);
    }

    Point fetchKingPoint(ChessColor color) {
        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            if(entry.getValue().getColor().equals(color) && entry.getValue().getPieceType().equals(KING)){
                return entry.getKey();
            }
        }
        return null;
    }
}
