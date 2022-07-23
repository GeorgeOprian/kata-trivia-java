package trivia.cr.exception;

public class GameStartedException extends RuntimeException {
    public GameStartedException(String message) {
        super(message);
    }
}
