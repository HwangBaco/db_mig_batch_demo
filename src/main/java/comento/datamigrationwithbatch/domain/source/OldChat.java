package comento.datamigrationwithbatch.domain.source;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "chat", schema = "coredb")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OldChat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "room_id")
    private Long roomId;

    @OneToMany(mappedBy = "chat")
    private List<OldChatLike> chatLikes = new ArrayList<>();

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
