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
 * Is responsible for finding legal moves by checking a specified direction and returning all legal moves in a Square list in that direction
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
     * @param s             potential legal square
     * @param squareToCheck square moving from
     * @param listToAddTo
     * @return returns boolean that breaks the loop where the method was called, if a point has been added
     */
    public static boolean addSquareIfLegal(IBoard board, Square s, Square squareToCheck, List<Square> listToAddTo) {
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

    /**
     * Checks the possible moves available in the upwards direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> up(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getY() - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Square s = new Square(squareToCheck.getX(), i);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Checks the possible moves available in the downwards direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> down(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getY() + 1; i < 8 && iterations > 0; i++, iterations--) {
            Square s = new Square(squareToCheck.getX(), i);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Checks the possible moves available in the leftwards direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> left(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getX() - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Square s = new Square(i, squareToCheck.getY());
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Checks the possible moves available in the rightwards direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> right(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        for (int i = squareToCheck.getX() + 1; i < 8 && iterations > 0; i++, iterations--) {
            Square s = new Square(i, squareToCheck.getY());
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Checks the possible moves available in the up and left direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> upLeft(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX() - 1);
            s.setY(s.getY() - 1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Checks the possible moves available in the up and right direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> upRight(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX() + 1);
            s.setY(s.getY() - 1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Checks the possible moves available in the down and right direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> downRight(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX() + 1);
            s.setY(s.getY() + 1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Checks the possible moves available in the down and left direction
     *
     * @param board
     * @param squareToCheck square moving from
     * @param iterations the maximum number of steps allowed for the piece to move
     * @return
     */
    public static List<Square> downLeft(IBoard board, Square squareToCheck, int iterations) {
        var returnList = new ArrayList<Square>();
        Square s = new Square(squareToCheck.getX(), squareToCheck.getY());
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            s.setX(s.getX() - 1);
            s.setY(s.getY() + 1);
            if (addSquareIfLegal(board, s, squareToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * marks squares for pawn promotion if a pawn moves to the opposite side
     *
     * @param board
     * @param squareToCheck to check if the piece on the square is a pawn and what color it is
     * @param legalSquares  possible squares the pawn can move to
     */
    public static void checkPawnPromotion(IBoard board, Square squareToCheck, ArrayList<Square> legalSquares) {
        if (legalSquares.size() == 0) return;

        for (Square s : legalSquares) {
            if (board.fetchPieceOnSquare(squareToCheck).getPieceType() == PAWN) {
                if (s.getY() == 0 && board.fetchPieceOnSquare(squareToCheck).getColor() == WHITE || s.getY() == 7 && board.fetchPieceOnSquare(squareToCheck).getColor() == BLACK) {
                    s.setSquareType(PROMOTION);
                }
            }
        }
    }

    /**
     * returns a list of possible en passant moves, needs to know of the last move(ply) to check if en passant is possible
     *
     * @param lastPly       last move
     * @param squareToCheck the square with the piece that is going to make the move
     * @param board
     * @return list of possible en passant moves
     */
    static public List<Square> getEnPassantSquares(Ply lastPly, Square squareToCheck, IBoard board) {
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
     * checks if the king can make the castle move to the right
     *
     * @param board
     * @param squareToCheck
     * @return true if castling to the right is possible
     */
    public static boolean checkRightCastling(IBoard board, Square squareToCheck) {
        for (int i = squareToCheck.getX() + 1; i <= squareToCheck.getX() + 2; i++) {
            if (isOccupied(board, new Square(i, squareToCheck.getY()))) {
                return false;
            }
        }
        Square s = new Square(squareToCheck.getX() + 3, squareToCheck.getY());
        if (isOccupied(board, s)) {
            return board.isPieceOnSquareRook(s) && !board.fetchPieceOnSquare(s).getHasMoved() && board.pieceOnSquareColorEquals(s, board.fetchPieceOnSquareColor(squareToCheck));
        }
        return false;
    }

    /**
     * checks if the king can make the castle move to the left
     *
     * @param board
     * @param squareToCheck
     * @return true if castling to the left is possible
     */
    public static boolean checkLeftCastling(IBoard board, Square squareToCheck) {
        for (int i = squareToCheck.getX() - 1; i >= squareToCheck.getX() - 3; i--) {
            if (isOccupied(board, new Square(i, squareToCheck.getY()))) {
                return false;
            }
        }
        Square s = new Square(squareToCheck.getX() - 4, squareToCheck.getY());
        if (isOccupied(board, s)) {
            return board.isPieceOnSquareRook(s) && !board.fetchPieceOnSquare(s).getHasMoved() && board.pieceOnSquareColorEquals(s, board.fetchPieceOnSquareColor(squareToCheck));
        }
        return false;
    }

    //-------------------------------------------------------------------------------------
    //Fetch
    public static List<Square> fetchLegalSquaresByColor(IBoard board, ChessColor color) {
        List<Square> opponentLegalSquares = new ArrayList<>();

        for (Map.Entry<Square, IPiece> entry : board.getBoardEntrySet()) {
            if (entry.getValue().getColor().equals(color)) {
                opponentLegalSquares.addAll(entry.getValue().getMoveDelegate().fetchMoves(board, entry.getKey(), entry.getValue().getHasMoved(), false));
            }
        }

        return opponentLegalSquares;
    }

    public static ChessColor fetchPieceColorOnSquare(IBoard board, Square squareToCheck) {
        return board.fetchPieceOnSquareColor(squareToCheck);
    }

    //-------------------------------------------------------------------------------------
    //Is checks
    public static boolean isOccupied(IBoard board, Square s) {
        return board.isOccupied(s);
    }

    public static void isKingInCheck(IBoard board, Square kingSquare, ChessColor opponentColor) {
        if (fetchLegalSquaresByColor(board, opponentColor).contains(kingSquare))
            kingSquare.setSquareType(IN_CHECK);
        else
            kingSquare.setSquareType(NORMAL);
    }
}
