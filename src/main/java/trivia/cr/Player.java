package trivia.cr;

import java.util.Objects;

public class Player {
    private final String name;
    private int currentLocation;
    private int correctlyAnsweredQuestionsInARow;
    private int incorrectlyAnsweredQuestionsInARow;
    private int goldCoins;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.currentLocation = 0;
        this.goldCoins = 0;
        this.inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getGoldCoins() {
        return goldCoins;
    }

    public void addNumberOfCoins(int numberOfCoins) {
        goldCoins += numberOfCoins;
    }

    public void setGoldCoins(int goldCoins) {
        this.goldCoins = goldCoins;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public void movePosition(int numberOfPlaces) {
        currentLocation += numberOfPlaces;
    }

    public int getCorrectlyAnsweredQuestionsInARow() {
        return correctlyAnsweredQuestionsInARow;
    }

    public void setCorrectlyAnsweredQuestionsInARow(int correctlyAnsweredQuestionsInARow) {
        this.correctlyAnsweredQuestionsInARow = correctlyAnsweredQuestionsInARow;
    }

    public void incrementCorrectlyAnsweredQuestionsInARow() {
        correctlyAnsweredQuestionsInARow++;
    }

    public void resetCorrectlyAnsweredQuestionsInARow() {
        correctlyAnsweredQuestionsInARow = 0;
    }

    public int getIncorrectlyAnsweredQuestionsInARow() {
        return incorrectlyAnsweredQuestionsInARow;
    }

    public void setIncorrectlyAnsweredQuestionsInARow(int incorrectlyAnsweredQuestionsInARow) {
        this.incorrectlyAnsweredQuestionsInARow = incorrectlyAnsweredQuestionsInARow;
    }

    public void incrementIncorrectlyAnsweredQuestionsInARow() {
        incorrectlyAnsweredQuestionsInARow++;
    }

    public void resetIncorrectlyAnsweredQuestionsInARow() {
        incorrectlyAnsweredQuestionsInARow = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }
}