package nl.demian.trivia.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TriviaResponseDTO {

    @JsonProperty("response_code")
    int responseCode;

    @JsonProperty("results")
    QuestionResponseDTO[] results;
}
