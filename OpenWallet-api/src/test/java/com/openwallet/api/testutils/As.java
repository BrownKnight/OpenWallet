package com.openwallet.api.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openwallet.api.data.models.UserInfo;
import com.openwallet.api.data.models.UserLogin;
import com.openwallet.api.data.models.responses.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

public class As {
    private static final Logger logger = LoggerFactory.getLogger(As.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockMvc mockMvc;

    private static As AdminInstance;
    private static As UserAInstance;
    private static As UserBInstance;
    private final String username;
    private Long userId;
    private String token;

    private As(String username) {
        this.username = username;
    }

    public static As Admin() throws Exception {
        if (AdminInstance == null) {
            AdminInstance = new As("Admin");
            AdminInstance.initUserData();
            TestData.initIntegrationData();
        }
        return AdminInstance;
    }

    public static As UserA() throws Exception {
        if (UserAInstance == null) {
            UserAInstance = new As("UserA");
            UserAInstance.initUserData();
        }
        return UserAInstance;
    }

    public static As UserB() throws Exception {
        if (UserBInstance == null) {
            UserBInstance = new As("UserB");
            UserBInstance.initUserData();
        }
        return UserBInstance;
    }

    public static void setMockMvc(MockMvc mockMvc) {
        As.mockMvc = mockMvc;
    }

    public String getUsername() {
        return username;
    }

    private void initUserData() throws Exception {
        logger.info("Creating user data for {}", this.username);
        UserLogin userLogin = new UserLogin(this.username, "TEST_PASSWORD");
        userLogin.setUser(new UserInfo(this.username, this.username, String.format("%s@ow.email", this.username)));

        userId = TestUtils.readResponseAs(this.postRequest("/api/login/register", userLogin)
                .andExpect(status().isOk())
                .andReturn(), UserLogin.class)
                .getId();

        this.login();
    }

    public ResultActions postRequest(String url, Object content) throws Exception {
        String strContent = objectMapper.writeValueAsString(content);
        return this.postRequest(url, strContent);
    }

    public ResultActions postRequest(String url, String content) throws Exception {
        return mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token)));
    }

    public ResultActions putRequest(String url, Object content) throws Exception {
        String strContent = objectMapper.writeValueAsString(content);
        return this.putRequest(url, strContent);
    }

    public ResultActions putRequest(String url, String content) throws Exception {
        return mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token)));
    }

    public ResultActions getRequest(String url) throws Exception {
        return mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token)));
    }

    public As login() throws Exception {
        try {
            MvcResult body = this.login(this.username, "TEST_PASSWORD")
                    .andExpect(status().isOk())
                    .andReturn();

            LoginResponse res = TestUtils.readResponseAs(body, LoginResponse.class);
            if (res != null) {
                this.token = res.getToken();
            }
        } catch (Exception e) {
            logger.error("Could not login with user {}", this.username);
            logger.error(e.getMessage());
            throw e;
        }

        return this;
    }

    public ResultActions login(String username, String password) throws Exception {
        return this.postRequest("/api/login", Map.of("username", username, "password", password));
    }

    public Long getUserId() {
        return userId;
    }
}
