package trivia.cr.question;

public class DuplicatePlayerNameException extends RuntimeException {

    public DuplicatePlayerNameException() {
        super("Player names must be unique");
    }
}
