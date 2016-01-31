package com.askog.voting.entity;

import javax.persistence.*;

@Entity
@Table(name = "questions")
@NamedQueries({
        @NamedQuery(
                name = "com.askog.voting.entity.Question.findAll",
                query = "SELECT q FROM Question q"
        )
})
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text", nullable = false)
    private String text;

    public Question() {
    }

    public Question(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
