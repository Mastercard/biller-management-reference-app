package com.mastercard.developer.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.example.BillerManagementApiExample;
import org.apache.commons.io.IOUtils;
import org.openapitools.client.model.BillerManagementRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SampleRequestLoader {

    private static String PAYLOAD_PATH = "payloads/biller-management-%s.json";

    public static List<BillerManagementRequest> getRequestFromJson(String scenario) throws IOException {
        InputStream inputStream  = BillerManagementApiExample.class.getClassLoader().getResourceAsStream(String.format(PAYLOAD_PATH, scenario).toLowerCase());
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        List<BillerManagementRequest> request = objectMapper.readValue(content, new TypeReference<List<BillerManagementRequest>>(){});
        String effectiveDate = DateUtil.getNextValidDate();
        request.stream().forEach(billerManagementRequest -> billerManagementRequest.setEffectiveDate(effectiveDate));
        return request;
    }
}
