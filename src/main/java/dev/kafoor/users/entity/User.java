package dev.kafoor.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
        indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_nickname", columnList = "nickname")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_email_nickname", columnNames = {"email", "nickname"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "email", length = 32, nullable = false)
    private String email;

    @Column(name = "nickname", length = 32, nullable = false)
    private String nickname;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;
}