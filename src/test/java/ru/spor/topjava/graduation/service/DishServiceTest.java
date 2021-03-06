package ru.spor.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.spor.topjava.graduation.model.Dish;
import ru.spor.topjava.graduation.repository.JpaUtil;
import ru.spor.topjava.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.spor.topjava.graduation.DishTestDataUtil.*;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Autowired
    private JpaUtil jpaUtil;

    @Test
    void create() {
        Dish newDish = new Dish(null, "newDish test", 155);
        Dish created = service.create(newDish);
        newDish.setId(created.getId());
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH, FIFTH_DISH, SIXTH_DISH, SEVENTH_DISH, newDish);
    }

    @Test
    void delete() {
        service.delete(SIXTH_DISH_ID);
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH, FIFTH_DISH, SEVENTH_DISH);
    }

    @Test
    void update() {
        Dish updated = new Dish(FIRST_DISH);
        updated.setPrice(100);
        service.update(updated);
        assertMatch(service.get(FIRST_DISH_ID), updated);
    }

    @Test
    void get() {
        assertMatch(service.get(FIRST_DISH_ID), FIRST_DISH);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH, FIFTH_DISH, SIXTH_DISH, SEVENTH_DISH);
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