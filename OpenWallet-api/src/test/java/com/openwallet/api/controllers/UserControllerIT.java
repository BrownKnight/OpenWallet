package com.openwallet.api.controllers;

import com.openwallet.api.data.models.User;
import com.openwallet.api.testutils.As;
import com.openwallet.api.testutils.BaseIntegrationTest;
import com.openwallet.api.testutils.Endpoints;
import com.openwallet.api.testutils.TestUtils;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

class UserControllerIT extends BaseIntegrationTest {
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
                .andDo((data) -> user.setId(TestUtils.readResponseAs(data, User.class)
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


    @Test
    public void AbleToChangeCurrentUsersPassword() throws Exception {
        As.UserA()
                .postRequest(Endpoints.USERS + "/change-password", Map.of("newPassword", "NewPassword"))
                .andExpect(status().isOk());

        As.UserA()
                .login(As.UserA()
                        .getUsername(), "NewPassword")
                .andExpect(status().isOk());
    }
}
