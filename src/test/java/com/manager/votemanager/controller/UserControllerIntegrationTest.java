package com.manager.votemanager.controller;

import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.models.enums.RoleEnum;
import com.manager.votemanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.manager.votemanager.helpers.PayloadsUtils.getJsonAsString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldGetUserWhenIHaveAValidName() throws Exception {
        final String name = "Testing";
        final String response = getJsonAsString("user.json");

        doReturn(mockUserResult()).when(userService).getUser(name);

        final MvcResult result = mockMvc.perform(get("/v1/user")
                        .contentType(APPLICATION_JSON)
                        .param("name", name))
                .andExpect(status().isOk())
                .andReturn();

        final String actualResponse = result.getResponse().getContentAsString();

        JSONAssert.assertEquals(response, actualResponse, false);
    }

    @Test
    void shouldStatus201WhenCreateUserValid() throws Exception {
        final String request = getJsonAsString("user.json");

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(mockUserResult());

        this.mockMvc.perform(post("/v1/user/create")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }

    private static User mockUserResult() {

        return new User(1L, "Testing", "12345678910", "test@email.com", "123456", RoleEnum.ADMIN);
    }

}
