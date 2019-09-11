package ru.spor.topjava.graduation;

import ru.spor.topjava.graduation.model.Role;
import ru.spor.topjava.graduation.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spor.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int ADMIN_ID = START_SEQ;
    public static final int USER_ID = START_SEQ + 1;
    // public static final int USER_2_ID = START_SEQ + 2;
    // public static final int USER_3_ID = START_SEQ + 3;

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final User USER = new User(USER_ID, "User1", "user1@yandex.ru", "password1", Role.ROLE_USER);
    // public static final User USER_2 = new User(USER_2_ID, "User2", "user2@yandex.ru", "password2", Role.ROLE_USER);
    // public static final User USER_3 = new User(USER_3_ID, "User3", "user3@yandex.ru", "password3", Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes", "password").isEqualTo(expected);
    }
}
