package mindchess.model;

public interface IPlayer {
    String getName();

    int getCurrentTime();

    ChessColor getColor();

    ChessTimer getTimer();

    PlayerType getPlayerType();
}
