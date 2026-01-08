package dev.kafoor.users.entity;

import dev.kafoor.users.entity.enums.UserRole;
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
@Table(name = "roles", indexes = {
        @Index(name = "idx_role_name", columnList = "name")
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 16, unique = true, nullable = false)
    private UserRole name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Role:" +
                "\n\tid:          " + id +
                "\n\tname:        " + name +
                "\n\tcreatedAt:   " + createdAt +
                "\n\tupdatedAt:   " + updatedAt;
    }
}