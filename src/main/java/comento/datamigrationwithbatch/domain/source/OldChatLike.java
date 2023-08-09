package comento.datamigrationwithbatch.domain.source;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chat_like", schema = "coredb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OldChatLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;    // 아래 chat이 있는데 여기 chatId가 왜 있을까...?
    // 로직을 확인해 봐야겠다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private OldChat chat;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
