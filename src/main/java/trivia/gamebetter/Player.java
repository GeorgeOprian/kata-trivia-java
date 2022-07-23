package trivia.gamebetter;

public class Player {
    private final String name;
    private int currentLocation;
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

    public void incrementPurse() {
        goldCoins++;
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

    @Override
    public String toString() {
        return name;
    }
}