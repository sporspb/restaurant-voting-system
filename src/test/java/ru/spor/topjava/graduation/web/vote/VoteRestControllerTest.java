package ru.spor.topjava.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spor.topjava.graduation.model.Vote;
import ru.spor.topjava.graduation.service.VoteService;
import ru.spor.topjava.graduation.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.FIRST_RESTAURANT;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.FIRST_RESTAURANT_ID;
import static ru.spor.topjava.graduation.TestUtil.readFromJson;
import static ru.spor.topjava.graduation.TestUtil.userHttpBasic;
import static ru.spor.topjava.graduation.UserTestDataUtil.ADMIN;
import static ru.spor.topjava.graduation.UserTestDataUtil.USER;
import static ru.spor.topjava.graduation.VoteTestDataUtil.*;

public class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    protected VoteService service;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/admin/vote/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/admin/vote/" + FIRST_VOTE_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER_VOTE_1));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/admin/vote")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(USER_VOTE_1, ADMIN_VOTE_1, USER_VOTE_2, ADMIN_VOTE_2)));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/admin/vote/" + FIRST_VOTE_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(service.getAll(Sort.by("id")), ADMIN_VOTE_1, USER_VOTE_2, ADMIN_VOTE_2);
    }

    @Test
    void vote() throws Exception {
        Vote created = new Vote(null, LocalDate.now(), FIRST_RESTAURANT, USER);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/rest/vote/" + FIRST_RESTAURANT_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated());

        Vote returned = readFromJson(result, Vote.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(Sort.by("id")), USER_VOTE_1, ADMIN_VOTE_1, USER_VOTE_2, ADMIN_VOTE_2, created);
    }
}
