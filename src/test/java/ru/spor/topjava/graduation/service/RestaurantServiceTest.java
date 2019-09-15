package ru.spor.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.spor.topjava.graduation.model.Restaurant;
import ru.spor.topjava.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

    @Test
    void add() {
        Restaurant newRestaurant = new Restaurant(null, "newRestaurant");
        service.add(newRestaurant);
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT, newRestaurant);
    }

    @Test
    void delete() {
        service.delete(SECOND_RESTAURANT_ID);
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void update() {
        Restaurant updated = new Restaurant(FIRST_RESTAURANT);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(FIRST_RESTAURANT_ID), updated);
    }

    @Test
    void get() {
        assertMatch(service.get(FIRST_RESTAURANT_ID), FIRST_RESTAURANT);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}