package chess.model;

public class Game {
    private Board board = new Board();

    public void handleBoardClick(int x, int y){
        board.handleBoardClick(x, y);
    }

    public void initGame(){
        board.initBoard();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
