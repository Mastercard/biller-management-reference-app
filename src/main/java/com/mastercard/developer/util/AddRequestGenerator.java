package com.mastercard.developer.util;

import org.openapitools.client.model.*;

import java.util.ArrayList;
import java.util.List;

public class AddRequestGenerator {

    public static int BILLER_ID = 1871480623;

    public static List<BillerManagementRequest> generateRequest() {
        List<BillerManagementRequest> requestList = new ArrayList<>();
        // "Add" action with convenienceFee true and cardPaymentEnabled true
        requestList.add(generateAddRequest(true, true));

        // "Add" action with convenienceFee true and cardPaymentEnabled false
        requestList.add(generateAddRequest(true, false));

        // "Add" action with convenienceFee false and cardPaymentEnabled true
        requestList.add(generateAddRequest(false, true));

        // "Add" action with convenienceFee false and cardPaymentEnabled false
        requestList.add(generateAddRequest(false, false));

        return requestList;
    }

    public static BillerManagementRequest generateAddRequest(boolean convenienceFeeFlag, boolean cardPaymentEnabled){
        BillerManagementRequest request = new BillerManagementRequest();
        request.setAction("Add"); // required field
        String effectiveDate = DateUtil.getNextValidDate();  // valid effective date can't be weekends, or BPX Restricted Holidays
        request.setEffectiveDate(effectiveDate); // required field
        if(convenienceFeeFlag){
            request.setGeneral(generateGeneralModel("Yes"));
            request.setConvenienceFees(generateConvenienceFeeModelList(cardPaymentEnabled));
        } else {
            request.setGeneral(generateGeneralModel("No"));
        }

        if(cardPaymentEnabled) {
            request.setServiceRelationships(generateServiceRelationshipModelList(true));
            request.setCardPaymentSupport(generateCardPaymentModel());
        } else {
            request.setServiceRelationships(generateServiceRelationshipModelList(false));
        }
        request.setServiceAreas(generateServiceAreaModel()); // required field,
        request.setConsumerAuths(generateConsumerAuthenticationModelList());
        return request;
    }

    public static GeneralModel generateGeneralModel(String convenienceFeeFlag){
        GeneralModel general = new GeneralModel();
        general.setBillerId(String.valueOf(BILLER_ID++)); // required field
        general.setBillerLogoUrl("www.automation.com");
        general.setBillerShortName("Independence");
        general.setEstimatedPostingHour("3");
        general.setTermsAndConditions("Terms and Conditions");
        general.setConvenienceFee(convenienceFeeFlag);
        return general;
    }

    public static List<ServiceRelationshipModel> generateServiceRelationshipModelList(boolean cardPaymentEnabled){
        List<ServiceRelationshipModel> list = new ArrayList<>();
        ServiceRelationshipModel service = new ServiceRelationshipModel();
        service.setBspId("013001");
        service.setServiceType("BPX_CL_EB_PAYC");
        List<String> settlementServices = new ArrayList<>();
        settlementServices.add("RPPS"); //mandatory settlement service
        if(cardPaymentEnabled){
            settlementServices.add("MPGS");
        }
        service.setSettlementServices(settlementServices);
        list.add(service);
        return list;
    }

    public static List<ConvenienceFeeModel> generateConvenienceFeeModelList(boolean cardPaymentEnabled){
        List<ConvenienceFeeModel> list = new ArrayList<>();
        ConvenienceFeeModel convBank = new ConvenienceFeeModel();
        convBank.setPaymentType("BANK"); // mandatory payment type when convenience fee is 'Yes'
        convBank.setFlatFee("2.00");
        convBank.setPercentFee("0.00");
        list.add(convBank);

        //Additional convenience Fee payment types must match the selections in CardPaymentModel cardTypes field
        if(cardPaymentEnabled) {
            ConvenienceFeeModel convCrdt = new ConvenienceFeeModel();
            convCrdt.setPaymentType("CRDT");
            convCrdt.setPercentFee("0.02");
            convCrdt.setFlatFee("0.00");
            list.add(convCrdt);

            ConvenienceFeeModel convDebt = new ConvenienceFeeModel();
            convDebt.setPaymentType("DEBT");
            convDebt.setFlatFee("2.00");
            convDebt.setPercentFee("0.00");
            list.add(convDebt);
        }
        return list;
    }

    public static CardPaymentModel generateCardPaymentModel(){
        CardPaymentModel cardPaymentModel = new CardPaymentModel();
        List<String> cardNetworks = new ArrayList<>();
        cardNetworks.add("MAST");
        cardNetworks.add("VISA");
        cardPaymentModel.setCardNetworks(cardNetworks);

        List<String> cardTypes = new ArrayList<>();
        cardTypes.add("CRDT");
        cardTypes.add("DEBT");
        cardPaymentModel.setCardTypes(cardTypes);
        return cardPaymentModel;
    }

    public static ServiceAreaModel generateServiceAreaModel(){
        ServiceAreaModel serviceAreaModel = new ServiceAreaModel();
        serviceAreaModel.setZipCodes("44445,12345");
        return serviceAreaModel;
    }

    public static List<ConsumerAuthenticationModel> generateConsumerAuthenticationModelList(){
        List<ConsumerAuthenticationModel> list = new ArrayList<>();
        ConsumerAuthenticationModel con = new ConsumerAuthenticationModel();
        con.setCategory("CODE");
        con.setCategoryLabel("Code Label");
        con.setDataType("P");
        con.setMaxLength("3");
        con.setNotes("Code consumer auth");
        list.add(con);
        return list;
    }
}
