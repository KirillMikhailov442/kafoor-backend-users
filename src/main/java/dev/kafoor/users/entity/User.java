package dev.kafoor.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Сущность, представляющая пользователя системы.
 * <p>
 * Хранит основную информацию о пользователе, включая учетные данные,
 * статус подтверждения, роли, токены и временные метки жизненного цикла.
 * </p>
 *
 * <p>
 * Таблица имеет следующие индексы:
 * <ul>
 *   <li>idx_users_email - для поиска по email</li>
 *   <li>idx_users_nickname - для поиска по nickname</li>
 * </ul>
 * А также уникальное ограничение на комбинацию email и nickname.
 * </p>
 *
 * @author Kafoor Dev Team
 * @version 1.0
 * @since 2024
 */
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

    /**
     * Уникальный идентификатор пользователя.
     * <p>
     * Генерируется автоматически при сохранении новой сущности.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Имя пользователя.
     * <p>
     * Максимальная длина - 32 символа. Обязательное поле.
     * </p>
     */
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    /**
     * Электронная почта пользователя.
     * <p>
     * Используется для входа в систему и коммуникации.
     * Максимальная длина - 32 символа. Обязательное поле.
     * </p>
     */
    @Column(name = "email", length = 32, nullable = false)
    private String email;

    /**
     * Уникальное имя пользователя (псевдоним).
     * <p>
     * Отображается в интерфейсе системы. Максимальная длина - 32 символа.
     * Обязательное поле. Вместе с email образует уникальную пару.
     * </p>
     */
    @Column(name = "nickname", length = 32, nullable = false)
    private String nickname;

    /**
     * Хеш пароля пользователя.
     * <p>
     * Хранится в зашифрованном виде. Обязательное поле.
     * </p>
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Флаг, указывающий подтвержден ли аккаунт пользователя.
     * <p>
     * Обычно устанавливается в true после подтверждения email.
     * </p>
     */
    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    /**
     * Набор ролей, назначенных пользователю.
     * <p>
     * Определяет права доступа пользователя в системе.
     * Загружается сразу при загрузке пользователя (EAGER).
     * </p>
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    /**
     * Список токенов, связанных с пользователем.
     * <p>
     * Включает access, refresh и другие типы токенов.
     * Загружается сразу при загрузке пользователя (EAGER).
     * При удалении пользователя все связанные токены также удаляются.
     * </p>
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    /**
     * Дата и время создания записи пользователя.
     * <p>
     * Автоматически устанавливается при создании записи.
     * Не может быть изменена после создания.
     * </p>
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления записи пользователя.
     * <p>
     * Автоматически обновляется при изменении записи.
     * </p>
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Дата и время деактивации пользователя.
     * <p>
     * Если значение равно null, пользователь активен.
     * При деактивации устанавливается текущая дата и время.
     * </p>
     */
    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;

    /**
     * Возвращает строковое представление пользователя.
     * <p>
     * Включает все основные поля, кроме чувствительных данных
     * (таких как passwordHash).
     * </p>
     *
     * @return форматированное строковое представление объекта User
     */
    @Override
    public String toString() {
        return "User:" +
                "\n\tid:            " + id +
                "\n\tname:          " + name +
                "\n\temail:         " + email +
                "\n\tnickname:      " + nickname +
                "\n\tisConfirmed:   " + isConfirmed +
                "\n\tcreatedAt:     " + createdAt +
                "\n\tupdatedAt:     " + updatedAt +
                "\n\tdeactivatedAt: " + deactivatedAt;
    }
}