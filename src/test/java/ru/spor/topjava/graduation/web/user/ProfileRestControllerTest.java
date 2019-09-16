package ru.spor.topjava.graduation.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spor.topjava.graduation.model.User;
import ru.spor.topjava.graduation.service.UserService;
import ru.spor.topjava.graduation.to.UserTo;
import ru.spor.topjava.graduation.util.UserUtil;
import ru.spor.topjava.graduation.web.AbstractControllerTest;
import ru.spor.topjava.graduation.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spor.topjava.graduation.TestUtil.readFromJson;
import static ru.spor.topjava.graduation.TestUtil.userHttpBasic;
import static ru.spor.topjava.graduation.UserTestDataUtil.*;
import static ru.spor.topjava.graduation.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    protected UserService service;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(Sort.unsorted()), ADMIN);
    }

    @Test
    void register() throws Exception {
        UserTo createdTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);

        User created = UserUtil.createNewFromTo(createdTo);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getByEmail("newemail@ya.ru"), created);
    }

    //TODO fix not found
    /*@Test
    void registerNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new UserTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }*/

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(service.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER), updatedTo));
    }

    //TODO fix tests
    /*@Test
    void updateNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(new UserTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }*/
}
