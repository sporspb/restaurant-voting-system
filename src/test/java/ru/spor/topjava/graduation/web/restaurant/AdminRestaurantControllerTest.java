package ru.spor.topjava.graduation.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spor.topjava.graduation.RestaurantTestDataUtil;
import ru.spor.topjava.graduation.model.Restaurant;
import ru.spor.topjava.graduation.service.RestaurantService;
import ru.spor.topjava.graduation.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spor.topjava.graduation.RestaurantTestDataUtil.*;
import static ru.spor.topjava.graduation.TestUtil.readFromJson;
import static ru.spor.topjava.graduation.TestUtil.userHttpBasic;
import static ru.spor.topjava.graduation.UserTestDataUtil.ADMIN;
import static ru.spor.topjava.graduation.web.json.JsonUtil.writeValue;
import static ru.spor.topjava.graduation.web.restaurant.AdminRestaurantController.REST_URL;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    protected RestaurantService service;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void add() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant");
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        newRestaurant.setId(returned.getId());

        RestaurantTestDataUtil.assertMatch(returned, newRestaurant);
        assertMatch(service.getAll(Sort.by(Sort.Direction.ASC, "id")), FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT, newRestaurant);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(FIRST_RESTAURANT);
        updated.setName("UpdatedName");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/{restaurantId}", FIRST_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(service.get(FIRST_RESTAURANT_ID), updated);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/{restaurantId}", SECOND_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(service.getAll(Sort.unsorted()), FIRST_RESTAURANT, THIRD_RESTAURANT);
    }
}