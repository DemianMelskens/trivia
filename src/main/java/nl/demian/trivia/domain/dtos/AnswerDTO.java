package nl.demian.trivia.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AnswerDTO {

    @NotBlank
    String question;

    @NotBlank
    String answer;
}
