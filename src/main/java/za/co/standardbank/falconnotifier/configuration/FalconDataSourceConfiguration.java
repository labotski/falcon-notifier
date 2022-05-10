package za.co.standardbank.falconnotifier.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import za.co.standardbank.falconnotifier.entites.falcon.FalconEntity;

@Configuration
@EnableJpaRepositories(basePackages = "za.co.standardbank.falconnotifier.repository.falcon", entityManagerFactoryRef = "falconEntityManagerFactory", transactionManagerRef = "falconTransactionManager")
class FalconDataSourceConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.falcon")
	public DataSourceProperties falconDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.falcon.configuration")
	public DataSource falconDataSource() {
		return falconDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean(name = "falconEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean falconEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(falconDataSource()).packages(FalconEntity.class).build();
	}

	@Bean
	@Primary
	public PlatformTransactionManager falconTransactionManager(
			final @Qualifier("falconEntityManagerFactory") LocalContainerEntityManagerFactoryBean falconEntityManagerFactory) {
		return new JpaTransactionManager(falconEntityManagerFactory.getObject());
	}

}
