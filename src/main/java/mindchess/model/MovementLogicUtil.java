package mindchess.model;

import mindchess.model.pieces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mindchess.model.ChessColor.BLACK;
import static mindchess.model.ChessColor.WHITE;
import static mindchess.model.PieceType.PAWN;
import static mindchess.model.SquareType.*;

/**
 * Is responsible for finding legal moves
 */
public class MovementLogicUtil {
    private MovementLogicUtil() {
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

    //-------------------------------------------------------------------------------------
    //Directions
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

    //-------------------------------------------------------------------------------------
    //Special rules
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

    /**
     *
     * @param board
     * @param squareToCheck
     * @return
     */
    public static boolean checkRightCastling(Board board, Square squareToCheck) {
        for (int i = squareToCheck.getX() + 1; i <= squareToCheck.getX() + 2; i++) {
            if (isOccupied(board, new Square(i, squareToCheck.getY()))) {
                return false;
            }
        }
        Square s = new Square(squareToCheck.getX() + 3, squareToCheck.getY());
        if (isOccupied(board, s)) {
            return board.isPieceOnSquareRook(s) && board.pieceOnSquareColorEquals(s, board.fetchPieceOnSquareColor(squareToCheck));
        }
        return false;
    }

    /**
     *
     * @param board
     * @param squareToCheck
     * @return
     */
    public static boolean checkLeftCastling(Board board, Square squareToCheck) {
        for (int i = squareToCheck.getX() - 1; i >= squareToCheck.getX() - 3; i--) {
            if (isOccupied(board, new Square(i, squareToCheck.getY()))) {
                return false;
            }
        }
        Square s = new Square(squareToCheck.getX() - 4, squareToCheck.getY());
        if (isOccupied(board, s)) {
            return board.isPieceOnSquareRook(s) && board.pieceOnSquareColorEquals(s, board.fetchPieceOnSquareColor(squareToCheck));
        }
        return false;
    }

    //-------------------------------------------------------------------------------------
    //Fetch
    public static List<Square> fetchLegalSquaresByColor(Board board, ChessColor color){
        List<Square> opponentLegalSquares = new ArrayList<>();

        for (Map.Entry<Square, IPiece> entry : board.getBoardEntrySet()) {
            if(entry.getValue().getColor().equals(color)){
                opponentLegalSquares.addAll(entry.getValue().getMoveDelegate().fetchMoves(board, entry.getKey(), entry.getValue().getHasMoved(), false));
            }
        }

        return opponentLegalSquares;
    }

    public static ChessColor fetchPieceColorOnSquare(Board board, Square squareToCheck) {
        return board.fetchPieceOnSquareColor(squareToCheck);
    }

    //-------------------------------------------------------------------------------------
    //Is checks
    public static boolean isOccupied(Board board, Square s) {
        return board.isOccupied(s);
    }

    public static void isKingInCheck(Board board, Square kingSquare, ChessColor opponentColor) {
        if (fetchLegalSquaresByColor(board, opponentColor).contains(kingSquare))
            kingSquare.setSquareType(IN_CHECK);
        else
            kingSquare.setSquareType(NORMAL);
    }
}
