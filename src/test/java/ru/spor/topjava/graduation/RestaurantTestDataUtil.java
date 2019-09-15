package ru.spor.topjava.graduation;

import ru.spor.topjava.graduation.model.Restaurant;
import ru.spor.topjava.graduation.to.RestaurantTo;
import ru.spor.topjava.graduation.to.RestaurantVoteTo;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu", "votes");
    }

    public static void assertMatch(RestaurantVoteTo actual, RestaurantVoteTo expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu", "votes").isEqualTo(expected);
    }

    public static void assertMatchTo(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu");
    }

    public static void assertMatchTo(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }

   /* public static ResultMatcher contentJson(List<RestaurantTo> expected) {
        return result -> assertMatchTo(readListFromJsonMvcResult(result, RestaurantTo.class), expected);
    }

    public static ResultMatcher contentJson(RestaurantTo expected) {
        return result -> assertMatchTo(readFromJsonMvcResult(result, RestaurantTo.class), expected);
    }*/
}
