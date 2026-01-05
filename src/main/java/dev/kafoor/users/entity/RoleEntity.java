package dev.kafoor.users.entity;

import dev.kafoor.users.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая роль пользователя в системе.
 * <p>
 * Эта сущность хранит информацию о различных ролях, которые могут быть назначены пользователям.
 * Каждая роль имеет уникальное имя, представленное перечислением {@link UserRole}.
 * Для оптимизации поиска по имени роли создан индекс {@code idx_role_name}.
 * </p>
 * <p>
 * Пример использования:
 * <pre>
 * {@code
 * Role adminRole = Role.builder()
 *     .name(UserRole.ADMIN)
 *     .build();
 * }
 * </pre>
 * </p>
 *
 * @see UserRole
 * @see Entity
 * @see Table
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles", indexes = {
        @Index(name = "idx_role_name", columnList = "name")
})
public class RoleEntity {

    /**
     * Уникальный идентификатор роли.
     * <p>
     * Генерируется автоматически при сохранении новой роли в базу данных
     * с использованием стратегии IDENTITY.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Название роли.
     * <p>
     * Хранится как строка, соответствующая одному из значений перечисления {@link UserRole}.
     * Поле должно быть уникальным и не может быть null. Максимальная длина - 16 символов.
     * </p>
     *
     * @see UserRole
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 16, unique = true, nullable = false)
    private UserRole name;

    /**
     * Дата и время создания записи о роли.
     * <p>
     * Автоматически устанавливается при создании новой записи с использованием
     * {@link CreationTimestamp}. Не может быть null.
     * </p>
     *
     * @see CreationTimestamp
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления записи о роли.
     * <p>
     * Автоматически обновляется при изменении записи с использованием
     * {@link UpdateTimestamp}. Не может быть null.
     * </p>
     *
     * @see UpdateTimestamp
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Возвращает строковое представление объекта Role.
     * <p>
     * Формат вывода включает все поля сущности с отступами для лучшей читаемости.
     * </p>
     *
     * @return строковое представление роли в формате:
     * <pre>
     * Role:
     *     id:          [id]
     *     name:        [name]
     *     createdAt:   [createdAt]
     *     updatedAt:   [updatedAt]
     * </pre>
     */
    @Override
    public String toString() {
        return "Role:" +
                "\n\tid:          " + id +
                "\n\tname:        " + name +
                "\n\tcreatedAt:   " + createdAt +
                "\n\tupdatedAt:   " + updatedAt;
    }
}