package kafoor.users.user_service.models;

import jakarta.persistence.*;
import kafoor.users.user_service.models.enums.UserRoles;
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
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 16)
    private UserRoles name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private long createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private long updatedAt;
}
