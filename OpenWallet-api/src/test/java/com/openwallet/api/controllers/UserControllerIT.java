package com.openwallet.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openwallet.api.data.models.User;
import com.openwallet.api.testutils.As;
import com.openwallet.api.testutils.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "test")
class UserControllerIT extends BaseIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void GivenAUserItCanSaveItSuccessfully() throws Exception {
        User user = new User("Test01", "TestL01", "test01@ow.email");

        As.Admin()
                .putRequest("/api/v1/users", user)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(user.getFirstName())));
    }

    @Test
    void GivenAUserItCanSaveAndRetrieveTheEntity() throws Exception {
        User user = new User("Test01", "TestL01", "test01@ow.email");

        As.Admin()
                .putRequest("/api/v1/users", user)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(user.getFirstName())));

        As.Admin()
                .getRequest("/api/v1/users")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(user.getFirstName())));
    }

    @Test
    void GivenAPartialUserItOnlyUpdatesTheGivenProperties() throws Exception {
        User user = new User("Test01", "TestL01", "test01@ow.email");

        As.Admin()
                .putRequest("/api/v1/users", user)
                .andDo(print())
                .andDo((data) -> user.setId(objectMapper.readValue(data.getResponse()
                        .getContentAsString(), User.class)
                        .getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(user.getFirstName())));

        As.Admin()
                .putRequest("/api/v1/users", Map.of("id", user.getId(), "lastName", "Changed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Changed")));

        As.Admin()
                .getRequest(String.format("/api/v1/users/%s", user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(user.getFirstName())));
    }

}
