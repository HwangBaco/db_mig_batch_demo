package comento.datamigrationwithbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing // batch 기능 활성화
@SpringBootApplication
public class DataMigrationWithBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataMigrationWithBatchApplication.class, args);
    }

}
