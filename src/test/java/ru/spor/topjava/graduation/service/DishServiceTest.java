package ru.spor.topjava.graduation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.spor.topjava.graduation.model.Dish;
import ru.spor.topjava.graduation.repository.JpaUtil;
import ru.spor.topjava.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.spor.topjava.graduation.DishTestDataUtil.assertMatch;
import static ru.spor.topjava.graduation.DishTestDataUtil.*;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(cacheManager.getCache("meals")).clear();
        jpaUtil.clear2ndLevelCache();
    }

    @Test
    void create() {
        Dish newDish = new Dish(null, FIRST_RESTAURANT, LocalDate.of(2019, 8, 19), "newDish test", 155);
        Dish created = service.create(newDish);
        newDish.setId(created.getId());
        assertMatch(service.getByRestaurant(FIRST_RESTAURANT_ID), FIRST_DISH, SECOND_DISH, newDish);
    }

    @Test
    void delete() {
        service.delete(SIXTH_DISH_ID, THIRD_RESTAURANT_ID);
        assertMatch(service.getByRestaurant(THIRD_RESTAURANT_ID), FIFTH_DISH, SEVENTH_DISH);
    }

    @Test
    void update() {
        Dish updated = new Dish(FIRST_DISH);
        updated.setPrice(100);
        service.update(updated);
        assertMatch(service.get(FIRST_DISH_ID), updated);
    }

    @Test
    void getByRestaurant() {
        assertMatch(service.getByRestaurant(THIRD_RESTAURANT_ID), FIFTH_DISH, SIXTH_DISH, SEVENTH_DISH);
    }

    @Test
    void get() {
        assertMatch(service.get(FIRST_DISH_ID), FIRST_DISH);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(1, FIRST_RESTAURANT_ID));
    }
}