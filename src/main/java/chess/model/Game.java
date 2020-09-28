package chess.model;

public class Game {
    private Board board = new Board();

    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");

    public void handleBoardClick(int x, int y) {
        board.handleBoardClick(x, y);
    }

    public void initGame() {
        board.initBoard();
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
