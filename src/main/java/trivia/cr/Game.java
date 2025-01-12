package trivia.cr;

import trivia.cr.exception.GameStartedException;

import java.util.ArrayList;
import java.util.LinkedList;


// TODO refactor me
public class Game implements IGame {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];
    int[] incorrectlyAnsweredQuestionsInARow = new int[6];
    int[] correctlyAnsweredQuestionsInARow = new int[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    LinkedList geographyQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    private boolean gameStarted;

    public Game() {
        for (int i = 0; i < 100; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
            geographyQuestions.addLast("Geography Question " + i);
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        if (gameStarted) {
            throw new GameStartedException("The game has already started. More players can't be added...");
        }


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        gameStarted = true;
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 14) places[currentPlayer] = places[currentPlayer] - 15;

                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 14) places[currentPlayer] = places[currentPlayer] - 15;

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
        if (currentCategory() == "Geography")
            System.out.println(geographyQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        if (places[currentPlayer] == 3) return "Rock";
        if (places[currentPlayer] == 7) return "Rock";
        if (places[currentPlayer] == 11) return "Rock";
        return "Geography";
    }

    public boolean wasCorrectlyAnswered() {
        correctlyAnsweredQuestionsInARow[currentPlayer] ++;
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");

                if (playerIsOnAStreak()) {
                    purses[currentPlayer] += GameBetter.NUMBER_OF_COINS_AWARDED_ON_STREAK;
                } else {
                    purses[currentPlayer]++;
                }

                System.out.println(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }

        } else {

//         System.out.println("Answer was corrent!!!!"); //Typooo
            System.out.println("Answer was correct!!!!");
            if (playerIsOnAStreak()) {
                purses[currentPlayer] += GameBetter.NUMBER_OF_COINS_AWARDED_ON_STREAK;
            } else {
                purses[currentPlayer]++;
            }
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");

        if (playerIsOnAStreak()) {
            correctlyAnsweredQuestionsInARow[currentPlayer] = 0;
        }

        if (incorrectlyAnsweredQuestionsInARow[currentPlayer] == 0) {
            incorrectlyAnsweredQuestionsInARow[currentPlayer]++;
            return true;
        }

        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;
        incorrectlyAnsweredQuestionsInARow[currentPlayer] = 0;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }

    private boolean playerIsOnAStreak() {
        return correctlyAnsweredQuestionsInARow[currentPlayer] >= GameBetter.REQUIRED_QUESTIONS_FOR_STREAK;
    }

    @Override
    public Player getCurrentPlayer() {
        return new Player(players.get(currentPlayer).toString());
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 12);
    }
}
