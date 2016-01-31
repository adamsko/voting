package com.askog.voting.resources;

import com.askog.voting.api.QuestionDto;
import com.askog.voting.db.QuestionDao;
import com.askog.voting.db.VoteDao;
import com.askog.voting.entity.Question;
import com.askog.voting.entity.Vote;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/questions")
@Produces(MediaType.APPLICATION_JSON)
public class QuestionsResource {
    private final QuestionDao questionDao;
    private final VoteDao voteDao;

    public QuestionsResource(QuestionDao questionDao, VoteDao voteDao) {
        this.questionDao = questionDao;
        this.voteDao = voteDao;
    }

    @GET
    @UnitOfWork
    @Timed
    public List<QuestionDto> listQuestions() {
        List<QuestionDto> dtos = new ArrayList<>();
        for (Question question : questionDao.findAll()) {
            QuestionDto dto = QuestionDto.fromDomain(question);
            dto.setNumberOfVotes(numberOfVotesForQuestion(question));
            dtos.add(dto);
        }
        return dtos;
    }

    @GET
    @Path("/{questionId}")
    @UnitOfWork
    @Timed
    public QuestionDto getQuestion(@PathParam("questionId") LongParam questionId) {
        Question question = findSafely(questionId.get());
        QuestionDto questionDto = QuestionDto.fromDomain(question);
        questionDto.setNumberOfVotes(numberOfVotesForQuestion(question));
        return questionDto;
    }

    @POST
    @UnitOfWork
    @Timed
    public QuestionDto createQuestion(QuestionDto question) {
        return QuestionDto.fromDomain(questionDao.create(QuestionDto.toDomain(question)));
    }

    @POST
    @Path("/{questionId}/vote")
    @UnitOfWork
    @Timed
    public Response vote(@PathParam("questionId") LongParam questionId) {
        voteDao.create(new Vote(findSafely(questionId.get())));
        return Response.accepted().build();
    }

    private long numberOfVotesForQuestion(Question question) {
        return voteDao.findAllWithQuestion(question);
    }

    private Question findSafely(long questionId) {
        Optional<Question> question = questionDao.findById(questionId);
        if (!question.isPresent()) {
            throw new NotFoundException("No such question.");
        }
        return question.get();
    }
}
