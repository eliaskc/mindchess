package mindchess.model;

/**
 * Class Player represents a player playing mindchess and contains attributes for that player
 */
public class Player implements IPlayer {
    private final ChessTimer chessTimer = new ChessTimer();
    private String name;
    private final ChessColor chessColor;
    private PlayerType playerType;

    Player(String name, ChessColor chessColor, PlayerType playerType, Integer gameLength) {
        this.name = name;
        this.chessColor = chessColor;
        this.playerType = playerType;
        this.chessTimer.setTime(gameLength);
    }

    public String getName() {
        return name;
    }

    public int getCurrentTime(){
        return chessTimer.getTime();
    }

    public ChessColor getColor() {
        return chessColor;
    }

    public ChessTimer getTimer() {
        return chessTimer;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
