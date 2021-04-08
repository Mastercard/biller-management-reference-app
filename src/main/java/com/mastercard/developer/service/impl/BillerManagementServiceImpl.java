package com.mastercard.developer.service.impl;

import com.mastercard.developer.exception.ServiceException;
import com.mastercard.developer.service.BillerManagementService;
import com.mastercard.developer.util.AddRequestGenerator;
import com.mastercard.developer.util.DeactivateRequestGenerator;
import com.mastercard.developer.util.UpdateRequestGenerator;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.BillerManagementControllerApi;
import org.openapitools.client.model.BillerManagementRequest;
import org.openapitools.client.model.BillerManagementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BillerManagementServiceImpl implements BillerManagementService {

    private BillerManagementControllerApi billerManagementControllerApi;

    @Autowired
    public BillerManagementServiceImpl(ApiClient apiClient) {
        log.info("-->> INITIALIZING USER API");
        billerManagementControllerApi = new BillerManagementControllerApi(apiClient);
    }

    public List<BillerManagementResponse> manageBillers(String scenario) throws ServiceException {
        try {
            List<BillerManagementRequest> request = new ArrayList<>();
            if(scenario.equalsIgnoreCase("Add")){
                request = AddRequestGenerator.generateRequest();
            } else if(scenario.equalsIgnoreCase("Update")){
                request = UpdateRequestGenerator.generateRequest();
            } else if(scenario.equalsIgnoreCase("Deactivate")){
                request = DeactivateRequestGenerator.generateRequest();
            } else if(scenario.equalsIgnoreCase("All")){
                request.addAll(AddRequestGenerator.generateRequest());
                request.addAll(UpdateRequestGenerator.generateRequest());
                request.addAll(DeactivateRequestGenerator.generateRequest());
            }

            printRequest(request, scenario);
            List<BillerManagementResponse> response = billerManagementControllerApi.processBillerUploadPostRequestUsingPOST(request);
            printResponse(response);
            return response;
        } catch(Exception e){
            throw new ServiceException("Service Exception");
        }
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
