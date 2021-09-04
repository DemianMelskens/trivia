package nl.demian.trivia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Question {

    private String category;
    private Difficulty difficulty;
    private String question;
    private Set<Option> options;
}
