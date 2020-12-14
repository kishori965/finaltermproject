package maven.demo.webapp.demo.config;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@Configuration
@ComponentScan(basePackages = "maven.demo.webapp.demo")
@EnableTransactionManagement
public class HibernteConfig {
	@Bean
	public DataSource getDataSource() throws SQLException {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriver(new org.h2.Driver());
		dataSource.setUrl("jdbc:h2:~/demo2");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");
		dataSource.setInitialSize(20);
		dataSource.setMaxTotal(100);

		return dataSource;

	}

	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		builder.addProperties(getProperties());
	builder.scanPackages("maven.demo.webapp.demo.dto");
		return builder.buildSessionFactory();
	}

	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}

	@Bean
	public HibernateTransactionManager getHibernateTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

}

