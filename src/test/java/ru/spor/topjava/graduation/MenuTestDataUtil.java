package ru.spor.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.spor.topjava.graduation.model.Menu;
import ru.spor.topjava.graduation.to.MenuTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spor.topjava.graduation.DishTestDataUtil.*;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;
import static ru.spor.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.spor.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.spor.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class MenuTestDataUtil {

    public static final int FIRST_MENU_ID = START_SEQ + 12;
    public static final int NEW_MENU_ID = START_SEQ + 23;

    public static final Menu FIRST_MENU = new Menu(FIRST_MENU_ID, LocalDate.of(2019, 8, 19), FIRST_DISH, FIRST_RESTAURANT);
    public static final Menu SECOND_MENU = new Menu(FIRST_MENU_ID + 1, LocalDate.of(2019, 8, 19), SECOND_DISH, FIRST_RESTAURANT);
    public static final Menu THIRD_MENU = new Menu(FIRST_MENU_ID + 2, LocalDate.of(2019, 8, 19), THIRD_DISH, SECOND_RESTAURANT);
    public static final Menu FOURTH_MENU = new Menu(FIRST_MENU_ID + 3, LocalDate.of(2019, 8, 19), FOURTH_DISH, SECOND_RESTAURANT);
    public static final Menu FIFTH_MENU = new Menu(FIRST_MENU_ID + 4, LocalDate.of(2019, 8, 20), FIRST_DISH, THIRD_RESTAURANT);
    public static final Menu SIXTH_MENU = new Menu(FIRST_MENU_ID + 5, LocalDate.of(2019, 8, 20), FIRST_DISH, THIRD_RESTAURANT);
    public static final Menu SEVENTH_MENU = new Menu(FIRST_MENU_ID + 6, LocalDate.of(2019, 8, 20), FIRST_DISH, THIRD_RESTAURANT);
    public static final Menu NEW_MENU = new Menu(NEW_MENU_ID, LocalDate.of(2019, 1, 1), FIRST_DISH, FIRST_RESTAURANT);
    public static final Menu UPDATED_MENU = new Menu(FIRST_MENU_ID, LocalDate.of(2019, 1, 1), FIRST_DISH, FIRST_RESTAURANT);

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dish", "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dish", "restaurant").isEqualTo(expected);
    }

    public static MenuTo getUpdatedTo() {
        return new MenuTo(FIRST_MENU_ID, LocalDate.of(2019, 1, 1), FIRST_RESTAURANT_ID, FIRST_DISH_ID);
    }

    public static MenuTo getCreatedTo() {
        return new MenuTo(null, LocalDate.of(2019, 1, 1), FIRST_RESTAURANT_ID, FIRST_DISH_ID);
    }

    public static ResultMatcher contentJson(List<Menu> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), expected);
    }

    public static ResultMatcher contentJson(Menu expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Menu.class), expected);
    }
}
