package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.pieces.IPiece;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for the Board class
 */
public interface IBoard {
    void initBoard();

    void placePieceOnSquare(Square square, IPiece piece);

    IPiece removePieceFromSquare(Square square);

    boolean  isOccupied(Square s);

    void markPieceOnSquareHasMoved(Square s);

    IPiece fetchPieceOnSquare(Square squareSelected);

    ChessColor fetchPieceOnSquareColor(Square square);

    boolean pieceOnSquareColorEquals(Square s, ChessColor chessColor);

    Square fetchKingSquare(ChessColor color);

    List<IPiece> getDeadPieces();

    IPiece getPieceOnSquare(Square square);

    Map<Square, IPiece> getBoardSnapShot();

    Set<Map.Entry<Square, IPiece>> getBoardEntrySet();

    Set<Square> getBoardKeys();

    boolean isSquareContainsAPiece(Square square);

    boolean isPieceOnSquareRook(Square square);

    boolean isAPieceOnSquare(Square square);
}
