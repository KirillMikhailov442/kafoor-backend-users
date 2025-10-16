package kafoor.users.user_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ip", length = 16, nullable = false)
    private String ip;

    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @CreationTimestamp
    @Column(name = "created_at")
    private long createdAt;

    @UpdateTimestamp
    @Column(name = "last_login_at")
    private long lastLogin;
}
