package trivia.gamebetter.question;

import trivia.gamebetter.question.Question.Category;

public class QuestionFactory {

    public Question getQuestion(Category category, int index) {
        switch (category) {
            case POP:
                return new PopQuestion(category, index);
            case SCIENCE:
                return new ScienceQuestion(category, index);
            case SPORTS:
                return new SportsQuestion(category, index);
            case ROCK:
            default:
                return new RockQuestion(category, index);
        }
    }

}
