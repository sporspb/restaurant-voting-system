package ru.spor.topjava.graduation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import ru.spor.topjava.graduation.model.Role;
import ru.spor.topjava.graduation.model.User;
import ru.spor.topjava.graduation.repository.JpaUtil;
import ru.spor.topjava.graduation.util.exception.NotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static ru.spor.topjava.graduation.UserTestDataUtil.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(cacheManager.getCache("users")).clear();
        jpaUtil.clear2ndLevelCache();
    }

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Collections.emptySet(), false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(newUser, created);
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), ADMIN, USER, newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(USER_ID);
        assertMatch(service.getAll(Sort.unsorted()), ADMIN);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll(Sort.by(Sort.Direction.ASC, "name"));
        assertMatch(all, ADMIN, USER);
    }

    @Test
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }
}