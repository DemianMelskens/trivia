package nl.demian.trivia.mappers;

import nl.demian.trivia.domain.Difficulty;
import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import nl.demian.trivia.domain.dtos.QuestionResponseDTO;
import nl.demian.trivia.domain.dtos.TriviaResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TriviaApiResponseMapperTest {

    TriviaApiResponseMapper triviaApiResponseMapper = new TriviaApiResponseMapperImpl();

    @Test
    void toQuestions() {
        final QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setCategory("test");
        questionResponseDTO.setDifficulty("easy");
        questionResponseDTO.setQuestion("how to test");
        questionResponseDTO.setCorrectAnswer("all logic");
        questionResponseDTO.setIncorrectAnswers(Set.of(
                "you don't",
                "only few object",
                "all external libraries"
        ));

        final TriviaResponseDTO triviaResponseDTO = new TriviaResponseDTO();
        triviaResponseDTO.setResponseCode(0);
        triviaResponseDTO.setResults(new QuestionResponseDTO[]{questionResponseDTO});


        Set<Question> result = triviaApiResponseMapper.toQuestions(triviaResponseDTO);

        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().orElse(null)).isNotNull();
        assertThat(Objects.requireNonNull(result.stream().findFirst().orElse(null)).getCategory()).isEqualTo("test");
        assertThat(Objects.requireNonNull(result.stream().findFirst().orElse(null)).getDifficulty()).isEqualTo(Difficulty.EASY);
        assertThat(Objects.requireNonNull(result.stream().findFirst().orElse(null)).getQuestion()).isEqualTo("how to test");
        assertThat(Objects.requireNonNull(result.stream().findFirst().orElse(null)).getOptions()).containsExactlyInAnyOrderElementsOf(Set.of(
                new Option("you don't", false),
                new Option("only few object", false),
                new Option("all external libraries", false),
                new Option("all logic", true)
        ));
    }
}
