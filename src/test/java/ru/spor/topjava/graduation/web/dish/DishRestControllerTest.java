package ru.spor.topjava.graduation.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spor.topjava.graduation.model.Dish;
import ru.spor.topjava.graduation.service.DishService;
import ru.spor.topjava.graduation.web.AbstractControllerTest;
import ru.spor.topjava.graduation.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spor.topjava.graduation.DishTestDataUtil.*;
import static ru.spor.topjava.graduation.TestUtil.readFromJson;
import static ru.spor.topjava.graduation.TestUtil.userHttpBasic;
import static ru.spor.topjava.graduation.UserTestDataUtil.ADMIN;
import static ru.spor.topjava.graduation.UserTestDataUtil.USER;
import static ru.spor.topjava.graduation.util.exception.ErrorType.APP_ERROR;

class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';
    @Autowired
    protected DishService service;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + FIRST_DISH_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(FIRST_DISH));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH, FIFTH_DISH, SIXTH_DISH, SEVENTH_DISH)));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + SIXTH_DISH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(service.getAll(Sort.by("id")), FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH, FIFTH_DISH, SEVENTH_DISH);
    }

    @Test
    void create() throws Exception {
        Dish expected = new Dish(null, "newDish", 10);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Dish returned = readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(Sort.by("id")), FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH, FIFTH_DISH, SIXTH_DISH, SEVENTH_DISH, expected);
    }

    @Test
    void update() throws Exception {
        Dish updated = new Dish(FIRST_DISH);
        updated.setName("UpdatingName");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + FIRST_DISH_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(service.get(FIRST_DISH_ID), updated);
    }

    @Test
    void wrongRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isInternalServerError())
                .andExpect(errorType(APP_ERROR));
    }
}