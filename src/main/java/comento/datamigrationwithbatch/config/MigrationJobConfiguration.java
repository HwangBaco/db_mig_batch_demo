package comento.datamigrationwithbatch.config;

import comento.datamigrationwithbatch.domain.source.OldChat;
import comento.datamigrationwithbatch.domain.target.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class MigrationJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("SourceDBManagerFactory")
    private EntityManagerFactory sourceDBManagerFactory;

    @Autowired
    @Qualifier("TargetDBManagerFactory")
    private EntityManagerFactory targetDBManagerfactory;

    @Value("${chunkSize:1000}") // 기본값 : 1000
    private int chunkSize; // 한번에 조회할 데이터 수

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean
    public Job migrationJob() {
        return jobBuilderFactory.get("migrationJob")
                .start(migrationToTarget())
                .build();
    }

    @Bean
    public Step migrationToTarget() {
        return stepBuilderFactory.get("migrationToTarget")
                .transactionManager(new JpaTransactionManager(targetDBManagerfactory))
                .<OldChat, Chat>chunk(chunkSize)
                .reader(reader())
//                .processor(targetProcessor()) // OldChat과 Chat이 동일한 데이터이므로 프로세싱 진행 x
                .writer(targetWriter())
                .build();
    }

    @Bean
    public ItemReader<OldChat> reader() {
        // 데이터 조회
        return new JpaPagingItemReaderBuilder<OldChat>()
                .name("reader")
                .entityManagerFactory(sourceDBManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT c FROM OldChat.c")
                .build();
    }

    @Bean
    public JpaItemWriter<Chat> targetWriter() {
        JpaItemWriter<Chat> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(targetDBManagerfactory);
        writer.setUsePersist(true);
        return writer;
    }
}
