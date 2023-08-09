package comento.datamigrationwithbatch.domain.source;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OldChatRepository extends JpaRepository<OldChat, Long> {

}
