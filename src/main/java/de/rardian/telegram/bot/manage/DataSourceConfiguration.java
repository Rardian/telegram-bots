package de.rardian.telegram.bot.manage;

//@Configuration
//@EnableTransactionManagement
// Aktivierung des TX-Managements führt zu Nichtspeicherung der Daten.
public class DataSourceConfiguration /*implements TransactionManagementConfigurer*/ {

	//	@Autowired
	//	DataSource dataSource;
	//
	//	@Bean
	//	public PlatformTransactionManager transactionManager() {
	//		return new DataSourceTransactionManager(dataSource);
	//	}
	//
	//	@Override
	//	public PlatformTransactionManager annotationDrivenTransactionManager() {
	//		return transactionManager();
	//	}
}
