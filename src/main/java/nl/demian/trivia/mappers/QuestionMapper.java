package nl.demian.trivia.mappers;

import nl.demian.trivia.domain.Option;
import nl.demian.trivia.domain.Question;
import nl.demian.trivia.domain.dtos.QuestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(source = "options", target = "options", qualifiedByName = "mapOptions")
    QuestionDTO toDTO(final Question entity);

    @Named("mapOptions")
    default Set<String> mapOptions(final Set<Option> options) {
        return options.stream().map(Option::getValue).collect(Collectors.toSet());
    }
}
