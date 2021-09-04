package nl.demian.trivia.web;

import lombok.RequiredArgsConstructor;
import nl.demian.trivia.domain.dtos.AnswerDTO;
import nl.demian.trivia.domain.dtos.QuestionDTO;
import nl.demian.trivia.mappers.AnswerMapper;
import nl.demian.trivia.mappers.QuestionMapper;
import nl.demian.trivia.services.AnswerService;
import nl.demian.trivia.services.TriviaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trivia")
public class TriviaResource {

    final TriviaService triviaService;
    final AnswerService answerService;

    final AnswerMapper answerMapper;
    final QuestionMapper questionMapper;

    @GetMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<QuestionDTO> getQuestions() {
        return triviaService.getQuestions().stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @PostMapping(value = "/checkanswer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean checkAnswer(@Valid @RequestBody final AnswerDTO dto) {
        return answerService.checkAnswer(this.answerMapper.toEntity(dto));
    }
}
