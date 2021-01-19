package com.openwallet.api.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public final class TestUtils {
    private static final Logger logger = LoggerFactory.getLogger(TestUtils.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readResponseAs(MvcResult result, Class<T> type) {
        try {
            String content = result.getResponse()
                    .getContentAsString();

            if (!content.isEmpty()) {
                return objectMapper.readValue(content, type);
            } else {
                logger.error("Empty string returned in response!");
            }
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to read a response!");
    }
}
