package com.askog.voting.api;

import com.askog.voting.entity.Question;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDto {

    private long id;

    private String text;

    private long numberOfVotes;

    public QuestionDto() {
    }

    public QuestionDto(long id, String text, long numberOfVotes) {
        this.id = id;
        this.text = text;
        this.numberOfVotes = numberOfVotes;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getNumberOfVotes() {
        return numberOfVotes;
    }

    public static QuestionDto fromDomain(Question question) {
        return new QuestionDto(question.getId(), question.getText(), 0);
    }

    public static List<QuestionDto> fromDomain(List<Question> questions) {
        return questions.stream().map(question -> fromDomain(question)).collect(Collectors.toList());
    }

    public static Question toDomain(QuestionDto questionDto) {
        return new Question(questionDto.getText());
    }

    public static List<Question> toDomain(List<QuestionDto> questionDtos) {
        return questionDtos.stream().map(questionDto -> toDomain(questionDto)).collect(Collectors.toList());
    }

    public void setNumberOfVotes(long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
