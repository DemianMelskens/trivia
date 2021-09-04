package nl.demian.trivia.web;

import nl.demian.trivia.domain.Answer;
import nl.demian.trivia.domain.Difficulty;
import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import nl.demian.trivia.domain.dtos.AnswerDTO;
import nl.demian.trivia.domain.dtos.QuestionDTO;
import nl.demian.trivia.mappers.AnswerMapper;
import nl.demian.trivia.mappers.QuestionMapper;
import nl.demian.trivia.services.AnswerService;
import nl.demian.trivia.services.TriviaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TriviaResourceTest {

    @Mock
    TriviaService triviaService;

    @Mock
    AnswerService answerService;

    @Mock
    AnswerMapper answerMapper;

    @Mock
    QuestionMapper questionMapper;

    @InjectMocks
    TriviaResource triviaResource;

    @Test
    void getQuestions() {
        final Set<Question> questions = Set.of(
                new Question("", Difficulty.EASY, "test1", Set.of(new Option("test1", false))),
                new Question("", Difficulty.EASY, "test2", Set.of(new Option("test2", false))),
                new Question("", Difficulty.EASY, "test3", Set.of(new Option("test3", false)))
        );

        final QuestionDTO dto1 = new QuestionDTO("", "", "test1", Set.of());
        final QuestionDTO dto2 = new QuestionDTO("", "", "test2", Set.of());
        final QuestionDTO dto3 = new QuestionDTO("", "", "test3", Set.of());
        final Set<QuestionDTO> dtos = Set.of(dto1, dto2, dto3);

        doReturn(questions).when(triviaService).getQuestions();
        doReturn(dto1, dto2, dto3).when(questionMapper).toDTO(any());

        final Set<QuestionDTO> result = triviaResource.getQuestions();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrderElementsOf(dtos);
        verify(triviaService, times(1)).getQuestions();
        verify(questionMapper, times(3)).toDTO(any());
    }

    @Test
    void checkAnswer() {
        final AnswerDTO dto = new AnswerDTO("test", "test");
        final Answer answer = new Answer("test", "test");

        doReturn(answer).when(answerMapper).toEntity(dto);
        doReturn(true).when(answerService).checkAnswer(answer);

        final boolean result = triviaResource.checkAnswer(dto);

        assertThat(result).isTrue();
        verify(answerMapper, times(1)).toEntity(dto);
        verify(answerService, times(1)).checkAnswer(answer);
    }
}
