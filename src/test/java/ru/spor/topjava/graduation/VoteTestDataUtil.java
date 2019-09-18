package ru.spor.topjava.graduation;

import ru.spor.topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.FIRST_RESTAURANT;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.SECOND_RESTAURANT;
import static ru.spor.topjava.graduation.UserTestDataUtil.ADMIN;
import static ru.spor.topjava.graduation.UserTestDataUtil.USER;
import static ru.spor.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestDataUtil {

    public static final int FIRST_VOTE_ID = START_SEQ + 19;

    public static final LocalDateTime VOTE_DATE_TIME_NEW_BEFORE = LocalDateTime.of(2019, 8, 21, 10, 5);
    public static final LocalDateTime VOTE_DATE_TIME_NEW_AFTER = LocalDateTime.of(2019, 8, 21, 11, 5);
    public static final LocalDateTime VOTE_DATE_TIME_BEFORE = LocalDateTime.of(2019, 8, 20, 10, 3);
    public static final LocalDateTime VOTE_DATE_TIME_AFTER = LocalDateTime.of(2019, 8, 20, 11, 5);

    public static final Vote USER_VOTE_1 = new Vote(FIRST_VOTE_ID, LocalDate.of(2019, 8, 19), FIRST_RESTAURANT, USER);
    public static final Vote USER_VOTE_2 = new Vote(FIRST_VOTE_ID + 2, LocalDate.of(2019, 8, 20), FIRST_RESTAURANT, USER);

    public static final Vote ADMIN_VOTE_1 = new Vote(FIRST_VOTE_ID + 1, LocalDate.of(2019, 8, 19), FIRST_RESTAURANT, ADMIN);
    public static final Vote ADMIN_VOTE_2 = new Vote(FIRST_VOTE_ID + 3, LocalDate.of(2019, 8, 20), SECOND_RESTAURANT, ADMIN);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(expected);
    }

    /*public static ResultMatcher contentJson(Vote... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<Vote> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), expected);
    }

    public static ResultMatcher contentJson(Vote expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Vote.class), expected);
    }*/
}
