package ru.spor.topjava.graduation.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.spor.topjava.graduation.model.User;
import ru.spor.topjava.graduation.service.UserService;
import ru.spor.topjava.graduation.to.UserTo;
import ru.spor.topjava.graduation.util.UserUtil;

import java.util.List;

import static ru.spor.topjava.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.spor.topjava.graduation.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    protected Logger log = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    protected UserService service;

    public User create(User user) {
        log.info("Created user {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void update(User user, int id) {
        log.info("Update {} with id {}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("Update {} with id {}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User create(UserTo userTo) {
        log.info("Created user {}", userTo);
        checkNew(userTo);
        User user = UserUtil.createNewFromTo(userTo);
        return service.create(user);
    }

    public User get(int userId) {
        log.info("Get user {}", userId);
        return service.get(userId);
    }

    public List<User> getAll() {
        log.info("Get all users");
        return service.getAll(Sort.unsorted());
    }

    public void delete(int userId) {
        log.info("Delete user {}", userId);
        service.delete(userId);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }
}

