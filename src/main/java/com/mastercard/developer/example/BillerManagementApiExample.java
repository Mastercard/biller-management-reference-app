package com.mastercard.developer.example;

import com.mastercard.developer.interceptors.OkHttp2OAuth1Interceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.biller_management_client.api.BillerManagementControllerApi;
import com.mastercard.developer.biller_management_client.model.BillerManagementRequest;
import com.mastercard.developer.biller_management_client.model.BillerManagementResponse;
import com.mastercard.developer.biller_management_client.ApiClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class BillerManagementApiExample {

    private static String PAYLOAD_PATH = "payloads/biller-management-%s.json";

    private static boolean runThisScenario(String[] args, String scenario) {
        return (args!=null && args.length>0 && args[0].toLowerCase().contains(scenario)) || (args == null || args.length == 0);
    }

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static void main(String[] args) throws Exception {
        RequestHelper.loadProperties();
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(RequestHelper.getBaseURL());
        apiClient.getHttpClient().interceptors()
                .add(new OkHttp2OAuth1Interceptor(RequestHelper.getConsumerkey(), RequestHelper.getPrivateKey()));
        apiClient.setDebugging(true);

        BillerManagementControllerApi billerManagementControllerApi = new BillerManagementControllerApi(apiClient);

        if(runThisScenario(args,"add")) {
            // This is to add a new Biller into Billpay Exchange
            // Provides values for all required fields in BillerManagementRequest model
            executePostBillerScenario(billerManagementControllerApi, "add");
        }

        if(runThisScenario(args,"update")) {
            // This is to update details of an existing Biller
            // Only need to provide values for the fields that need to be updated, providing empty value means user wants to
            // update the field to Null value. Fields not exist in requesy payload means no changes needed.
            executePostBillerScenario(billerManagementControllerApi, "update");
        }

        if(runThisScenario(args,"deactivate")) {
            // This is to deactivate an existing Biller
            // Only need to provide values for DeactivationModel in BillerManagementRequest Model
            executePostBillerScenario(billerManagementControllerApi, "deactivate");
        }

        if(runThisScenario(args,"all")) {
            // This is to add, update, and deactivate Billers in same request payload
            executePostBillerScenario(billerManagementControllerApi, "all");
        }

    }

    private static void executePostBillerScenario(BillerManagementControllerApi billerManagementControllerApi, String scenario) {
        try{
            List<BillerManagementRequest> request = getRequestFromJson(scenario); // read sample JSON payload into java objects
            printRequest(request, scenario);
            List<BillerManagementResponse> response = billerManagementControllerApi.processBillerUploadPostRequestUsingPOST(request);
            printResponse(response);
        } catch (Exception e) {
            System.err.println("Exception when calling Post");
            e.printStackTrace();
        }
    }

    private static List<BillerManagementRequest> getRequestFromJson(String scenario) throws IOException {
        InputStream inputStream  = BillerManagementApiExample.class.getClassLoader().getResourceAsStream(String.format(PAYLOAD_PATH, scenario).toLowerCase());
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        List<BillerManagementRequest> request = objectMapper.readValue(content, new TypeReference<List<BillerManagementRequest>>(){});
        String effectiveDate = DateUtil.getNextValidDate();
        request.stream().forEach(billerManagementRequest -> billerManagementRequest.setEffectiveDate(effectiveDate));
        return request;
    }

    private static void printRequest(Object request, String scenario){
        System.out.println(String.format("---------Request for scenario %s---------", scenario));
        System.out.println(request);
        System.out.println("---------------Request End------------------");
    }

    private static void printResponse(Object response){
        System.out.println("---------------Parsed Response---------------");
        System.out.println(response);
        System.out.println("---------------Response End------------------");
    }
}
