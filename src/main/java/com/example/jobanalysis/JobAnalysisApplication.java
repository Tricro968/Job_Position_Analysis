package com.example.jobanalysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.jobanalysis.mapper")
public class JobAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobAnalysisApplication.class, args);
    }
}