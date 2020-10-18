package chess.model;

import chess.model.pieces.IPiece;
import chess.model.pieces.PieceFactory;

import java.awt.*;
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
    private final Map<Point, IPiece> boardMap = new HashMap<>();
    private final List<IPiece> deadIPieces = new ArrayList<>();
    private static Board instance = null;

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }
    private Board() {}

    Map<Point, IPiece> getBoardMap() {
        return boardMap;
    }

    void initBoard() {
        boardMap.clear();
        deadIPieces.clear();
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
        boardMap.put(new Point(0, 0), PieceFactory.createRook(BLACK));
        boardMap.put(new Point(1, 0), PieceFactory.createKnight(BLACK));
        boardMap.put(new Point(2, 0), PieceFactory.createBishop(BLACK));
        boardMap.put(new Point(3, 0), PieceFactory.createQueen(BLACK));
        boardMap.put(new Point(4, 0), PieceFactory.createKing(BLACK));
        boardMap.put(new Point(5, 0), PieceFactory.createBishop(BLACK));
        boardMap.put(new Point(6, 0), PieceFactory.createKnight(BLACK));
        boardMap.put(new Point(7, 0), PieceFactory.createRook(BLACK));
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Point(i, 1), PieceFactory.createPawn(BLACK));
        }
    }

    private void placeWhitePieces() {
        boardMap.put(new Point(0, 7), PieceFactory.createRook(WHITE));
        boardMap.put(new Point(1, 7), PieceFactory.createKnight(WHITE));
        boardMap.put(new Point(2, 7), PieceFactory.createBishop(WHITE));
        boardMap.put(new Point(3, 7), PieceFactory.createQueen(WHITE));
        boardMap.put(new Point(4, 7), PieceFactory.createKing(WHITE));
        boardMap.put(new Point(5, 7), PieceFactory.createBishop(WHITE));
        boardMap.put(new Point(6, 7), PieceFactory.createKnight(WHITE));
        boardMap.put(new Point(7, 7), PieceFactory.createRook(WHITE));
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Point(i, 6), PieceFactory.createPawn(WHITE));
        }
    }

    List<chess.model.pieces.IPiece> getDeadPieces() {
        return deadIPieces;
    }

    public ChessColor fetchPieceOnPointColor(Point point) {
        return boardMap.get(point).getColor();
    }

    public boolean isPieceOnPointRook(Point point) {
        return boardMap.get(point).getPieceName().equals("Rook");
    }

    public IPiece fetchPieceOnPoint(Point pointSelected) {
        return boardMap.get(pointSelected);
    }

    public Point fetchKingPoint(ChessColor color) {
        for (Map.Entry<Point, chess.model.pieces.IPiece> entry : boardMap.entrySet()) {
            if(entry.getValue().getColor().equals(color) && entry.getValue().getPieceName().equals("King")){
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isOccupied(Point p) {
        return boardMap.containsKey(p);
    }

    public boolean pieceOnPointColorEquals(Point p, ChessColor chessColor) {
        return boardMap.get(p).getColor().equals(chessColor);
    }

    public void markPieceOnPointHasMoved(Point p){
        //if (fetchPieceOnPoint(p) != null){
            fetchPieceOnPoint(p).setHasMoved(true);
        //}
    }
}
