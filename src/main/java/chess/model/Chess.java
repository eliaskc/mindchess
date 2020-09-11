package chess.model;

import java.util.ArrayList;
import java.util.List;

public class Chess {
    private static Chess instance = null;

    Player player1 = new Player("Player 3");
    Player player2 = new Player("Player 4");

    Board board = new Board();

    List<Move> moves = new ArrayList<>();

    public static Chess getInstance() {
        if (instance == null) {
            instance = new Chess();
            instance.init();
        }
        return instance;
    }

    private void init() {
        initializeBoard();
    }

    private void initializeBoard() {
        Square squares[][] = board.getSquares();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j);
            }
        }
    }

    public void startGame() {

    }

    public void endGame() {

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    //etc...

}
