package com.askog.voting;

import com.askog.voting.db.QuestionDao;
import com.askog.voting.db.VoteDao;
import com.askog.voting.entity.Question;
import com.askog.voting.entity.Vote;
import com.askog.voting.resources.QuestionsResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class VotingApplication extends Application<VotingConfiguration> {

    public static void main(String[] args) throws Exception {
        new VotingApplication().run(args);
    }

    private final HibernateBundle<VotingConfiguration> hibernateBundle =
            new HibernateBundle<VotingConfiguration>(Question.class, Vote.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(VotingConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "voting";
    }

    @Override
    public void initialize(Bootstrap<VotingConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new MigrationsBundle<VotingConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(VotingConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(VotingConfiguration votingConfiguration, Environment environment) throws Exception {
        final QuestionDao questionDao = new QuestionDao(hibernateBundle.getSessionFactory());
        final VoteDao voteDao = new VoteDao(hibernateBundle.getSessionFactory());

        environment.jersey().register(new QuestionsResource(questionDao, voteDao));
    }
}
