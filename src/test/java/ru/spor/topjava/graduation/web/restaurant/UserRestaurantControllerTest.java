package ru.spor.topjava.graduation.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spor.topjava.graduation.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;
import static ru.spor.topjava.graduation.TestUtil.userHttpBasic;
import static ru.spor.topjava.graduation.UserTestDataUtil.USER;
import static ru.spor.topjava.graduation.web.restaurant.UserRestaurantController.REST_URL;

class UserRestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT)));
    }

    @Test
    void getRestaurantById() throws Exception {
        mockMvc.perform(get(REST_URL + "/{restaurantId}", FIRST_RESTAURANT_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(FIRST_RESTAURANT));
    }
}