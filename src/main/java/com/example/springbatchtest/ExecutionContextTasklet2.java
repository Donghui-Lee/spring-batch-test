package com.example.springbatchtest;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet2 implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		System.out.println("Step2 was executed");

		ExecutionContext jobExecutionContext = chunkContext.getStepContext()
														   .getStepExecution()
														   .getJobExecution()
														   .getExecutionContext();
		ExecutionContext stepExecutionContext = chunkContext.getStepContext()
															.getStepExecution()
															.getExecutionContext();
		// 이전에 저장한 jobName, stepName 꺼내옴
		System.out.println("jobName : " + jobExecutionContext.get("jobName"));
		System.out.println("stepName : " + stepExecutionContext.get("stepName"));


		if (stepExecutionContext.get("stepName") == null) {
			String stepName = chunkContext.getStepContext()
										  .getStepExecution()
										  .getStepName();

			stepExecutionContext.put("stepName", stepName);
		}
		return RepeatStatus.FINISHED;
	}
}
