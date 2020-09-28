package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static chess.model.Color.*;

public class Game {
    private Board board = new Board();

    private Player playerWhite = new Player("Player 1", WHITE);
    private Player playerBlack = new Player("Player 2", BLACK);

    public void handleBoardClick(int x, int y) {
        board.handleBoardClick(x, y);
    }

    public void initGame() {
        board.initBoard();

        playerWhite.setPieces(board.getPiecesByColor(WHITE));
        playerBlack.setPieces(board.getPiecesByColor(BLACK));
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }
}
