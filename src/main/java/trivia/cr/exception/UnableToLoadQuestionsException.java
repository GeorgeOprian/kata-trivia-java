package trivia.cr.exception;

public class UnableToLoadQuestionsException extends RuntimeException {
    public UnableToLoadQuestionsException() {
        super("Unable to load the questions");
    }
}
