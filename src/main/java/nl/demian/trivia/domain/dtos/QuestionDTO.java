package nl.demian.trivia.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private String category;
    private String difficulty;
    private String question;
    private Set<String> options;
}
