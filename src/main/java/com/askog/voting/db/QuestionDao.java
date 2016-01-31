package com.askog.voting.db;

import com.askog.voting.entity.Question;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class QuestionDao extends AbstractDAO<Question> {

    public QuestionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Question create(Question question) {
        return persist(question);
    }

    public List<Question> findAll() {
        return list(namedQuery("com.askog.voting.entity.Question.findAll"));
    }
}
