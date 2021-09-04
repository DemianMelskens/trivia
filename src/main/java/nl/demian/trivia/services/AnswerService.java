package nl.demian.trivia.services;

import nl.demian.trivia.domain.Answer;
import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AnswerService {

    private Map<String, String> answers = new HashMap<>();

    public void addAnswer(final Question question) {
        this.answers.put(
                question.getQuestion(),
                question.getOptions().stream()
                        .filter(Option::getCorrect)
                        .findFirst()
                        .map(Option::getValue)
                        .orElseThrow()
        );
    }

    public boolean checkAnswer(final Answer answer) {
        return this.answers.get(answer.getQuestion()).equalsIgnoreCase(answer.getAnswer());
    }
}
