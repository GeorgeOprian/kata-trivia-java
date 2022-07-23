package trivia.cr.exception;

public class IncorrectNumberOfPlayersException extends RuntimeException {

    public IncorrectNumberOfPlayersException(int minNumberOfPlayers, int maxNumberOfPlayers) {
        super(String.format("The number of players must be between %s and %s", minNumberOfPlayers, maxNumberOfPlayers));
    }
}
