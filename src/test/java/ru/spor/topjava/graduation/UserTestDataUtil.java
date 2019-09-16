package ru.spor.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.spor.topjava.graduation.model.Role;
import ru.spor.topjava.graduation.model.User;
import ru.spor.topjava.graduation.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spor.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.spor.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.spor.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class UserTestDataUtil {
    public static final int ADMIN_ID = START_SEQ;
    public static final int USER_ID = START_SEQ + 1;

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
