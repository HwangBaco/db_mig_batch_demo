package comento.datamigrationwithbatch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "TargetDBManagerFactory", // 아래에서 등록한 Eean Id
        basePackages = {"comento.datamigrationwithbatch.domain.target"} // 소스DB 리포지토리 패키지
)
public class TargetDBManagerFactoryConfiguration {
    @Autowired
    @Qualifier("TargetDatabase")
    private DataSource targetDatabase;

    @Primary
    @Bean(name = "TargetDBManagerFactory")
    public LocalContainerEntityManagerFactoryBean targetDBManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(targetDatabase)
                .packages("comento.datamigrationwithbatch.domain.target") // 소스DB 엔티티 패키지
                .persistenceUnit("Target")
                .build();
    }
}
