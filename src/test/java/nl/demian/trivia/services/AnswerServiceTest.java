package nl.demian.trivia.services;

import nl.demian.trivia.domain.Answer;
import nl.demian.trivia.domain.Difficulty;
import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerServiceTest {

    private AnswerService answerService;

    @BeforeEach
    void setUp() {
        this.answerService = new AnswerService();
    }

    @Test
    void addAnswerAndCheckAnswer() {
        final Question question = new Question(
                "test",
                Difficulty.EASY,
                "how to test",
                Set.of(
                        new Option("you don't", false),
                        new Option("only few object", false),
                        new Option("all logic", true),
                        new Option("all external libraries", false)
                )
        );
        answerService.addAnswer(question);

        assertThat(answerService.checkAnswer(new Answer("how to test", "only few object"))).isFalse();
        assertThat(answerService.checkAnswer(new Answer("how to test", "all logic"))).isTrue();
    }
}
