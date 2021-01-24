package com.openwallet.api.controllers;

import com.openwallet.api.data.models.UserInfo;
import com.openwallet.api.testutils.As;
import com.openwallet.api.testutils.BaseIntegrationTest;
import com.openwallet.api.testutils.Endpoints;
import com.openwallet.api.testutils.TestUtils;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

class UserInfoControllerIT extends BaseIntegrationTest {
    @Test
    void GivenAUserItCanSaveItSuccessfully() throws Exception {
        UserInfo userInfo = new UserInfo("Test01", "TestL01", "test01@ow.email");

        As.Admin()
                .putRequest("/api/v1/users", userInfo)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(userInfo.getFirstName())));
    }

    @Test
    void GivenAUserItCanSaveAndRetrieveTheEntity() throws Exception {
        UserInfo userInfo = new UserInfo("Test01", "TestL01", "test01@ow.email");

        As.Admin()
                .putRequest("/api/v1/users", userInfo)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(userInfo.getFirstName())));

        As.Admin()
                .getRequest("/api/v1/users")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(userInfo.getFirstName())));
    }

    @Test
    void GivenAPartialUserItOnlyUpdatesTheGivenProperties() throws Exception {
        UserInfo userInfo = new UserInfo("Test01", "TestL01", "test01@ow.email");

        As.Admin()
                .putRequest("/api/v1/users", userInfo)
                .andDo(print())
                .andDo((data) -> userInfo.setId(TestUtils.readResponseAs(data, UserInfo.class)
                        .getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(userInfo.getFirstName())));

        As.Admin()
                .putRequest("/api/v1/users", Map.of("id", userInfo.getId(), "lastName", "Changed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Changed")));

        As.Admin()
                .getRequest(String.format("/api/v1/users/%s", userInfo.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(userInfo.getFirstName())));
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

    @Test
    public void ChangingPasswordWithNoNewPasswordRaisesError() throws Exception {
        As.UserA()
                .postRequest(Endpoints.USERS + "/change-password", Map.of("newPassword", ""))
                .andExpect(status().isBadRequest());

        As.UserA()
                .postRequest(Endpoints.USERS + "/change-password", Map.of())
                .andExpect(status().isBadRequest());
    }
}
