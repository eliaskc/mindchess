package mindchess.model;

import mindchess.model.pieces.IPiece;
import mindchess.model.pieces.PieceFactory;

import java.util.*;

import static mindchess.model.ChessColor.BLACK;
import static mindchess.model.ChessColor.WHITE;

/**
 * Board represents the mindchess board and contains the information and methods to see where things are on the board.
 * <p>
 * The mindchess board is represented by a map with Squares and IPieces that this class is responsible for putting into and
 * removing from.
 */
public class Board implements IBoard {
    private final Map<Square, IPiece> boardMap = new HashMap<>();
    private final List<IPiece> deadPieces = new ArrayList<>();

    Board() {
    }

    @Override
    public void initBoard() {
        boardMap.clear();
        deadPieces.clear();
        placeAllPieces();
    }

    /**
     * Puts the IPiece and Square into the boardMap with the Square as the key
     *
     * @param square
     * @param piece
     */
    @Override
    public void placePieceOnSquare(Square square, IPiece piece) {
        boardMap.put(square, piece);
    }

    /**
     * removes the square and corresponding IPiece from the boardMap
     *
     * @param square square with a piece on it
     * @return The removed piece
     */
    @Override
    public IPiece removePieceFromSquare(Square square) {
        return boardMap.remove(square);
    }

    /**
     * Checks if a square is occupied by a IPiece
     *
     * @param s The square we check
     * @return True if there is a IPiece on the square
     */
    @Override
    public boolean isOccupied(Square s) {
        return boardMap.containsKey(s);
    }

    /**
     * Changes the hasMoved attribute of the piece on the square to true
     *
     * @param s The square with the piece on it
     */
    @Override
    public void markPieceOnSquareHasMoved(Square s) {
        fetchPieceOnSquare(s).setHasMoved(true);
    }

    /**
     * places all pieces on the board
     */
    private void placeAllPieces() {
        placeBlackPieces();
        placeWhitePieces();
    }

    private void placeBlackPieces() {
        boardMap.put(new Square(0, 0), PieceFactory.createRook(BLACK));
        boardMap.put(new Square(1, 0), PieceFactory.createKnight(BLACK));
        boardMap.put(new Square(2, 0), PieceFactory.createBishop(BLACK));
        boardMap.put(new Square(3, 0), PieceFactory.createQueen(BLACK));
        boardMap.put(new Square(4, 0), PieceFactory.createKing(BLACK));
        boardMap.put(new Square(5, 0), PieceFactory.createBishop(BLACK));
        boardMap.put(new Square(6, 0), PieceFactory.createKnight(BLACK));
        boardMap.put(new Square(7, 0), PieceFactory.createRook(BLACK));
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Square(i, 1), PieceFactory.createPawn(BLACK));
        }
    }

    private void placeWhitePieces() {
        boardMap.put(new Square(0, 7), PieceFactory.createRook(WHITE));
        boardMap.put(new Square(1, 7), PieceFactory.createKnight(WHITE));
        boardMap.put(new Square(2, 7), PieceFactory.createBishop(WHITE));
        boardMap.put(new Square(3, 7), PieceFactory.createQueen(WHITE));
        boardMap.put(new Square(4, 7), PieceFactory.createKing(WHITE));
        boardMap.put(new Square(5, 7), PieceFactory.createBishop(WHITE));
        boardMap.put(new Square(6, 7), PieceFactory.createKnight(WHITE));
        boardMap.put(new Square(7, 7), PieceFactory.createRook(WHITE));
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Square(i, 6), PieceFactory.createPawn(WHITE));
        }
    }

    //-------------------------------------------------------------------------------------
    //Fetchers

    @Override
    public IPiece fetchPieceOnSquare(Square squareSelected) {
        return boardMap.get(squareSelected);
    }

    @Override
    public ChessColor fetchPieceOnSquareColor(Square square) {
        return boardMap.get(square).getColor();
    }

    @Override
    public boolean pieceOnSquareColorEquals(Square s, ChessColor chessColor) {
        return boardMap.get(s).getColor().equals(chessColor);
    }

    @Override
    public Square fetchKingSquare(ChessColor color) {
        for (Map.Entry<Square, mindchess.model.pieces.IPiece> entry : boardMap.entrySet()) {
            if (entry.getValue().getColor().equals(color) && entry.getValue().getPieceType().equals(PieceType.KING)) {
                return entry.getKey();
            }
        }
        return null;
    }

    //-------------------------------------------------------------------------------------
    //Getters
    @Override
    public List<IPiece> getDeadPieces() {
        return deadPieces;
    }
    @Override
    public IPiece getPieceOnSquare(Square square) {
        return boardMap.get(square);
    }

    @Override
    public Map<Square, IPiece> getBoardSnapShot() {
        return new HashMap<Square, IPiece>(boardMap);
    }

    @Override
    public Set<Map.Entry<Square, IPiece>> getBoardEntrySet() {
        return boardMap.entrySet();
    }

    @Override
    public Set<Square> getBoardKeys() {
        return boardMap.keySet();
    }

    @Override
    public boolean isSquareContainsAPiece(Square square) {
        return boardMap.containsKey(square);
    }

    @Override
    public boolean isPieceOnSquareRook(Square square) {
        return boardMap.get(square).getPieceType().equals(PieceType.ROOK);
    }

    @Override
    public boolean isAPieceOnSquare(Square square) {
        return boardMap.containsKey(square);
    }
}
