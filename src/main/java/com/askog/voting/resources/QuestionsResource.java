package com.askog.voting.resources;

import com.askog.voting.db.QuestionDao;
import com.askog.voting.entity.Question;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/questions")
@Produces(MediaType.APPLICATION_JSON)
public class QuestionsResource {
    private final QuestionDao questionDao;

    public QuestionsResource(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @GET
    @UnitOfWork
    @Timed
    public List<Question> listQuestions() {
        return questionDao.findAll();
    }

    @GET
    @Path("/{questionId}")
    @UnitOfWork
    @Timed
    public Question getQuestion(@PathParam("questionId") LongParam personId) {
        return findSafely(personId.get());
    }

    @POST
    @UnitOfWork
    @Timed
    public Question createQuestion(Question question) {
        return questionDao.create(question);
    }

    private Question findSafely(long questionId) {
        Optional<Question> question = questionDao.findById(questionId);
        if (!question.isPresent()) {
            throw new NotFoundException("No such question.");
        }
        return question.get();
    }
}
