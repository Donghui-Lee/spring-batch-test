package com.example.springbatchtest;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRepositoryListener implements JobExecutionListener {

	@Autowired
	private JobRepository jobRepository;

	@Override
	public void beforeJob(JobExecution jobExecution) {

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// Job 수행된 이후 수행
		String jobName = jobExecution.getJobInstance()
									 .getJobName();

		// Program arugments
		// --name=batchJob requestDate=20220204
		// DB 에 저장된 파라미터 확인 후 하드 코딩
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("requestDate", "20220204")
				.toJobParameters();

		JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);

		if (lastJobExecution != null) {
			for (StepExecution execution : lastJobExecution.getStepExecutions()) {
				BatchStatus status = execution.getStatus();
				ExitStatus exitStatus = execution.getExitStatus();
				String stepName = execution.getStepName();

				System.out.println("status = " + status);
				System.out.println("exitStatus = " + exitStatus);
				System.out.println("stepName = " + stepName);

			}
		}
	}
}
