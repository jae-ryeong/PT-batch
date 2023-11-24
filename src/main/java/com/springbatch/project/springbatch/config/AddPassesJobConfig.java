package com.springbatch.project.springbatch.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class AddPassesJobConfig {

    private final EntityManagerFactory entityManagerFactory;

    private final PlatformTransactionManager transactionManager;


}
