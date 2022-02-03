package com.example.springbatchtest;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet3 implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		System.out.println("Step3 was executed");

		// 예외 발생 시킴
		// 예외 발생 시, Step4 는 예외로 인해 실행되지 않음 - Job 의 전체적인 상태는 실패
		// 재시작 시, 실패한 단계에서 수행되는지 확인

		ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

		Object name = jobExecutionContext.get("name");

		if (name == null) {
			jobExecutionContext.put("name", "user1");
			// 예외 발생 시킴
			throw new RuntimeException("Step3 was failed");
		}
		return RepeatStatus.FINISHED;
	}
}
