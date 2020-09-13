package chess.model;

import chess.model.pieces.Piece;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.fxml.FXML;

import java.net.SocketTimeoutException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Chess {
    private static Chess instance = null;

    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");

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
        board.initializeBoard();
        board.pieces.add(PieceFactory.createBishop(board.getSquares()[0][0],true, Color.WHITE));
        board.fetchPieceImages();
    }

    public void startGame() {}

    public void endGame() {}

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    //find the square where the mouse clicked
    public void findSquare(double mouseX, double mouseY){
        //Check pieces squares
        //Check if marked piece exists
        //move or mark piece
        int x = translateX(mouseX);
        int y = translateY(mouseY);
        //System.out.println(x + " " + y);

        if (!pieceClicked(x, y)) {
            if (!(board.markedPiece == null)) {
                board.markedPiece.setPosition(board.squares[x][y]);
                board.setMarkedPiece(null);
            }
        }

        System.out.println(x + " " + y + " " + board.getMarkedStatus());
        System.out.println(board.markedPiece);
    }


    //Checks if the click is inside the square being checked
    private boolean checkSquare(Piece piece, double x, double y){
        if((piece.getPosition().getCoordinatesX() == x && piece.getPosition().getCoordinatesY() == y)){
            return true;
        }
        return false;
    }

    //Sets the mouse clicks x to the a value 0-7 corresponding to the correct square
    private int translateX(double x) {
        for (int i = 0; i < 8; i++) {
            if((i * 75 + 359 <= x && x <= 359 + 75*(i+1))){
                return i;
            }
        }
        return -1;
    }

    //Sets the mouse clicks y to the a value 0-7 corresponding to the correct square
    private int translateY(double y) {
        for (int i = 0; i < 8; i++) {
            if((i * 75 + 79 <= y && y <= 79 + 75*(i+1))){
                return i;
            }
        }
        return -1;
    }

    //If the square that was clicked has a piece then return true
    private boolean pieceClicked(int x, int y) {
        for (int i = 0; i < board.pieces.size(); i++) {
            if(checkSquare(board.pieces.get(i),x,y)){
                board.setMarkedPiece(board.pieces.get(i));
                return true;
            }
        }
        return false;
    }



    //etc...

}
