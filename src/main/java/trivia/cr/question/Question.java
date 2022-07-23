package trivia.cr.question;

public abstract class Question {

    public enum Category {
        POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock"), GEOGRAPHY("Geography");

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
    private final String questionText;

    public Question(Category category, String questionText) {
        this.category = category;
        this.questionText = questionText;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return questionText;
    }
}
