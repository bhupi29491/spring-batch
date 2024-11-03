package com.bhupi.spring_batch.app.config;

import com.bhupi.spring_batch.app.decider.MyJobExecutionDecider;
import com.bhupi.spring_batch.app.listener.MyStepExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public JobExecutionDecider jobExecutionDecider() {
        return new MyJobExecutionDecider();
    }

    @Bean
    public StepExecutionListener myStepExecutionListener() {
        return new MyStepExecutionListener();
    }

    @Bean
    @Primary
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step1 is executed..!!");
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          boolean isFailure = false;
                                                          if (isFailure) {
                                                              throw new Exception("Test Exception");
                                                          }
                                                          System.out.println("Step2 is executed..!!");
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
//                                                      .listener(myStepExecutionListener())
                                                      .build();
    }

    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step3", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step3 is executed..!!");
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }

    @Bean
    public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step4", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step4 is executed..!!");
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }


    @Bean
    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("job1", jobRepository).start(step1(jobRepository, transactionManager))
                                                    .on("COMPLETED")
                                                    .to(jobExecutionDecider())
                                                    .on("TEST_STATUS")
                                                    .to(step2(jobRepository, transactionManager))
                                                    .from(jobExecutionDecider())
                                                    .on("*")
                                                    .to(step3(jobRepository, transactionManager))
                                                    .end()
                                                    .build();
    }

}
