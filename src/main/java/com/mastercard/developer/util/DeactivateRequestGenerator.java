package com.mastercard.developer.util;

import org.openapitools.client.model.BillerManagementRequest;
import org.openapitools.client.model.DeactivationModel;
import org.openapitools.client.model.GeneralModel;

import java.util.ArrayList;
import java.util.List;

public class DeactivateRequestGenerator {

    public static List<BillerManagementRequest> generateRequest() {
        List<BillerManagementRequest> requestList = new ArrayList<>();
        requestList.add(generateDeactivateRequest());
        return requestList;
    }

    public static BillerManagementRequest generateDeactivateRequest(){
        BillerManagementRequest request = new BillerManagementRequest();
        request.setAction("Deactivate"); // required field
        String effectiveDate = DateUtil.getNextValidDate(); // valid effective date can't be weekends, or BPX Restricted Holidays
        request.setEffectiveDate(effectiveDate); // required field
        request.setGeneral(generateGeneralModel());
        request.setDeactivation(generateDeactivationModel());
        return request;
    }

    public static GeneralModel generateGeneralModel(){
        GeneralModel general = new GeneralModel();
        general.setBillerId("9871480623"); // Biller Id is the only required field in general section when deactivate
        return general;
    }

    public static DeactivationModel generateDeactivationModel(){
        DeactivationModel deactivationModel = new DeactivationModel();
        deactivationModel.setDeactivationReasonCode("BCON"); // required field
        deactivationModel.setDeactivationNotes("Deactivate Biller"); // required field
        return deactivationModel;
    }

}
