package com.openwallet.api.testutils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseIntegrationTest.class);

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        As.setMockMvc(mockMvc);
        As.Admin();
    }
}
