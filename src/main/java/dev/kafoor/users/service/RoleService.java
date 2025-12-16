package dev.kafoor.users.service;

import dev.kafoor.users.entity.Role;
import dev.kafoor.users.entity.enums.UserRole;
import dev.kafoor.users.exception.Conflict;
import dev.kafoor.users.exception.NotFound;
import dev.kafoor.users.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления ролями пользователей.
 * Предоставляет методы для создания, поиска, обновления и удаления ролей.
 * Все методы выполняют операции с сущностью {@link Role}.
 *
 * <p>Сервис использует {@link RoleRepo} для доступа к данным и обрабатывает
 * возможные исключения, связанные с отсутствием или конфликтом данных.</p>
 *
 * @author Mikhailov
 * @version 1.0
 * @see Role
 * @see UserRole
 * @see RoleRepo
 * @see NotFound
 * @see Conflict
 */
@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepo roleRepo;

    /**
     * Получает список всех ролей из базы данных.
     *
     * @return список всех ролей
     * @see Role
     */
    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }

    /**
     * Находит роль по её идентификатору.
     *
     * @param id идентификатор роли
     * @return найденная роль
     * @throws NotFound если роль с указанным идентификатором не найдена
     */
    public Role findRoleById(long id) {
        return roleRepo.findById(id).orElseThrow(() -> new NotFound("Role not found by id " + id));
    }

    /**
     * Находит роль по её идентификатору, возвращая результат в виде {@link Optional}.
     *
     * @param id идентификатор роли
     * @return {@link Optional}, содержащий роль, если она найдена,
     *         или пустой {@link Optional}, если роль не найдена
     */
    public Optional<Role> findRoleByIdOptional(long id) {
        return roleRepo.findById(id);
    }

    /**
     * Находит роль по её имени.
     *
     * @param name имя роли из перечисления {@link UserRole}
     * @return найденная роль
     * @throws NotFound если роль с указанным именем не найдена
     */
    public Role findRoleByName(UserRole name) {
        return roleRepo.findByName(name).orElseThrow(() -> new NotFound("Role not found by name " + name));
    }

    /**
     * Находит роль по её имени, возвращая результат в виде {@link Optional}.
     *
     * @param name имя роли из перечисления {@link UserRole}
     * @return {@link Optional}, содержащий роль, если она найдена,
     *         или пустой {@link Optional}, если роль не найдена
     */
    public Optional<Role> findRoleByNameOptional(UserRole name) {
        return roleRepo.findByName(name);
    }

    /**
     * Создаёт новую роль с указанным именем.
     *
     * @param name имя новой роли из перечисления {@link UserRole}
     * @return созданная роль
     * @throws Conflict если роль с таким именем уже существует в базе данных
     */
    public Role createRole(UserRole name) {
        if (roleRepo.existsByName(name)) {
            throw new Conflict(String.format("Role %s already exists in the database", name));
        }
        Role newRole = Role.builder().name(name).build();
        roleRepo.save(newRole);
        return newRole;
    }

    /**
     * Обновляет имя существующей роли.
     *
     * @param newName новое имя роли из перечисления {@link UserRole}
     * @param id идентификатор обновляемой роли
     * @return обновлённая роль
     * @throws NotFound если роль с указанным идентификатором не найдена
     */
    public Role updateRole(UserRole newName, long id) {
        Role role = findRoleById(id);
        role.setName(newName);
        return roleRepo.save(role);
    }

    /**
     * Находит роль по имени или создаёт новую, если роль с таким именем не существует.
     *
     * @param name имя роли из перечисления {@link UserRole}
     * @return существующая или созданная роль
     */
    public Role findOrCreateRole(UserRole name) {
        Optional<Role> role = findRoleByNameOptional(name);
        return role.orElseGet(() -> createRole(name));
    }

    /**
     * Удаляет роль по её идентификатору.
     *
     * @param id идентификатор удаляемой роли
     * @throws NotFound если роль с указанным идентификатором не найдена
     */
    public void deleteRoleById(long id) {
        roleRepo.delete(findRoleById(id));
    }

    /**
     * Удаляет роль по её имени.
     *
     * @param name имя удаляемой роли из перечисления {@link UserRole}
     * @throws NotFound если роль с указанным именем не найдена
     */
    public void deleteRoleByName(UserRole name) {
        roleRepo.delete(findRoleByName(name));
    }
}