package com.springbatch.project.springbatch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration // 자동 설정
//@EnableBatchProcessing // 배치 환경 및 설정 초기화 애노테이션, springBoot 3.0 이상부터 사용X
public class TestBatchConfig {

}
