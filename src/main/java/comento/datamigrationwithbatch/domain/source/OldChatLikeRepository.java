package comento.datamigrationwithbatch.domain.source;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OldChatLikeRepository extends JpaRepository<OldChatLike, Long> {
}
