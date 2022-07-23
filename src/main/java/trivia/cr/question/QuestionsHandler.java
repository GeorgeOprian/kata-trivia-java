package trivia.cr.question;

import trivia.cr.exception.UnableToLoadQuestionsException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class QuestionsHandler {

    private static final int NUMBER_OF_QUESTIONS_FOR_CATEGORY = 50;
    private final QuestionFactory factory;
    private final Map<Question.Category, Deque<Question>> questions = new EnumMap<>(Question.Category.class);

    public QuestionsHandler(Question.Category... categories) throws IOException {
        factory = new QuestionFactory();
        for (Question.Category category : categories) {
            addQuestionsForCategory(category);
        }
    }

    private void addQuestionsForCategory(Question.Category category) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();
        try (InputStream stream = loader.getResourceAsStream(category.getCategory().toLowerCase() + ".properties")) {
            prop.load(stream);
            for (int i = 0; i < NUMBER_OF_QUESTIONS_FOR_CATEGORY; i++) {
                addQuestion(factory.getQuestion(category, prop.getProperty("question_" + i)), category);
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    private void addQuestion(Question question, Question.Category category) {
        questions.computeIfAbsent(category, f -> new LinkedList<>());
        questions.get(category).addLast(question);
    }

    public Question removeQuestion(Question.Category category) {
        return questions.get(category).removeFirst();
    }

    public int getNumberOfCategories() {
        return questions.size();
    }
}
