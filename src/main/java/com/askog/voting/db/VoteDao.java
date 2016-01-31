package com.askog.voting.db;

import com.askog.voting.entity.Question;
import com.askog.voting.entity.Vote;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class VoteDao extends AbstractDAO<Vote> {

    public VoteDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Vote create(Vote vote) {
        return persist(vote);
    }

    public long findAllWithQuestion(Question question) {
        org.hibernate.Query query = namedQuery("com.askog.voting.entity.Vote.countAllWithQuestion");
        query.setParameter("question", question);
        return ((Long) query.uniqueResult()).longValue();
    }
}
