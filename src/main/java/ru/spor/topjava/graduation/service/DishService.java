package ru.spor.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.spor.topjava.graduation.model.Dish;
import ru.spor.topjava.graduation.repository.DishRepository;

import java.util.List;

import static ru.spor.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish) {
        Assert.notNull(dish, "Dish must be not null");
        return repository.save(dish);
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "Dish must not be null");
        Integer id = dish.getId();
        checkNotFoundWithId(get(id), id);
        repository.save(dish);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Dish> getAll(Sort sort) {
        return repository.findAll(sort);
    }
}
