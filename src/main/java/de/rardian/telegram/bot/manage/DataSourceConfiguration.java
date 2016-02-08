package de.rardian.telegram.bot.manage;

import javax.sql.DataSource;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration implements TransactionManagementConfigurer {

	@Autowired
	DataSource dataSource;

	// @Bean
	// public DataSource dataSource() {
	// // configure and return the necessary JDBC DataSource
	// }

	@Bean
	public PlatformTransactionManager transactionManager() {
		Validate.notNull(dataSource);
		return new DataSourceTransactionManager(dataSource);
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}
}
