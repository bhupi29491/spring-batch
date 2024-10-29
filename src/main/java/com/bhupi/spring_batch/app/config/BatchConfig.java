package com.bhupi.spring_batch.app.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private final PrintHelloTask printHelloTask;

    public BatchConfig(PrintHelloTask printHelloTask) {
        this.printHelloTask = printHelloTask;
    }

    @Bean
    public Step step1(JobRepository jobRepository, Tasklet task, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository).tasklet(task, transactionManager)
                                                      .build();
    }

    @Bean
    public Job firstJob(JobRepository jobRepository, Step step, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job1", jobRepository).start(step1(jobRepository, printHelloTask, transactionManager))
                                                    .build();
    }

}
