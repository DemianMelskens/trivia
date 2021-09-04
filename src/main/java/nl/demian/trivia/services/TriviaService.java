package nl.demian.trivia.services;

import lombok.extern.log4j.Log4j2;
import nl.demian.trivia.domain.Question;
import nl.demian.trivia.domain.dtos.TriviaResponseDTO;
import nl.demian.trivia.mappers.TriviaApiResponseMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Set;

@Log4j2
@Service
public class TriviaService {

    private final String triviaUrl;
    private final int amount;

    private final RestTemplate restTemplate;
    private final AnswerService answerService;
    private final TriviaApiResponseMapper triviaApiResponseMapper;

    public TriviaService(
            @Value("${trivia.api.url}") final String triviaUrl,
            @Value("${trivia.amount}") final int amount,
            final RestTemplate restTemplate,
            final AnswerService answerService,
            final TriviaApiResponseMapper triviaApiResponseMapper
    ) {
        this.triviaUrl = triviaUrl;
        this.amount = amount;
        this.restTemplate = restTemplate;
        this.answerService = answerService;
        this.triviaApiResponseMapper = triviaApiResponseMapper;
    }

    public Set<Question> getQuestions() {
        try {
            final Set<Question> questions = this.triviaApiResponseMapper.toQuestions(
                    Objects.requireNonNull(this.restTemplate.getForObject(triviaUrl + amount, TriviaResponseDTO.class))
            );
            questions.forEach(answerService::addAnswer);
            return questions;
        } catch (RestClientException | NullPointerException ex) {
            log.error(ex);
            return Set.of();
        }
    }
}
