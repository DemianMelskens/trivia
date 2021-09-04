package nl.demian.trivia.mappers;

import nl.demian.trivia.domain.Difficulty;
import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import nl.demian.trivia.domain.dtos.QuestionDTO;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class QuestionMapperTest {

    private final QuestionMapper questionMapper = new QuestionMapperImpl();

    @Test
    void toDTO() {
        final Question question = new Question("test", Difficulty.EASY, "how to test", Set.of(
                new Option("you don't", false),
                new Option("only few object", false),
                new Option("all logic", true),
                new Option("all external libraries", false)
        ));

        final QuestionDTO result = questionMapper.toDTO(question);

        assertThat(result).isNotNull();
        assertThat(result.getCategory()).isEqualTo("test");
        assertThat(result.getDifficulty()).isEqualTo("EASY");
        assertThat(result.getQuestion()).isEqualTo("how to test");
        assertThat(result.getOptions()).containsExactlyInAnyOrderElementsOf(Set.of(
                "you don't",
                "only few object",
                "all logic",
                "all external libraries"
        ));
    }
}
