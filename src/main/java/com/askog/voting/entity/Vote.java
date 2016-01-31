package com.askog.voting.entity;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@NamedQueries({
        @NamedQuery(
                name = "com.askog.voting.entity.Vote.countAllWithQuestion",
                query = "SELECT count(v) FROM Vote v where v.question like :question"
        )
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    public Vote() {
    }

    public Vote(Question question) {
        this.question = question;
    }

    public long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }
}
