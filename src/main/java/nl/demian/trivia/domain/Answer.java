package nl.demian.trivia.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Answer {

    private final String question;
    private final String answer;
}
