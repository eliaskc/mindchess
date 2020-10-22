package mindchess.model;

/**
 * Enum for the type of square a Square can be, so that special rules can be applied
 */
public enum SquareType {
    NORMAL,
    EN_PASSANT,
    CASTLING,
    PROMOTION,
    IN_CHECK
}
