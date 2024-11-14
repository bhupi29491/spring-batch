package com.bhupi.spring_batch.app.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class MyJobExecutionDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        String sk1 = jobExecution.getExecutionContext()
                                 .getString("sk1");
        String sk2 = jobExecution.getExecutionContext()
                                 .getString("sk2");
        String exitStatus = "";

        if ("ABC".equals(sk1) && "KLM".equals(sk2)) {
            exitStatus = "STEP_3";
        } else if ("ABC".equals(sk1) && "TUV".equals(sk2)) {
            exitStatus = "STEP_4";
        } else {
            exitStatus = "STEP_5";
        }

        return new FlowExecutionStatus(exitStatus);
    }
}
