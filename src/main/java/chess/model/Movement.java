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
    public Movement(Map<Square, Piece> boardMap, List<Ply> plies) {
        this.boardMap = boardMap;
        this.plies = plies;
    }

    public Movement() {
    }

    private Map<Square, Piece> boardMap = new HashMap<>();
    private List<Ply> plies = new ArrayList<>();

    boolean checkingOpponentLegalSquaresInProgress = false;

    public void setBoardMap(Map<Square, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    public List<Square> fetchLegalMoves(Piece pieceToMove, Square selectedSquare) {
        List<Square> legalSquares = new ArrayList<>(); // Holds Squares which are valid to move to

        switch (pieceToMove.getPieceType()) {
            case ROOK -> legalMovesRook(pieceToMove, selectedSquare, legalSquares);

            case BISHOP -> legalMovesBishop(pieceToMove, selectedSquare, legalSquares);

            case KNIGHT -> legalMovesKnight(pieceToMove, selectedSquare, legalSquares);

            case QUEEN -> legalMovesQueen(pieceToMove, selectedSquare, legalSquares);

            case KING -> legalMovesKing(pieceToMove, selectedSquare, legalSquares);

            case PAWN -> legalMovesPawn(pieceToMove, selectedSquare, legalSquares);
        }
        return legalSquares;
    }

    private void legalMovesPawn(Piece pieceToMove, Square selectedSquare, List<Square> legalSquares) {
        int x = selectedSquare.getX();
        int y = selectedSquare.getY();

        if (pieceToMove.getColor() == WHITE) {
            if (isUnoccupied(new Square(x, y - 1))) {
                up(pieceToMove, selectedSquare, 1, legalSquares);

                if (isUnoccupied(new Square(x, y - 2)) && !pieceHasMoved(pieceToMove)) {
                    addSquare(new Square(x, y - 2), pieceToMove, legalSquares);
                }
            }
            if (isOccupied(new Square(x + 1, y - 1))) upRight(pieceToMove, selectedSquare, 1, legalSquares);
            if (isOccupied(new Square(x - 1, y - 1))) upLeft(pieceToMove, selectedSquare, 1, legalSquares);
        } else if (pieceToMove.getColor() == BLACK) {
            if (isUnoccupied(new Square(x, y + 1))) {
                down(pieceToMove, selectedSquare, 1, legalSquares);

                if (isUnoccupied(new Square(x, y + 2)) && !pieceHasMoved(pieceToMove)) {
                    addSquare(new Square(x, y + 2), pieceToMove, legalSquares);
                }
            }
            if (isOccupied(new Square(x + 1, y + 1))) downRight(pieceToMove, selectedSquare, 1, legalSquares);
            if (isOccupied(new Square(x - 1, y + 1))) downLeft(pieceToMove, selectedSquare, 1, legalSquares);
        }

        legalSquares.addAll(getEnPassantSquares(pieceToMove,selectedSquare));
    }

    private void legalMovesRook(Piece pieceToMove, Square selectedSquare, List<Square> legalSquare) {
        up(pieceToMove, selectedSquare, 7, legalSquare);
        down(pieceToMove, selectedSquare, 7, legalSquare);
        left(pieceToMove, selectedSquare, 7, legalSquare);
        right(pieceToMove, selectedSquare, 7, legalSquare);
    }

    private void legalMovesBishop(Piece pieceToMove, Square selectedSquare, List<Square> legalSquare) {
        upLeft(pieceToMove, selectedSquare, 7, legalSquare);
        upRight(pieceToMove, selectedSquare, 7, legalSquare);
        downRight(pieceToMove, selectedSquare, 7, legalSquare);
        downLeft(pieceToMove, selectedSquare, 7, legalSquare);
    }

    private void legalMovesKnight(Piece pieceToMove, Square selectedSquare, List<Square> legalSquares) {
        int x = selectedSquare.getX();
        int y = selectedSquare.getY();

        addSquare(new Square(x + 1, y - 2), pieceToMove, legalSquares);
        addSquare(new Square(x + 2, y - 1), pieceToMove, legalSquares);
        addSquare(new Square(x + 2, y + 1), pieceToMove, legalSquares);
        addSquare(new Square(x + 1, y + 2), pieceToMove, legalSquares);
        addSquare(new Square(x - 1, y + 2), pieceToMove, legalSquares);
        addSquare(new Square(x - 2, y + 1), pieceToMove, legalSquares);
        addSquare(new Square(x - 2, y - 1), pieceToMove, legalSquares);
        addSquare(new Square(x - 1, y - 2), pieceToMove, legalSquares);
    }

    private void legalMovesKing(Piece pieceToMove, Square selectedSquare, List<Square> legalSquares) {
        up(pieceToMove, selectedSquare, 1, legalSquares);
        right(pieceToMove, selectedSquare, 1, legalSquares);
        down(pieceToMove, selectedSquare, 1, legalSquares);
        left(pieceToMove, selectedSquare, 1, legalSquares);

        upLeft(pieceToMove, selectedSquare, 1, legalSquares);
        upRight(pieceToMove, selectedSquare, 1, legalSquares);
        downLeft(pieceToMove, selectedSquare, 1, legalSquares);
        downRight(pieceToMove, selectedSquare, 1, legalSquares);

        legalSquares.addAll(getCastlingSquare(pieceToMove, selectedSquare));
        if (!checkingOpponentLegalSquaresInProgress) {
            List<Square> opponentLegalSquares = fetchOpponentLegalSquares(pieceToMove.getColor());
            legalSquares.removeIf(p -> opponentLegalSquares.contains(p));
        }
    }

    private void legalMovesQueen(Piece pieceToMove, Square selectedSquare, List<Square> legalSquares) {
        up(pieceToMove, selectedSquare, 7, legalSquares);
        down(pieceToMove, selectedSquare, 7, legalSquares);
        left(pieceToMove, selectedSquare, 7, legalSquares);
        right(pieceToMove, selectedSquare, 7, legalSquares);

        upLeft(pieceToMove, selectedSquare, 7, legalSquares);
        upRight(pieceToMove, selectedSquare, 7, legalSquares);
        downRight(pieceToMove, selectedSquare, 7, legalSquares);
        downLeft(pieceToMove, selectedSquare, 7, legalSquares);
    }

    private void up(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        for (int i = selectedSquare.getY() - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Square square = new Square(selectedSquare.getX(), i);
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    private void down(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        for (int i = selectedSquare.getY() + 1; i < 8 && iterations > 0; i++, iterations--) {
            Square square = new Square(selectedSquare.getX(), i);
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    private void left(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        for (int i = selectedSquare.getX() - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Square square = new Square(i, selectedSquare.getY());
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    private void right(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        for (int i = selectedSquare.getX() + 1; i < 8 && iterations > 0; i++, iterations--) {
            Square square = new Square(i, selectedSquare.getY());
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    private void upLeft(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        Square square = new Square(selectedSquare.getX(), selectedSquare.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            square.setX(square.getX()-1);
            square.setY(square.getY()-1);
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    private void upRight(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        Square square = new Square(selectedSquare.getX(), selectedSquare.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            square.setX(square.getX()+1);
            square.setY(square.getY()-1);
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    private void downRight(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        Square square = new Square(selectedSquare.getX(), selectedSquare.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            square.setX(square.getX()+1);
            square.setY(square.getY()+1);
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    private void downLeft(Piece pieceToMove, Square selectedSquare, int iterations, List<Square> legalSquares) {
        Square square = new Square(selectedSquare.getX(), selectedSquare.getY());

        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            square.setX(square.getX()-1);
            square.setY(square.getY()+1);
            if (addSquare(square, pieceToMove, legalSquares)) break;
        }
    }

    /**
     * Adds square to the list of legal moves if the square is inside the board AND:
     * - the square is empty
     * - the square has a piece of the opposite color
     *
     * @param square           square to move to
     * @param pieceToMove
     * @return returns boolean that breaks the loop where the method was called, if a square has been added
     */
    private boolean addSquare(Square square, Piece pieceToMove, List<Square> listToAddTo) {
        boolean breakLoop;      //Used just to be extra clear, instead of return false or true
        if (square.getX() >= 0 && square.getX() < 8 && square.getY() >= 0 && square.getY() < 8) {
            if (boardMap.get(square) == null) {
                listToAddTo.add(new Square(square.getX(), square.getY()));
                breakLoop = false;
            } else if (boardMap.get(square).getColor() != pieceToMove.getColor()) {
                listToAddTo.add(new Square(square.getX(), square.getY()));
                breakLoop = true;
            } else {
                breakLoop = true;
            }
        } else {
            breakLoop = true;
        }
        return breakLoop;
    }

    private boolean isUnoccupied(Square square) {
        return !boardMap.containsKey(square);
    }

    private boolean isOccupied(Square square) {
        return boardMap.containsKey(square);
    }

    List<Square> getCastlingSquare(Piece pieceToMove, Square selectedSquare) {
        List<Square> castlingSquares = new ArrayList<>();
        if (!pieceHasMoved(pieceToMove)) {
            if (checkRightCastling(selectedSquare)) {
                castlingSquares.add(new Square(selectedSquare.getX() + 2, selectedSquare.getY()));
            }
            if (checkLeftCastling(selectedSquare)) {
                castlingSquares.add(new Square(selectedSquare.getX() - 2, selectedSquare.getY()));
            }
        }
        return castlingSquares;
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
     * @param selectedSquare
     * @return
     */
    private boolean checkRightCastling(Square selectedSquare) {
        for (int i = selectedSquare.getX() + 1; i <= selectedSquare.getX() + 2; i++) {
            if (isOccupied(new Square(i, selectedSquare.getY()))) {
                return false;
            }
        }

        Square square = new Square(selectedSquare.getX() + 3, selectedSquare.getY());
        if (isOccupied(square)) {
            Piece piece = boardMap.get(square);
            return piece.getPieceType() == PieceType.ROOK && !pieceHasMoved(piece) && piece.getColor() == boardMap.get(selectedSquare).getColor();
        }
        return false;
    }

    /**
     * Checks that the conditions for castling to the left are filled
     *
     * @param selectedSquare
     * @return
     */
    private boolean checkLeftCastling(Square selectedSquare) {
        for (int i = selectedSquare.getX() - 1; i >= selectedSquare.getX() - 3; i--) {
            if (isOccupied(new Square(i, selectedSquare.getY()))) {
                return false;
            }
        }

        Square square = new Square(selectedSquare.getX() - 4, selectedSquare.getY());
        if (isOccupied(square)) {
            Piece piece = boardMap.get(square);
            return piece.getPieceType() == PieceType.ROOK && !pieceHasMoved(piece) && piece.getColor() == boardMap.get(selectedSquare).getColor();
        }
        return false;
    }

    List<Square> getEnPassantSquares(Piece pieceToMove, Square selectedSquare) {
        List<Square> enPassantSquares = new ArrayList<>();
        if (plies.size() == 0) return enPassantSquares;

        Ply lastPly = plies.get(plies.size() - 1);
        Piece lastMovedPiece = lastPly.getMovedPiece();

        if (lastMovedPiece.getPieceType() == PAWN && lastMovedPiece.getColor() != pieceToMove.getColor() && Math.abs(lastPly.getMovedFrom().getY() - lastPly.getMovedTo().getY()) == 2) {
            if ((lastPly.getMovedTo().getX() == selectedSquare.getX() + 1 || lastPly.getMovedTo().getX() == selectedSquare.getX() - 1) && lastPly.getMovedTo().getY() == selectedSquare.getY()) {
                if (lastMovedPiece.getColor() == BLACK) {
                    enPassantSquares.add(new Square(lastPly.getMovedTo().getX(), lastPly.getMovedTo().getY() - 1));
                } else if (lastMovedPiece.getColor() == WHITE) {
                    enPassantSquares.add(new Square(lastPly.getMovedTo().getX(), lastPly.getMovedTo().getY() + 1));
                }
            }
        }
        return enPassantSquares;
    }

    private List<Square> fetchOpponentLegalSquares(ChessColor color){
        List<Square> opponentLegalSquares = new ArrayList<>();
        checkingOpponentLegalSquaresInProgress = true;

        for (Map.Entry<Square, Piece> entry : boardMap.entrySet()) {
            if(!(entry.getValue().getColor().equals(color))){
                opponentLegalSquares.addAll(fetchLegalMoves(boardMap.get(entry.getKey()), entry.getKey()));
            }
        }

        checkingOpponentLegalSquaresInProgress = false;
        return opponentLegalSquares;
    }

    boolean isKingInCheck(Square kingSquare) {
        return fetchOpponentLegalSquares(boardMap.get(kingSquare).getColor()).contains(kingSquare);
    }

    Square fetchKingSquare(ChessColor color) {
        for (Map.Entry<Square, Piece> entry : boardMap.entrySet()) {
            if(entry.getValue().getColor().equals(color) && entry.getValue().getPieceType().equals(KING)){
                return entry.getKey();
            }
        }
        return null;
    }
}
