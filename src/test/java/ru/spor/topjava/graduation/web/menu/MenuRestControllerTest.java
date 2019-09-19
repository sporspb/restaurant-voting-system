package ru.spor.topjava.graduation.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spor.topjava.graduation.service.MenuService;
import ru.spor.topjava.graduation.to.MenuTo;
import ru.spor.topjava.graduation.web.AbstractControllerTest;
import ru.spor.topjava.graduation.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spor.topjava.graduation.MenuTestDataUtil.*;
import static ru.spor.topjava.graduation.TestUtil.userHttpBasic;
import static ru.spor.topjava.graduation.UserTestDataUtil.ADMIN;

public class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL + '/';
    @Autowired
    protected MenuService service;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + FIRST_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(FIRST_MENU));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(FIRST_MENU, SECOND_MENU, THIRD_MENU, FOURTH_MENU, FIFTH_MENU, SIXTH_MENU, SEVENTH_MENU)));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + FIRST_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(service.getAll(Sort.by("id")), SECOND_MENU, THIRD_MENU, FOURTH_MENU, FIFTH_MENU, SIXTH_MENU, SEVENTH_MENU);
    }

    @Test
    void getAllByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "?date=2019-08-19")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(FIRST_MENU, SECOND_MENU, THIRD_MENU, FOURTH_MENU)));
    }

    @Test
    void update() throws Exception {
        MenuTo updated = getUpdatedTo();
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + FIRST_MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)));

        assertMatch(service.get(FIRST_MENU_ID), UPDATED_MENU);
    }
}
