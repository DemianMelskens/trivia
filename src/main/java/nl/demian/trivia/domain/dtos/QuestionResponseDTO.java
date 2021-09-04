package nl.demian.trivia.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class QuestionResponseDTO {

    @JsonProperty("category")
    String category;

    @JsonProperty("type")
    String type;

    @JsonProperty("difficulty")
    String difficulty;

    @JsonProperty("question")
    String question;

    @JsonProperty("correct_answer")
    String correctAnswer;

    @JsonProperty("incorrect_answers")
    Set<String> incorrectAnswers;
}
