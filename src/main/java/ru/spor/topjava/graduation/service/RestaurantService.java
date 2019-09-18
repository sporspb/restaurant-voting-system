package ru.spor.topjava.graduation.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.spor.topjava.graduation.model.Restaurant;
import ru.spor.topjava.graduation.repository.RestaurantRepository;
import ru.spor.topjava.graduation.util.exception.NotFoundException;

import java.util.List;

import static ru.spor.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant add(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    public Restaurant get(Integer id) {
        Assert.notNull(id, "id must be not null");
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Restaurant> getAll(Sort sort) {
        return repository.findAll(sort);
    }

    public List<Restaurant> getAllWithMenuDishes() {
        return repository.findAllWithMenus();
    }

    public List<Restaurant> getAllWithVotes() {
        return repository.findAllWithVotes();
    }

    public List<Restaurant> getAllWithMenuDishesAndVotes() {
        return repository.findAllWithMenus();
    }
}
