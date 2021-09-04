package nl.demian.trivia.mappers;

import nl.demian.trivia.domain.Answer;
import nl.demian.trivia.domain.dtos.AnswerDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerMapperTest {

    private final AnswerMapper answerMapper = new AnswerMapperImpl();

    @Test
    void toEntity() {
        AnswerDTO answerDTO = new AnswerDTO("how to test", "all logic");

        Answer result = answerMapper.toEntity(answerDTO);

        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo("how to test");
        assertThat(result.getAnswer()).isEqualTo("all logic");
    }
}
