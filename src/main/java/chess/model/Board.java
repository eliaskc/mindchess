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
    private final Map<Point, Piece> boardMap = new HashMap<>();
    private final List<Piece> deadPieces = new ArrayList<>();
    public Board() {
    }


    public Map<Point, Piece> getBoardMap() {
        return boardMap;
    }


    public void initBoard() {
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
        boardMap.put(new Point(0, 0), new Piece(true, BLACK, ROOK));
        boardMap.put(new Point(1, 0), new Piece(true, BLACK, KNIGHT));
        boardMap.put(new Point(2, 0), new Piece(true, BLACK, BISHOP));
        boardMap.put(new Point(3, 0), new Piece(true, BLACK, QUEEN));
        boardMap.put(new Point(4, 0), new Piece(true, BLACK, KING));
        boardMap.put(new Point(5, 0), new Piece(true, BLACK, BISHOP));
        boardMap.put(new Point(6, 0), new Piece(true, BLACK, KNIGHT));
        boardMap.put(new Point(7, 0), new Piece(true, BLACK, ROOK));
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Point(i, 1), new Piece(true, BLACK, PAWN));
        }
    }

    private void placeWhitePieces() {
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Point(i, 6), new Piece(true, WHITE, PAWN));
        }
        boardMap.put(new Point(0, 7), new Piece(true, WHITE, ROOK));
        boardMap.put(new Point(1, 7), new Piece(true, WHITE, KNIGHT));
        boardMap.put(new Point(2, 7), new Piece(true, WHITE, BISHOP));
        boardMap.put(new Point(3, 7), new Piece(true, WHITE, QUEEN));
        boardMap.put(new Point(4, 7), new Piece(true, WHITE, KING));
        boardMap.put(new Point(5, 7), new Piece(true, WHITE, BISHOP));
        boardMap.put(new Point(6, 7), new Piece(true, WHITE, KNIGHT));
        boardMap.put(new Point(7, 7), new Piece(true, WHITE, ROOK));
    }

    List<Piece> getPiecesByColor(ChessColor chessColor) {
        List<Piece> returnList = new ArrayList<>();
        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            if (entry.getValue().getColor() == chessColor) {
                returnList.add(entry.getValue());
            }
        }
        return returnList;
    }

    public List<Piece> getDeadPieces() {
        return deadPieces;
    }
}
