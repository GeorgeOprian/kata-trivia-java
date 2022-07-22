package trivia.gamebetter.question;

public abstract class Question {

    public enum Category {
        POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");

        private final String category;

        Category(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return category;
        }
    }

    private final Category category;
    private final int index;

    public Question(Category category, int index) {
        this.category = category;
        this.index = index;
    }

    public Category getCategory() {
        return category;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return category.toString() + " Question " + index;
    }
}
