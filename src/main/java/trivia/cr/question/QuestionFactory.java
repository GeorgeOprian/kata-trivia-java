package trivia.cr.question;

import trivia.cr.question.Question.Category;

public class QuestionFactory {

    public Question getQuestion(Category category, String questionText) {
        switch (category) {
            case POP:
                return new PopQuestion(category, questionText);
            case SCIENCE:
                return new ScienceQuestion(category, questionText);
            case SPORTS:
                return new SportsQuestion(category, questionText);
            case ROCK:
                return new RockQuestion(category, questionText);
            case GEOGRAPHY:
            default:
                return new GeographyQuestion(category, questionText);
        }
    }

}
