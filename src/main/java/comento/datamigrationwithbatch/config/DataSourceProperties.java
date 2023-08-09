package comento.datamigrationwithbatch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
public class DataSourceProperties {
    @Primary
    @Bean(name = "SourceDatabase")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.source-database")
    public DataSource sourceDatabase() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "TargetDatabase")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.target-database")
    public DataSource targetDatabase() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
