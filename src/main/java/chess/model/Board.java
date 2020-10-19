package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.*;

/**
 * Board represents the chess board and contains the information and methods to interact with the chess board
 */
public class Board {
    private final Map<Square, Piece> boardMap = new HashMap<>();
    private final List<Piece> deadPieces = new ArrayList<>();
    Board() {
    }

    Map<Square, Piece> getBoardMap() {
        return boardMap;
    }

    void initBoard() {
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
        boardMap.put(new Square(0, 0), new Piece(BLACK, ROOK));
        boardMap.put(new Square(1, 0), new Piece(BLACK, KNIGHT));
        boardMap.put(new Square(2, 0), new Piece(BLACK, BISHOP));
        boardMap.put(new Square(3, 0), new Piece(BLACK, QUEEN));
        boardMap.put(new Square(4, 0), new Piece(BLACK, KING));
        boardMap.put(new Square(5, 0), new Piece(BLACK, BISHOP));
        boardMap.put(new Square(6, 0), new Piece(BLACK, KNIGHT));
        boardMap.put(new Square(7, 0), new Piece(BLACK, ROOK));
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Square(i, 1), new Piece(BLACK, PAWN));
        }
    }

    private void placeWhitePieces() {
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Square(i, 6), new Piece(WHITE, PAWN));
        }
        boardMap.put(new Square(0, 7), new Piece(WHITE, ROOK));
        boardMap.put(new Square(1, 7), new Piece(WHITE, KNIGHT));
        boardMap.put(new Square(2, 7), new Piece(WHITE, BISHOP));
        boardMap.put(new Square(3, 7), new Piece(WHITE, QUEEN));
        boardMap.put(new Square(4, 7), new Piece(WHITE, KING));
        boardMap.put(new Square(5, 7), new Piece(WHITE, BISHOP));
        boardMap.put(new Square(6, 7), new Piece(WHITE, KNIGHT));
        boardMap.put(new Square(7, 7), new Piece(WHITE, ROOK));
    }

    List<Piece> getPiecesByColor(ChessColor chessColor) {
        List<Piece> returnList = new ArrayList<>();
        for (Map.Entry<Square, Piece> entry : boardMap.entrySet()) {
            if (entry.getValue().getColor() == chessColor) {
                returnList.add(entry.getValue());
            }
        }
        return returnList;
    }

    List<Piece> getDeadPieces() {
        return deadPieces;
    }
}
