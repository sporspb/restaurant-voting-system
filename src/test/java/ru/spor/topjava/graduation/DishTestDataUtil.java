package ru.spor.topjava.graduation;

import ru.spor.topjava.graduation.model.Dish;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spor.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class DishTestDataUtil {

    public static final int FIRST_DISH_ID = START_SEQ + 5;
    public static final int SIXTH_DISH_ID = START_SEQ + 10;
    public static final Dish FIRST_DISH = new Dish(FIRST_DISH_ID, "Lasagna", 155);
    public static final Dish SECOND_DISH = new Dish(FIRST_DISH_ID + 1, "Roast beef", 190);
    public static final Dish THIRD_DISH = new Dish(FIRST_DISH_ID + 2, "Salad", 70);
    public static final Dish FOURTH_DISH = new Dish(FIRST_DISH_ID + 3, "Cake", 80);
    public static final Dish FIFTH_DISH = new Dish(FIRST_DISH_ID + 4, "Risotto", 90);
    public static final Dish SIXTH_DISH = new Dish(SIXTH_DISH_ID, "Soup", 50);
    public static final Dish SEVENTH_DISH = new Dish(FIRST_DISH_ID + 6, "Fish", 115);

    private DishTestDataUtil() {
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    /*
    public static void assertMatchToWithRestaurant(DishRestaurantTo actual, DishRestaurantTo expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static void assertMatchDishTo(Iterable<DishTo> actual, Iterable<DishTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static void assertMatchDishToIgnoreId(Iterable<DishTo> actual, Iterable<DishTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "id").isEqualTo(expected);
    }

    public static void assertMatchDishTo(DishTo actual, DishTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }*/
}
