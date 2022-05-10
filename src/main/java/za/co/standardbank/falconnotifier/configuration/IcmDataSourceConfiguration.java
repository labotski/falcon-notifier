package za.co.standardbank.falconnotifier.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import za.co.standardbank.falconnotifier.entites.icm.IcmEntity;

@Configuration
@EnableJpaRepositories(basePackages = "za.co.standardbank.falconnotifier.repository.icm", entityManagerFactoryRef = "icmEntityManagerFactory", transactionManagerRef = "icmTransactionManager")
public class IcmDataSourceConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource.icm")
	public DataSourceProperties icmDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.icm.configuration")
	public DataSource icmDataSource() {
		return icmDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean(name = "icmEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean icmEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(icmDataSource()).packages(IcmEntity.class).build();
	}

	@Bean
	public PlatformTransactionManager icmTransactionManager(
			final @Qualifier("icmEntityManagerFactory") LocalContainerEntityManagerFactoryBean icmEntityManagerFactory) {
		return new JpaTransactionManager(icmEntityManagerFactory.getObject());
	}

}
