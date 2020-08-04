package com.mastercard.developer.example;

import com.mastercard.developer.service.BillerManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BillerManagementApiExample {

    private BillerManagementService service;

    @Autowired
    public BillerManagementApiExample(BillerManagementService service) {
        this.service = service;
    }


    private static boolean runThisScenario(String[] args, String scenario) {
        return (args!=null && args.length>0 && args[0].toLowerCase().contains(scenario)) || (args == null || args.length == 0);
    }

    public void run(String[] args) throws Exception {

        log.info("<<<---- BILLER MANAGEMENT API EXECUTION STARTED ---->>>");

        if(runThisScenario(args,"add")) {
            // This is to add a new Biller into Billpay Exchange
            // Provides values for all required fields in BillerManagementRequest model
            this.service.manageBillers("add");
        }

        if(runThisScenario(args,"update")) {
            // This is to update details of an existing Biller
            // Only need to provide values for the fields that need to be updated, providing empty value means user wants to
            // update the field to Null value. Fields not exist in requesy payload means no changes needed.
            this.service.manageBillers("update");
        }

        if(runThisScenario(args,"deactivate")) {
            // This is to deactivate an existing Biller
            // Only need to provide values for DeactivationModel in BillerManagementRequest Model
            this.service.manageBillers("deactivate");
        }

        if(args == null || args.length == 0) {
            // This is to add, update, and deactivate Billers in same request payload
            this.service.manageBillers("all");
        }
        log.info("<<<---- BILLER MANAGEMENT API EXECUTION ENDED ---->>>");
    }


}
