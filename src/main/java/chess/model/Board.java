package chess.model;

import chess.model.pieces.IPiece;
import chess.model.pieces.PieceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

/**
 * Board represents the chess board and contains the information and methods to interact with the chess board
 */
public class Board {
    private final Map<Square, IPiece> boardMap = new HashMap<>();
    private final List<IPiece> deadPieces = new ArrayList<>();
    int i = 0;

    Board() {
    }

    Map<Square, IPiece> getBoardMap() {
        return boardMap;
    }

    void initBoard() {
        boardMap.clear();
        deadPieces.clear();
        placeAllPieces();
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

    List<IPiece> getDeadPieces() {
        return deadPieces;
    }

    boolean isPieceOnSquareRook(Square square) {
        return boardMap.get(square).getPieceType().equals(PieceType.ROOK);
    }

    //TODO public only because of tests. Fix them to work without and make package-private
    public IPiece fetchPieceOnSquare(Square squareSelected) {
        return boardMap.get(squareSelected);
    }

    ChessColor fetchPieceOnSquareColor(Square square) {
        return boardMap.get(square).getColor();
    }

    //TODO public only because of tests. Fix them to work without and make package-private
    public boolean pieceOnSquareColorEquals(Square s, ChessColor chessColor) {
        return boardMap.get(s).getColor().equals(chessColor);
    }

    Square fetchKingSquare(ChessColor color) {
        for (Map.Entry<Square, chess.model.pieces.IPiece> entry : boardMap.entrySet()) {
            if(entry.getValue().getColor().equals(color) && entry.getValue().getPieceType().equals(PieceType.KING)){
                return entry.getKey();
            }
        }
        return null;
    }

    boolean isSquareAPiece(Square square){
        return boardMap.containsKey(square);
    }

    boolean isOccupied(Square s) {
        return boardMap.containsKey(s);
    }

    public void markPieceOnSquareHasMoved(Square s){
        fetchPieceOnSquare(s).setHasMoved(true);
    }
}
