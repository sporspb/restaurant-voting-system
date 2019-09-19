package ru.spor.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.spor.topjava.graduation.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spor.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.spor.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.spor.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestDataUtil {
    public static final int FIRST_RESTAURANT_ID = START_SEQ + 2;
    public static final int SECOND_RESTAURANT_ID = START_SEQ + 3;
    public static final int THIRD_RESTAURANT_ID = START_SEQ + 4;
    public static final Restaurant FIRST_RESTAURANT = new Restaurant(FIRST_RESTAURANT_ID, "Restaurant1");
    public static final Restaurant SECOND_RESTAURANT = new Restaurant(SECOND_RESTAURANT_ID, "Restaurant2");
    public static final Restaurant THIRD_RESTAURANT = new Restaurant(THIRD_RESTAURANT_ID, "Restaurant3");

    private RestaurantTestDataUtil() {
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menus", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menus", "votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(List<Restaurant> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), expected);
    }
}
