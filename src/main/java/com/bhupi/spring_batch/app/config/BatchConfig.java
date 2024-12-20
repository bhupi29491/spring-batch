package com.bhupi.spring_batch.app.config;

import com.bhupi.spring_batch.app.decider.MyJobExecutionDecider;
import com.bhupi.spring_batch.app.listener.MyJobExecutionListener;
import com.bhupi.spring_batch.app.listener.MyStepExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public MyJobExecutionListener myJobExecutionListener() {
        return new MyJobExecutionListener();
    }

    @Bean
    public JobExecutionDecider jobExecutionDecider() {
        return new MyJobExecutionDecider();
    }

    @Bean
    public MyStepExecutionListener myStepExecutionListener() {
        return new MyStepExecutionListener();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step1 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          ExecutionContext jobExecutionContext = chunkContext.getStepContext()
                                                                                                             .getStepExecution()
                                                                                                             .getJobExecution()
                                                                                                             .getExecutionContext();
                                                          System.out.println("Job Execution Context: " + jobExecutionContext);
//                                                          jobExecutionContext.put("sk1", "ABC");
                                                          ExecutionContext stepExecutionContext = chunkContext.getStepContext()
                                                                                                              .getStepExecution()
                                                                                                              .getExecutionContext();
                                                          stepExecutionContext.put("sk1", "ABC");
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .listener(promoListener())
                                                      .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step2 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          ExecutionContext jobExecutionContext = chunkContext.getStepContext()
                                                                                                             .getStepExecution()
                                                                                                             .getJobExecution()
                                                                                                             .getExecutionContext();
                                                          System.out.println("Job Execution Context: " + jobExecutionContext);
//                                                          jobExecutionContext.put("sk2", "KLM");
                                                          ExecutionContext stepExecutionContext = chunkContext.getStepContext()
                                                                                                              .getStepExecution()
                                                                                                              .getExecutionContext();
                                                          stepExecutionContext.put("sk2", "TUV");
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
//                                                      .listener(myStepExecutionListener())
                                                      .listener(promoListener())
                                                      .build();
    }

    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step3", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step3 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          ExecutionContext jobExecutionContext = chunkContext.getStepContext()
                                                                                                             .getStepExecution()
                                                                                                             .getJobExecution()
                                                                                                             .getExecutionContext();
                                                          System.out.println("Job Execution Context: " + jobExecutionContext);
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .listener(myStepExecutionListener())
                                                      .build();
    }

    @Bean
    public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step4", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step4 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }

    @Bean
    public Step step5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step5", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          boolean isFailure = false;
                                                          if (isFailure) {
                                                              throw new Exception("Test Exception");
                                                          }
                                                          System.out.println("Step5 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }

    @Bean
    public Step step6(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step6", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step6 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }

    @Bean
    public Step step7(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step7", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step7 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }

    @Bean
    public Step step8(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step8", jobRepository).tasklet((contribution, chunkContext) -> {
                                                          System.out.println("Step8 is executed on thread :: " + Thread.currentThread()
                                                                                                                       .getName());
                                                          return RepeatStatus.FINISHED;
                                                      }, transactionManager)
                                                      .build();
    }

    @Bean
    public Step job3Step(JobRepository jobRepository, Job job3) {
        return new StepBuilder("job3Step", jobRepository).job(job3)
                                                         .build();
    }

    @Bean
    public Flow flow1(Step step3, Step step4) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow1");
        flowBuilder.start(step3)
                   .next(step4)
                   .end();
        return flowBuilder.build();
    }

    @Bean
    public Flow flow2(Step step5, Step step6) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow2");
        flowBuilder.start(step5)
                   .next(step6)
                   .end();
        return flowBuilder.build();
    }

    @Bean
    public Flow flow3(Step step7, Step step8) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow3");
        flowBuilder.start(step7)
                   .next(step8)
                   .end();
        return flowBuilder.build();
    }

    @Bean
    public Flow splitFlow(Flow flow1, Flow flow2, Flow flow3) {
        return new FlowBuilder<Flow>("splitFlow").split(new SimpleAsyncTaskExecutor())
                                                 .add(flow1, flow2, flow3)
                                                 .build();
    }


//    @Bean
//    public Job job1(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
//        return new JobBuilder("job1", jobRepository).start(step1(jobRepository, transactionManager))
//                                                    .on("COMPLETED")
//                                                    .to(jobExecutionDecider())
//                                                    .on("TEST_STATUS")
//                                                    .to(step2(jobRepository, transactionManager))
//                                                    .from(jobExecutionDecider())
//                                                    .on("*")
//                                                    .to(step3(jobRepository, transactionManager))
//                                                    .end()
//                                                    .build();
//    }


    @Bean
    public StepExecutionListener promoListener() {
        ExecutionContextPromotionListener promotionListener = new ExecutionContextPromotionListener();
        promotionListener.setKeys(new String[]{
                "sk1",
                "sk2"});
        return promotionListener;
    }

    @Bean
    public Job job1(JobRepository jobRepository, Step step1, Step step2, Step step3, Step step4, Step step5) throws Exception {
        return new JobBuilder("job1", jobRepository).listener(myJobExecutionListener())
                                                    .start(step1)
                                                    .next(step2)
                                                    .next(jobExecutionDecider())
                                                    .on("STEP_3")
                                                    .to(step3)
                                                    .from(jobExecutionDecider())
                                                    .on("STEP_4")
                                                    .to(step4)
                                                    .from(jobExecutionDecider())
                                                    .on("STEP_5")
                                                    .to(step5)
                                                    .end()
                                                    .build();
    }

//    @Bean
//    public Job secondJob(JobRepository jobRepository, Step step5, Step step6, Flow flow1) throws Exception {
//        return new JobBuilder("job2", jobRepository).start(flow1)
//                                                    .next(step5)
//                                                    .next(step6)
//                                                    .end()
//                                                    .build();
//    }


    @Bean
    public Job job2(JobRepository jobRepository, Step job3Step, Flow splitFlow) throws Exception {
        return new JobBuilder("job2", jobRepository).listener(myJobExecutionListener())
                                                    .start(splitFlow)
                                                    .end()
                                                    .build();
    }

    @Bean
    public Job job3(JobRepository jobRepository, Step step5, Step step6) throws Exception {
        return new JobBuilder("job3", jobRepository).start(step5)
                                                    .next(step6)
                                                    .build();
    }

}
