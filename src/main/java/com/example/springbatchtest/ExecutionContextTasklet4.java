package com.example.springbatchtest;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet4 implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		System.out.println("Step4 was executed");

		// Step3 에서 예외 발생 후 재시작 시(오류 수정 후)
		// DB 에 있는 ExecutionContext 값을 가져와 출력
		ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

		System.out.println("name : " + jobExecutionContext.get("name"));
		System.out.println("jobName : " + jobExecutionContext.get("jobName"));
		return RepeatStatus.FINISHED;
	}
}
