package ru.spor.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.spor.topjava.graduation.model.Dish;
import ru.spor.topjava.graduation.repository.DishRepository;

import java.util.List;

import static ru.spor.topjava.graduation.util.ValidationUtil.checkNotFound;
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

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId) != 0, id);
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "Dish must be not null");
        repository.save(dish);
    }

    public List<Dish> getByRestaurant(int restaurantId) {
        return checkNotFound(repository.getByRestaurant_Id_OrderByDateDesc(restaurantId), "restaurantId=" + restaurantId);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public Dish getWithRestaurant(int id, int restaurantId) {
        return checkNotFoundWithId(repository.getWithRestaurant(id, restaurantId), id);
    }

//    public List<Dish> getAllByDate(int restaurantId, LocalDate date) {
//        Assert.notNull(date, "Date must be not null");
//        return repository.getAllByDate(restaurantId, date);
//    }
}
