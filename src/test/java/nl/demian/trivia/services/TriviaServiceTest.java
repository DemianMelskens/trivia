package nl.demian.trivia.services;

import nl.demian.trivia.domain.Difficulty;
import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import nl.demian.trivia.domain.dtos.TriviaResponseDTO;
import nl.demian.trivia.mappers.TriviaApiResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TriviaServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    AnswerService answerService;

    @Mock
    TriviaApiResponseMapper triviaApiResponseMapper;

    private TriviaService triviaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.triviaService = new TriviaService("https://opentdb.com/api.php?amount=", 5, restTemplate, answerService, triviaApiResponseMapper);
    }

    @Test
    void getQuestions() {
        final TriviaResponseDTO responseDTO = new TriviaResponseDTO();
        final Set<Question> questions = Set.of(
                new Question("", Difficulty.EASY, "test1", Set.of(new Option("test1", false))),
                new Question("", Difficulty.EASY, "test2", Set.of(new Option("test2", false))),
                new Question("", Difficulty.EASY, "test3", Set.of(new Option("test3", false)))
        );

        doReturn(responseDTO).when(restTemplate).getForObject(eq("https://opentdb.com/api.php?amount=5"), eq(TriviaResponseDTO.class));
        doReturn(questions).when(triviaApiResponseMapper).toQuestions(eq(responseDTO));
        doNothing().when(answerService).addAnswer(any());

        final Set<Question> result = triviaService.getQuestions();

        assertThat(result).containsExactlyInAnyOrderElementsOf(questions);
        verify(restTemplate, times(1)).getForObject(eq("https://opentdb.com/api.php?amount=5"), eq(TriviaResponseDTO.class));
        verify(triviaApiResponseMapper, times(1)).toQuestions(eq(responseDTO));
        verify(answerService, times(3)).addAnswer(any());
    }

    @Test
    void getQuestionsNullPointerException() {
        doReturn(null).when(restTemplate).getForObject(eq("https://opentdb.com/api.php?amount=5"), eq(TriviaResponseDTO.class));

        final Set<Question> result = triviaService.getQuestions();

        assertThat(result).isEmpty();
    }

    @Test
    void getQuestionsRestClientException() {
        doThrow(new RestClientException("Test error")).when(restTemplate).getForObject(eq("https://opentdb.com/api.php?amount=5"), eq(TriviaResponseDTO.class));

        final Set<Question> result = triviaService.getQuestions();

        assertThat(result).isEmpty();
    }
}
