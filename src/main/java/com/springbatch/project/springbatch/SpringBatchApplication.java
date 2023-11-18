package com.springbatch.project.springbatch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchApplication {

	/*
		https://spring.io/blog/2022/09/22/spring-batch-5-0-0-m6-and-4-3-7-are-out
		BatchConfigurer제거 DefaultBatchConfigurer되었습니다.
		더 이상 이 인터페이스와 기본 구현을 사용하여 동작을 사용자 정의해서는 안 됩니다.@EnableBatchProcessing
		라고 쓰여져 있다.
	 */

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
