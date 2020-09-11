package chess.model;

import java.util.ArrayList;
import java.util.List;

public class Chess {
    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");

    Board board = new Board();

    List<Move> moves = new ArrayList<>();

    public Chess(String player1name, String player2name) {
        player1.name = player1name;
        player2.name = player2name;
    }

    public void initializeBoard() {
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
    //etc...

}
