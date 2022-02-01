package com.mastercard.developer.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.example.BillerManagementApiExample;
import org.apache.commons.io.IOUtils;
import org.openapitools.client.model.BillerManagementResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SampleResponseLoader {

    private static String PAYLOAD_PATH = "biller-management-%s-response.json";

    public static List<BillerManagementResponse> getResponseFromJson(String scenario) throws IOException {
        InputStream inputStream  = BillerManagementApiExample.class.getClassLoader().getResourceAsStream(String.format(PAYLOAD_PATH, scenario).toLowerCase());
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BillerManagementResponse> responses = objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING).readValue(content, new TypeReference<List<BillerManagementResponse>>(){});
        return responses;
    }
}
