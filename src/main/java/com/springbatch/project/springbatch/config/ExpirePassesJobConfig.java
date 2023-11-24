package com.springbatch.project.springbatch.config;

import com.springbatch.project.springbatch.repository.pass.PassEntity;
import com.springbatch.project.springbatch.repository.pass.PassStatus;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class ExpirePassesJobConfig {

    private final int CHUNK_SIZE = 5;

    private final EntityManagerFactory entityManagerFactory;

    private final PlatformTransactionManager transactionManager;


    @Bean
    public Job expirePassesJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("expirePassesJob", jobRepository)
                .start(expirePassesStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step expirePassesStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("expirePassesStep", jobRepository)
                .<PassEntity, PassEntity> chunk(CHUNK_SIZE, transactionManager)  // <I, O> 인풋, 아웃풋
                .reader(expirePassesItemReader())
                .processor(expirePassesItemProcessor())
                .writer(expirePassesItemWriter())
                .build();
    }

    // JpaCursorItemReader: 페이징 기법보다 높은 성능으로, 데이터 변경에 무관한 무결성 조회가 가능하다.
    @Bean
    @StepScope
    public JpaCursorItemReader<PassEntity> expirePassesItemReader() {
        return new JpaCursorItemReaderBuilder<PassEntity>()
                .name("expirePassesItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from PassEntity p where p.status = :status and p.endedAt <= :endedAt")    // 상태가 진행 중 일때와 내가 호출한 시점보다 데이터의 시점이 더 작았을때(안끝난 상태일때를 말하는 듯 싶다)
                .parameterValues(Map.of("status", PassStatus.PROGRESSED, "endedAt", LocalDateTime.now()))
                .build();
    }

    @Bean
    public ItemProcessor<PassEntity, PassEntity> expirePassesItemProcessor() {
        return passEntity -> {
            passEntity.setStatus(PassStatus.EXPIRED);
            passEntity.setExpiredAt(LocalDateTime.now());
            return passEntity;
        };
    }

    //JpaItemWriter: JPA의 영속성 관리를 위해 EntityManager를 필수로 설정해줘야 합니다.
    @Bean
    public JpaItemWriter<PassEntity> expirePassesItemWriter() {
        return new JpaItemWriterBuilder<PassEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
