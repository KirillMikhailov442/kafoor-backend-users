package dev.kafoor.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_ip_user_agent_user_id", columnNames = {"ip", "user_agent", "user_id"})
        })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "refresh_token", nullable = false)
    private String refresh;

    @Column(name = "ip", length = 16, nullable = false)
    private String ip;

    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Token:" +
                "\n\tid:           " + id +
                "\n\tuser:         " + user.getId() + " " + "<" + user.getEmail() + ">" +
                "\n\trefreshToken: " + refresh +
                "\n\tip:           " + ip +
                "\n\tUserAgent:    " + userAgent +
                "\n\tcreatedAt:    " + createdAt +
                "\n\tupdatedAt:    " + updatedAt;
    }
}