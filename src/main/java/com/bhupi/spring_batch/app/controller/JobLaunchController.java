package com.bhupi.spring_batch.app.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLaunchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    public JobLaunchController(JobLauncher jobLauncher, @Qualifier("job1") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/launchJob/{id}")
    public void handle(@PathVariable("id") String id) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addString("param", id)
                                                                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
