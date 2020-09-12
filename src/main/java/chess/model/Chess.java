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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(352 + i * 71, 82 + j * 71);              //Hard coded until we find better solution(based on the imageview)
            }
        }
        squares[0][0].setPiece(PieceFactory.createPawn(true, Color.BLACK));
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

    //find the square where the mouse clicked
    public void findSquare(double x, double y){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(checkSquare(i,j,x,y)){
                    System.out.println(i + " " + j + " " + board.getSquares()[i][j].getPieceName());
                    break;
                }
            }
        }
    }

    private boolean checkSquare(int i, int j, double x, double y){
        if((board.getSquares()[i][j].getCoordinatesX() < x && x < board.getSquares()[i][j].getCoordinatesX()+71)){
            if((board.getSquares()[i][j].getCoordinatesY() < y && y < board.getSquares()[i][j].getCoordinatesY()+71)){
                return true;
            }
        }
        return false;
    }

    //etc...

}
