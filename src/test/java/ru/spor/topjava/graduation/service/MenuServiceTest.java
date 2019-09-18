package ru.spor.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.spor.topjava.graduation.model.Menu;
import ru.spor.topjava.graduation.repository.JpaUtil;
import ru.spor.topjava.graduation.to.MenuTo;

import java.time.LocalDate;

import static ru.spor.topjava.graduation.DishTestDataUtil.*;
import static ru.spor.topjava.graduation.MenuTestDataUtil.assertMatch;
import static ru.spor.topjava.graduation.MenuTestDataUtil.*;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Autowired
    private JpaUtil jpaUtil;

    @Test
    void create() {
        Menu newMenu = new Menu(LocalDate.of(2019, 8, 19), THIRD_RESTAURANT);
        Menu created = service.create(newMenu, FIRST_DISH_ID, THIRD_RESTAURANT_ID);
        assertMatch(service.getAllByRestaurantId(THIRD_RESTAURANT_ID), FIFTH_MENU, SIXTH_MENU, SEVENTH_MENU, created);
    }

    @Test
    void createFromTo() {
        Menu newMenu = new Menu(null, LocalDate.of(2019, 8, 19));
        newMenu.setDish(FIRST_DISH);
        newMenu.setRestaurant(THIRD_RESTAURANT);
        MenuTo menuTo = new MenuTo(newMenu);
        Menu created = service.createFromTo(menuTo);
        assertMatch(service.getAllByRestaurantId(THIRD_RESTAURANT_ID), FIFTH_MENU, SIXTH_MENU, SEVENTH_MENU, created);
    }

    @Test
    void update() {
        Menu updated = new Menu(FIRST_MENU);
        updated.setDish(SIXTH_DISH);
        service.update(updated);
        assertMatch(service.getById(FIRST_MENU_ID), updated);
    }

    @Test
    void updateFromTo() {
        Menu updated = new Menu(FIRST_MENU);
        updated.setRestaurant(THIRD_RESTAURANT);
        updated.setDate(LocalDate.of(2019, 8, 22));
        MenuTo menuTo = new MenuTo(updated);
        service.updateFromTo(menuTo);
        assertMatch(service.getById(FIRST_MENU_ID), updated);
    }

    @Test
    void delete() {
        service.delete(FIRST_MENU_ID);
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), SECOND_MENU, THIRD_MENU, FOURTH_MENU, FIFTH_MENU, SIXTH_MENU, SEVENTH_MENU);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_MENU, SECOND_MENU, THIRD_MENU, FOURTH_MENU, FIFTH_MENU, SIXTH_MENU, SEVENTH_MENU);
    }

    @Test
    void getAllByDate() {
        assertMatch(service.getAllByDate(LocalDate.of(2019, 8, 19)), FIRST_MENU, SECOND_MENU, THIRD_MENU, FOURTH_MENU);
    }

    @Test
    void getAllByRestaurantId() {
        assertMatch(service.getAllByRestaurantId(THIRD_RESTAURANT_ID), FIFTH_MENU, SIXTH_MENU, SEVENTH_MENU);
    }

    @Test
    void getAllByDateAndRestaurantId() {
        assertMatch(service.getAllByDateAndRestaurantId(LocalDate.of(2019, 8, 19), SECOND_RESTAURANT_ID), THIRD_MENU, FOURTH_MENU);
    }

    @Test
    void getById() {
        Menu menu = service.getById(FIRST_MENU_ID);
        assertMatch(menu, FIRST_MENU);
    }
}