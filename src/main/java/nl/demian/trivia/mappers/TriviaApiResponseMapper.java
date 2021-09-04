package nl.demian.trivia.mappers;

import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import nl.demian.trivia.domain.dtos.QuestionResponseDTO;
import nl.demian.trivia.domain.dtos.TriviaResponseDTO;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class TriviaApiResponseMapper {

    public Set<Question> toQuestions(final TriviaResponseDTO dto) {
        return Arrays.stream(dto.getResults()).map(this::toQuestion).collect(Collectors.toSet());
    }

    @Mapping(target = "options", ignore = true)
    abstract Question toQuestion(final QuestionResponseDTO dto);

    @BeforeMapping
    protected void prepareQuestionResponseDTO(final QuestionResponseDTO dto) {
        dto.setDifficulty(dto.getDifficulty().toUpperCase());
    }

    @AfterMapping
    protected void setOptions(@MappingTarget final Question question, final QuestionResponseDTO dto) {
        Set<Option> options = dto.getIncorrectAnswers().stream().map(this::toOption).collect(Collectors.toSet());
        options.add(toOption(dto.getCorrectAnswer(), true));
        question.setOptions(options);
    }

    private Option toOption(final String answer) {
        return toOption(answer, false);
    }

    private Option toOption(final String answer, boolean correct) {
        return new Option(answer, correct);
    }
}
