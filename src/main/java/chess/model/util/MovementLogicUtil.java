package chess.model.util;

import chess.model.*;
import chess.model.pieces.IPiece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;
import static chess.model.SquareType.EN_PASSANT;
import static chess.model.SquareType.PROMOTION;

/**
 * Is responsible for finding legal moves
 */
public class MovementLogicUtil {
    private MovementLogicUtil() {
    }

    public static List<Square> up(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getY() - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Square s = new Square(squareToCheck.getX(), i);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Square> down(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getY() + 1; i < 8 && iterations > 0; i++, iterations--) {
            Square s = new Square(squareToCheck.getX(), i);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Square> left(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getX() - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Square s = new Square(i, squareToCheck.getY());
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Square> right(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getX() + 1; i < 8 && iterations > 0; i++, iterations--) {
            Square s = new Square(i, squareToCheck.getY());
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Square> upLeft(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX()-1);
            s.setY(s.getY()-1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Square> upRight(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX()+1);
            s.setY(s.getY()-1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Square> downRight(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX()+1);
            s.setY(s.getY()+1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Square> downLeft(Board board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX()-1);
            s.setY(s.getY()+1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Adds square to the list of legal moves if the square is inside the board AND:
     * - the square is empty
     * - the square has a piece of the opposite color
     *
     * @param board
     * @param s            potential legal square
     * @param squareToCheck square moving from
     * @param listToAddTo
     * @return returns boolean that breaks the loop where the method was called, if a point has been added
     */
    public static boolean addSquareIfLegal(Board board, Square s, Square squareToCheck, List<Square> listToAddTo) {
        if (s.getX() >= 0 && s.getX() < 8 && s.getY() >= 0 && s.getY() < 8) {
            if (!isOccupied(board, s)) {
                listToAddTo.add(new Square(s.getX(), s.getY()));
                return false;
            } else if (!board.pieceOnSquareColorEquals(s, board.fetchPieceOnSquareColor(squareToCheck))) {
                listToAddTo.add(new Square(s.getX(), s.getY()));
                return true;
            }
        }
        return true;
    }

    public static boolean isOccupied(Board board, Square s) {
        return board.isOccupied(s);
    }

/*    private List<Square> fetchOpponentLegalSquares(ChessColor color){
        List<Square> opponentLegalSquares = new ArrayList<>();
        checkingOpponentLegalSquaresInProgress = true;

        for (Map.Entry<Square, Piece> entry : boardMap.entrySet()) {
            if(!(entry.getValue().getColor().equals(color))){
                opponentLegalSquares.addAll(fetchLegalMoves(boardMap.get(entry.getKey()), entry.getKey()));
            }
        }

        checkingOpponentLegalSquaresInProgress = false;
        return opponentLegalSquares;
    }*/

    static public List<Square> getEnPassantSquares(Ply lastPly, Square squareToCheck, Board board){
        List<Square> enPassantSquares = new ArrayList<>();
        Square movedFrom = lastPly.getMovedFrom();
        Square movedTo = lastPly.getMovedTo();
        PieceType pieceType = lastPly.getMovedPiece().getPieceType();
        ChessColor pieceToMoveColor = board.fetchPieceOnSquareColor(squareToCheck);

        if (pieceType.equals(PieceType.PAWN) && !board.pieceOnSquareColorEquals(movedTo, pieceToMoveColor) && Math.abs(movedFrom.getY() - movedTo.getY()) == 2) {
            if ((movedTo.getX() == squareToCheck.getX() + 1 || movedFrom.getX() == squareToCheck.getX() - 1) && movedTo.getY() == squareToCheck.getY()) {
                if (board.pieceOnSquareColorEquals(movedTo, ChessColor.BLACK)) {
                    enPassantSquares.add(new Square(movedTo.getX(), movedTo.getY() - 1, EN_PASSANT));
                } else if (board.pieceOnSquareColorEquals(movedTo, ChessColor.WHITE)) {
                    enPassantSquares.add(new Square(movedTo.getX(), movedTo.getY() + 1, EN_PASSANT));
                }
            }
        }
        return enPassantSquares;
    }

    public static void checkPawnPromotion(Board board, ArrayList<Square> legalSquares, Square squareToCheck) {
        if (legalSquares.size() == 0) return;

        for (Square s : legalSquares){
            if(board.fetchPieceOnSquare(squareToCheck).getPieceType() == PAWN){
                if(s.getY() == 0 && board.fetchPieceOnSquare(squareToCheck).getColor() == WHITE || s.getY() == 7 && board.fetchPieceOnSquare(squareToCheck).getColor() == BLACK){
                    s.setSquareType(PROMOTION);
                }
            }
        }
    }

}
