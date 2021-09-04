package nl.demian.trivia.mappers;

import nl.demian.trivia.domain.Answer;
import nl.demian.trivia.domain.dtos.AnswerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer toEntity(AnswerDTO dto);
}
