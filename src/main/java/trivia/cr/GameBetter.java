package trivia.cr;

import trivia.IGame;
import trivia.cr.exception.IncorrectNumberOfPlayersException;
import trivia.cr.exception.UnableToLoadQuestionsException;
import trivia.cr.question.DuplicatePlayerNameException;
import trivia.cr.question.Question;
import trivia.cr.question.QuestionsHandler;

import java.io.IOException;
import java.util.*;

// REFACTOR ME
public class GameBetter implements IGame {

    public static final int MIN_NUMBER_OF_PLAYERS = 2;
    public static final int MAX_NUMBER_OF_PLAYERS = 6;
    private static int BOARD_SIZE;
    private final QuestionsHandler questionsHandler;

    private final List<Player> players = new ArrayList<>();

    private final int PLACES_ON_BOARD_PER_CATEGORY;

    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public GameBetter() throws IncorrectNumberOfPlayersException {
        if (!isPlayable()) {
            throw new IncorrectNumberOfPlayersException(MIN_NUMBER_OF_PLAYERS, MAX_NUMBER_OF_PLAYERS);
        }

        try {
            questionsHandler = new QuestionsHandler(Question.Category.POP,
                    Question.Category.SCIENCE,
                    Question.Category.SPORTS,
                    Question.Category.ROCK,
                    Question.Category.GEOGRAPHY);
        } catch (IOException e) {
            throw new UnableToLoadQuestionsException();
        }

        PLACES_ON_BOARD_PER_CATEGORY = 3;
        BOARD_SIZE = questionsHandler.getNumberOfCategories() * PLACES_ON_BOARD_PER_CATEGORY;
    }

    public boolean isPlayable() {
        return (getCurrentNumberOfPlayers() > MIN_NUMBER_OF_PLAYERS || getCurrentNumberOfPlayers() < MAX_NUMBER_OF_PLAYERS);
    }

    public boolean add(String playerName) {
        Player newPlayer = new Player(playerName);

        if (players.contains(newPlayer)) {
            throw new DuplicatePlayerNameException();
        }

        players.add(newPlayer);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + getCurrentNumberOfPlayers());
        return true;
    }

    public int getCurrentNumberOfPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (!currentPlayer.isInPenaltyBox()) {
            movePlayerAndAskQuestion(currentPlayer, roll);
            return;
        }

        handlePenaltyBoxCases(roll, currentPlayer);
    }

    private void handlePenaltyBoxCases(int roll, Player currentPlayer) {
        isGettingOutOfPenaltyBox = roll % 2 != 0;
        if (!isGettingOutOfPenaltyBox) {
            System.out.println(currentPlayer + " is not getting out of the penalty box");
            return;
        }

        System.out.println(currentPlayer + " is getting out of the penalty box");
        movePlayerAndAskQuestion(currentPlayer, roll);
    }


    private void tryToGetPlayerOutOfPenaltyBox(int roll, Player currentPlayer) {
        isGettingOutOfPenaltyBox = roll % 2 != 0;
        if (isGettingOutOfPenaltyBox) {
            System.out.println(currentPlayer + " is getting out of the penalty box");

            movePlayerAndAskQuestion(currentPlayer, roll);
            return;
        }

        System.out.println(currentPlayer + " is not getting out of the penalty box");
    }

    private void movePlayerAndAskQuestion(Player currentPlayer, int roll) {
        movePlayer(currentPlayer, roll);
        askQuestion();
    }

    private void movePlayer(Player currentPlayer, int roll) {
        currentPlayer.movePosition(roll);
        if (currentPlayer.getCurrentLocation() > BOARD_SIZE - 1) {
            currentPlayer.movePosition(-BOARD_SIZE);
        }

        System.out.println(currentPlayer + "'s new location is " + currentPlayer.getCurrentLocation());
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        Question question = questionsHandler.removeQuestion(currentCategory());
        System.out.println(question);
    }


    private Question.Category currentCategory() {
        int currentLocation = players.get(currentPlayerIndex).getCurrentLocation();
//        int questionCategoryOrdinal = currentLocation % PLACES_ON_BOARD_PER_CATEGORY;
//        return Question.Category.getByOrdinal(questionCategoryOrdinal);

        switch (currentLocation) {
            case 0:
            case 4:
            case 8:
                return Question.Category.POP;
            case 1:
            case 5:
            case 9:
                return Question.Category.SCIENCE;
            case 2:
            case 6:
            case 10:
                return Question.Category.SPORTS;
            case 3:
            case 7:
            case 11:
                return Question.Category.ROCK;
            default:
                return Question.Category.GEOGRAPHY;
        }
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean notAWinner = true;
        if (currentPlayer.isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            switchToNextPlayer();
            return notAWinner;
        }

        awardPlayer(currentPlayer);
        notAWinner = didPlayerNotWin();
        switchToNextPlayer();

        return notAWinner;
    }

    private void awardPlayer(Player currentPlayer) {
        System.out.println("Answer was correct!!!!");

        currentPlayer.incrementPurse();

        System.out.println(currentPlayer + " now has " + currentPlayer.getGoldCoins() + " Gold Coins.");
    }

    private void switchToNextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
    }

    public boolean wrongAnswer() {
        boolean notAWinner = true;
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.getIncorrectlyAnsweredQuestionsInARow() == 0) {
            currentPlayer.incrementNumberOfQuestionsIncorrectlyAnsweredInARow();
            return notAWinner;
        }

        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer + " was sent to the penalty box");
        currentPlayer.resetIncorrectlyAnsweredQuestionsInARow();
        currentPlayer.setInPenaltyBox(true);

        switchToNextPlayer();
        return notAWinner;
    }

    private boolean didPlayerNotWin() {
        return players.get(currentPlayerIndex).getGoldCoins() != 6; //posibil bug poate trebuia sa fie > 6
    }
}
