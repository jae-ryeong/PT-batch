package com.springbatch.project.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchApplication {

	/*
		https://spring.io/blog/2022/09/22/spring-batch-5-0-0-m6-and-4-3-7-are-out
		BatchConfigurer제거 DefaultBatchConfigurer되었습니다.
		더 이상 이 인터페이스와 기본 구현을 사용하여 동작을 사용자 정의해서는 안 됩니다.@EnableBatchProcessing
		라고 쓰여져 있다.
		https://velog.io/@cho876/Spring-Batch-job-%EC%83%9D%EC%84%B1 바뀐 방식 여기서 자세히
	 */

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
