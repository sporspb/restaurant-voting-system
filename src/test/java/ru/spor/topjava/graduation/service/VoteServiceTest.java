package ru.spor.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.spor.topjava.graduation.model.Vote;

import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;
import static ru.spor.topjava.graduation.UserTestDataUtil.*;
import static ru.spor.topjava.graduation.VoteTestDataUtil.assertMatch;
import static ru.spor.topjava.graduation.VoteTestDataUtil.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;

    @Test
    void voteNewBeforeDecisionTime() {
        Vote newVote = new Vote(null, VOTE_DATE_TIME_NEW_BEFORE.toLocalDate(), SECOND_RESTAURANT, USER);
        newVote.setId(service.vote(USER_ID, SECOND_RESTAURANT_ID, VOTE_DATE_TIME_NEW_BEFORE.toLocalDate(), VOTE_DATE_TIME_NEW_BEFORE.toLocalTime()).getId());
        assertMatch(service.getForRestaurantAndDate(SECOND_RESTAURANT_ID, VOTE_DATE_TIME_NEW_AFTER.toLocalDate()), newVote);
    }

    @Test
    void voteNewAfterDecisionTime() {
        Vote newVote = new Vote(null, VOTE_DATE_TIME_NEW_AFTER.toLocalDate(), SECOND_RESTAURANT, ADMIN);
        newVote.setId(service.vote(ADMIN_ID, SECOND_RESTAURANT_ID, VOTE_DATE_TIME_NEW_AFTER.toLocalDate(), VOTE_DATE_TIME_NEW_AFTER.toLocalTime()).getId());
        assertMatch(service.getForRestaurantAndDate(SECOND_RESTAURANT_ID, VOTE_DATE_TIME_NEW_AFTER.toLocalDate()), newVote);
    }

    @Test
    void voteUpdateBeforeDecisionTime() {
        Vote newVote = new Vote(null, VOTE_DATE_TIME_BEFORE.toLocalDate(), SECOND_RESTAURANT, USER);
        Vote updated = service.vote(USER_ID, SECOND_RESTAURANT_ID, VOTE_DATE_TIME_BEFORE.toLocalDate(), VOTE_DATE_TIME_BEFORE.toLocalTime());
        newVote.setId(updated.getId());
        assertMatch(updated, newVote);
        assertMatch(service.getForRestaurantAndDate(SECOND_RESTAURANT_ID, VOTE_DATE_TIME_BEFORE.toLocalDate()), ADMIN_VOTE_2, updated);
    }

    @Test
    void voteUpdateAfterDecisionTime() {
        service.vote(USER_ID, SECOND_RESTAURANT_ID, VOTE_DATE_TIME_AFTER.toLocalDate(), VOTE_DATE_TIME_AFTER.toLocalTime());
        assertMatch(service.getForRestaurantAndDate(SECOND_RESTAURANT_ID, VOTE_DATE_TIME_AFTER.toLocalDate()), ADMIN_VOTE_2);
    }

    @Test
    void getAllForUser() {
        assertMatch(service.getAllForUser(USER_ID), USER_VOTE_1, USER_VOTE_2);
    }

    @Test
    void getAllForRestaurant() {
        assertMatch(service.getAllForRestaurant(FIRST_RESTAURANT_ID), USER_VOTE_1, ADMIN_VOTE_1, USER_VOTE_2);
    }

    @Test
    void getForUserAndDate() {
        assertMatch(service.getForUserAndDate(USER_ID, VOTE_DATE_TIME_BEFORE.toLocalDate()), USER_VOTE_2);
    }

    @Test
    void getForRestaurantAndDate() {
        assertMatch(service.getForRestaurantAndDate(SECOND_RESTAURANT_ID, VOTE_DATE_TIME_AFTER.toLocalDate()), ADMIN_VOTE_2);
    }
}