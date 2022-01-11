package com.example.springbatchtest;

import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JobParameterConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                                .start(step1())
                                .next(step2())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
								 .tasklet((stepContribution, chunkContext) -> {

									 // JobParameters
									 JobParameters jobParameters = stepContribution.getStepExecution()
																				   .getJobExecution()
																				   .getJobParameters();
									 String name = jobParameters.getString("name");
									 long seq = jobParameters.getLong("seq");
									 Date date = jobParameters.getDate("date");

									 System.out.println("===========================");
									 System.out.println("name:" + name);
									 System.out.println("seq: " + seq);
									 System.out.println("date: " + date);
									 System.out.println("===========================");


									 System.out.println("=======");

									 // Map 동일한 값을 받을 수 있음
									 Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();

									 String name2 = (String)jobParameters1.get("name");
									 long seq2 = (long)jobParameters1.get("seq");

									 System.out.println("step1 was executed");
									 return RepeatStatus.FINISHED;
								 })
								 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((stepContribution, chunkContext) -> {
                                     System.out.println("step2 was executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
}
