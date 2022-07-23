package trivia.gamebetter;

import trivia.IGame;
import trivia.gamebetter.question.Question;
import trivia.gamebetter.question.QuestionFactory;

import java.util.*;

// REFACTOR ME
public class GameBetter implements IGame {

    private static final int MIN_NUMBER_OF_PLAYERS = 2;
    private static final int MAX_NUMBER_OF_PLAYERS = 6;
    private static final int BOARD_SIZE = 12;
    private static final int NUMBER_OF_QUESTIONS_FOR_CATEGORY = 50;

    private final List<Player> players = new ArrayList<>();

    private final Map<Question.Category, Deque<Question>> questions = new EnumMap<>(Question.Category.class);

    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public GameBetter() {
        initQuestions();
    }

    private void initQuestions() {
        QuestionFactory factory = new QuestionFactory();
        for (int i = 0; i < NUMBER_OF_QUESTIONS_FOR_CATEGORY; i++) {
            addQuestionOfCategory(factory.getQuestion(Question.Category.POP, i), Question.Category.POP);
            addQuestionOfCategory(factory.getQuestion(Question.Category.SCIENCE, i), Question.Category.SCIENCE);
            addQuestionOfCategory(factory.getQuestion(Question.Category.SPORTS, i), Question.Category.SPORTS);
            addQuestionOfCategory(factory.getQuestion(Question.Category.ROCK, i), Question.Category.ROCK);
        }
    }

    private void addQuestionOfCategory(Question question, Question.Category category) {
        questions.computeIfAbsent(category, f -> new LinkedList<>());
        questions.get(category).addLast(question);
    }

    public boolean isPlayable() {
        return (getCurrentNumberOfPlayers() >= MIN_NUMBER_OF_PLAYERS);
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

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
        Question question = questions.get(currentCategory()).removeFirst();
        System.out.println(question);
    }


    private Question.Category currentCategory() {

        int currentLocation = players.get(currentPlayerIndex).getCurrentLocation();

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
            default:
                return Question.Category.ROCK;
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
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayerIndex) + " was sent to the penalty box");
        players.get(currentPlayerIndex).setInPenaltyBox(true);

        switchToNextPlayer();
        return true;
    }

    private boolean didPlayerNotWin() {
        return players.get(currentPlayerIndex).getGoldCoins() != 6; //posibil bug poate trebuia sa fie > 6
    }

}
