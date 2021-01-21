package com.openwallet.api.controllers;

import com.openwallet.api.testutils.As;
import com.openwallet.api.testutils.BaseIntegrationTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserLoginControllerIT extends BaseIntegrationTest {
    @Test
    public void GivenValidCredentialsLoginSucceeds() throws Exception {
        As.Admin().login();
    }

    @Test
    public void GivenInvalidCredentialsLoginFails() throws Exception {
        As.Admin().login("BAD", "INVALID").andExpect(status().is(HttpStatus.SC_UNAUTHORIZED));
    }
}
