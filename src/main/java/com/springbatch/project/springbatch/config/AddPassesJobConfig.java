package com.springbatch.project.springbatch.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class AddPassesJobConfig {

    private final PlatformTransactionManager transactionManager;
    private final AddPassesTasklet addPassesTasklet;
    private final JobRepository jobRepository;

    @Bean
    public Job addPassesJob() {
        return new JobBuilder("addPassesJob", jobRepository)
                .start(addPassesStep())
                .build();
    }

    @Bean
    public Step addPassesStep() {
        return new StepBuilder("addPassesStep", jobRepository)
                .tasklet(addPassesTasklet, transactionManager)
                .build();
    }
}
