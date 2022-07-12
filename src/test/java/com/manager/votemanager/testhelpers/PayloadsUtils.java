package com.manager.votemanager.testhelpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.manager.votemanager.models.v1.entity.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PayloadsUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static User getContentFrom(String path) throws JsonProcessingException {

        return objectMapper.readValue(path, User.class);
    }

    public static String getJsonAsString(String path) throws IOException {

        return Resources.toString(PayloadsUtils.class.getClassLoader().getResource("payloads/" + path), StandardCharsets.UTF_8);
    }
}
