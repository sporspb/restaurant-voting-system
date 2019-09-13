package ru.spor.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.spor.topjava.graduation.model.User;
import ru.spor.topjava.graduation.repository.UserRepository;

import java.util.List;

import static ru.spor.topjava.graduation.util.ValidationUtil.checkNotFound;
import static ru.spor.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {
    private final UserRepository repository;
    //private static final Sort SORT_BY_ID = new Sort(Sort.Direction.DESC, "id");

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "User must be not null");
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "Email must be not null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll(Sort sort) {
        return repository.findAll(sort);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "User must be not null");
        checkNotFoundWithId(repository.save(user), user.getId());
    }
}
