package dev.kafoor.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая токен обновления (refresh token) для аутентификации пользователя.
 * <p>
 * Каждый токен связан с конкретным пользователем ({@link UserEntity}) и содержит информацию
 * о сессии, включая IP-адрес и User-Agent клиента. Токены используются для поддержания
 * аутентификации пользователя без необходимости повторного ввода учетных данных.
 * </p>
 * <p>
 * В таблице применяется ограничение уникальности {@code uk_ip_user_agent}, гарантирующее,
 * что для каждой комбинации IP-адреса и User-Agent существует только одна активная сессия.
 * </p>
 * <p>
 * Пример использования:
 * <pre>
 * {@code
 * Token token = Token.builder()
 *     .user(user)
 *     .refresh("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
 *     .ip("192.168.1.1")
 *     .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
 *     .build();
 * }
 * </pre>
 * </p>
 *
 * @see UserEntity
 * @see Entity
 * @see Table
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_ip_user_agent", columnNames = {"ip", "user_agent"})
        })
public class TokenEntity {

    /**
     * Уникальный идентификатор токена.
     * <p>
     * Генерируется автоматически при сохранении нового токена в базу данных
     * с использованием стратегии IDENTITY.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Пользователь, которому принадлежит токен.
     * <p>
     * Устанавливается связь "многие-к-одному" с сущностью {@link UserEntity}.
     * Токен всегда должен быть связан с пользователем (nullable = false).
     * </p>
     *
     * @see UserEntity
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Токен обновления (refresh token).
     * <p>
     * JWT токен, используемый для получения новой пары access/refresh токенов.
     * Хранится в зашифрованном или хэшированном виде. Не может быть null.
     * </p>
     */
    @Column(name = "refresh_token", nullable = false)
    private String refresh;

    /**
     * IP-адрес клиента, с которого был получен токен.
     * <p>
     * Используется для отслеживания сессий и безопасности. Максимальная длина - 16 символов
     * (поддерживает IPv4 и IPv6 адреса). Не может быть null.
     * </p>
     */
    @Column(name = "ip", length = 16, nullable = false)
    private String ip;

    /**
     * User-Agent заголовок клиентского приложения или браузера.
     * <p>
     * Содержит информацию о типе клиента, операционной системе и версии.
     * Используется в комбинации с IP-адресом для уникальной идентификации сессии.
     * Не может быть null.
     * </p>
     */
    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    /**
     * Дата и время создания токена.
     * <p>
     * Автоматически устанавливается при создании новой записи с использованием
     * {@link CreationTimestamp}.
     * </p>
     *
     * @see CreationTimestamp
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления токена.
     * <p>
     * Автоматически обновляется при изменении записи с использованием
     * {@link UpdateTimestamp}. Обновляется при использовании токена для получения
     * новых access токенов.
     * </p>
     *
     * @see UpdateTimestamp
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Возвращает строковое представление объекта Token.
     * <p>
     * Формат вывода включает все поля сущности с отступами для лучшей читаемости.
     * Для связанного пользователя отображается только его идентификатор и email
     * для избежания циклических зависимостей в выводе.
     * </p>
     *
     * @return строковое представление токена в формате:
     * <pre>
     * Token:
     *     id:           [id]
     *     user:         [userId] <[userEmail]>
     *     refreshToken: [refresh]
     *     ip:           [ip]
     *     UserAgent:    [userAgent]
     *     createdAt:    [createdAt]
     *     updatedAt:    [updatedAt]
     * </pre>
     */
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