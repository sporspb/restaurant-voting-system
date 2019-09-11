package ru.spor.topjava.graduation.service;

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
    private final UserRepository userRepository;
    //private static final Sort SORT_BY_ID = new Sort(Sort.Direction.DESC, "id");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        Assert.notNull(user, "User must be not null");
        return userRepository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "Email must be not null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    public void update(User user) {
        Assert.notNull(user, "User must be not null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }
}
