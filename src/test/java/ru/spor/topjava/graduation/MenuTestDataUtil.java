package ru.spor.topjava.graduation;

import ru.spor.topjava.graduation.model.Menu;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spor.topjava.graduation.DishTestDataUtil.*;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;
import static ru.spor.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class MenuTestDataUtil {

    public static final int FIRST_MENU_ID = START_SEQ + 12;

    public static final Menu FIRST_MENU = new Menu(FIRST_MENU_ID, LocalDate.of(2019, 8, 19), FIRST_DISH, FIRST_RESTAURANT);
    public static final Menu SECOND_MENU = new Menu(FIRST_MENU_ID + 1, LocalDate.of(2019, 8, 19), SECOND_DISH, FIRST_RESTAURANT);
    public static final Menu THIRD_MENU = new Menu(FIRST_MENU_ID + 2, LocalDate.of(2019, 8, 19), THIRD_DISH, SECOND_RESTAURANT);
    public static final Menu FOURTH_MENU = new Menu(FIRST_MENU_ID + 3, LocalDate.of(2019, 8, 19), FOURTH_DISH, SECOND_RESTAURANT);
    public static final Menu FIFTH_MENU = new Menu(FIRST_MENU_ID + 4, LocalDate.of(2019, 8, 20), FIRST_DISH, THIRD_RESTAURANT);
    public static final Menu SIXTH_MENU = new Menu(FIRST_MENU_ID + 5, LocalDate.of(2019, 8, 20), FIRST_DISH, THIRD_RESTAURANT);
    public static final Menu SEVENTH_MENU = new Menu(FIRST_MENU_ID + 6, LocalDate.of(2019, 8, 20), FIRST_DISH, THIRD_RESTAURANT);

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dish", "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dish", "restaurant").isEqualTo(expected);
    }
}
