package com.example.springbatchtest;

import javax.sql.DataSource;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBatchConfigurer extends BasicBatchConfigurer {

	private final DataSource dataSource;

	protected CustomBatchConfigurer(BatchProperties properties, DataSource dataSource,
									TransactionManagerCustomizers transactionManagerCustomizers) {
		super(properties, dataSource, transactionManagerCustomizers);
		this.dataSource = dataSource;
	}

	@Override
	protected JobRepository createJobRepository() throws Exception {
		// 커스텀 JobRepository 생성

		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		// DataSource 설정
		factory.setDataSource(dataSource);
		// TransactionManager 설정
		factory.setTransactionManager(getTransactionManager());
		// 격리 수준 설정
		factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		// Table Prefix 설정
		factory.setTablePrefix("SYSTEM_");

		return factory.getObject();
	}
}
