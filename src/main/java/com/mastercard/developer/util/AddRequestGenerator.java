package com.mastercard.developer.util;

import org.openapitools.client.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddRequestGenerator {

    public static int BILLER_ID = 1871480623;

    public static List<BillerManagementRequest> generateRequest() {
        List<BillerManagementRequest> requestList = new ArrayList<>();
        // "Add" action with convenienceFee true, cardPaymentEnabled true and namCorridor true
        requestList.add(generateAddRequest(true, true, true));

        // "Add" action with convenienceFee true, cardPaymentEnabled false and namCorridor true
        requestList.add(generateAddRequest(true, false, true));

        // "Add" action with convenienceFee false, cardPaymentEnabled true and namCorridor false
        requestList.add(generateAddRequest(false, true, false));

        // "Add" action with convenienceFee false, cardPaymentEnabled false and namCorridor false
        requestList.add(generateAddRequest(false, false, false));

        return requestList;
    }

    public static BillerManagementRequest generateAddRequest(boolean convenienceFeeFlag, boolean cardPaymentEnabled, boolean namCorridor){
        BillerManagementRequest request = new BillerManagementRequest();
        request.setAction(BillerManagementRequest.ActionEnum.ADD); // required field
        String effectiveDate = DateUtil.getNextValidDate();  // valid effective date can't be weekends, or BPX Restricted Holidays
        request.setEffectiveDate(effectiveDate); // required field
        if(convenienceFeeFlag){
            request.setGeneral(generateGeneralModel(GeneralModel.ConvenienceFeeEnum.YES, namCorridor));
            request.setConvenienceFees(generateConvenienceFeeModelList(cardPaymentEnabled));
        } else {
            request.setGeneral(generateGeneralModel(GeneralModel.ConvenienceFeeEnum.NO, namCorridor));
        }

        if(cardPaymentEnabled) {
            request.setServiceRelationships(generateServiceRelationshipModelList(true, namCorridor));
            request.setCardPaymentSupport(generateCardPaymentModel());
        } else {
            request.setServiceRelationships(generateServiceRelationshipModelList(false, namCorridor));
        }
        request.setConsumerAuths(generateConsumerAuthenticationModelList());
        if(namCorridor) {
            request.setServiceAreas(generateServiceAreaModel()); // required field,
        } else {
            request.setCreditorAliases(generateCreditorAliasModelList());
        }

        return request;
    }

    public static GeneralModel generateGeneralModel(GeneralModel.ConvenienceFeeEnum convenienceFeeFlag, boolean namCorridor){
        GeneralModel general = new GeneralModel();
        general.setBillerId(String.valueOf(BILLER_ID++)); // required field
        general.setBillerLogoUrl("www.automation.com");
        general.setBillerShortName("Independence");
        general.setEstimatedPostingHour("3");
        general.setTermsAndConditions("Terms and Conditions");
        general.setConvenienceFee(convenienceFeeFlag);
        if(namCorridor) {
            general.setCurrencies(Arrays.asList("840"));
        } else {
            general.setCurrencies(Arrays.asList("978"));
            general.setBankDetail("TEST BANK DESC");
            general.setAccountInfo("SE7280000810340009783242");
        }
        return general;
    }

    public static List<ServiceRelationshipModel> generateServiceRelationshipModelList(boolean cardPaymentEnabled, boolean namCorridor){
        List<ServiceRelationshipModel> list = new ArrayList<>();
        ServiceRelationshipModel service = new ServiceRelationshipModel();
        service.setBspId("013001");
        service.setServiceType(ServiceRelationshipModel.ServiceTypeEnum.CL_EB_PAYC);
        List<ServiceRelationshipModel.SettlementServicesEnum> settlementServices = new ArrayList<>();
        settlementServices.add(ServiceRelationshipModel.SettlementServicesEnum.RPPS); //mandatory settlement service
        if(cardPaymentEnabled){
            settlementServices.add(ServiceRelationshipModel.SettlementServicesEnum.MPGS);
        }
        service.setSettlementServices(settlementServices);
        service.setCountries(namCorridor ? Arrays.asList("USA") : Arrays.asList("DNK"));
        service.setPrimaryCountry(namCorridor ? "USA" : "DNK"); // field only mandatory when multiple countries selected
        list.add(service);
        return list;
    }

    public static List<ConvenienceFeeModel> generateConvenienceFeeModelList(boolean cardPaymentEnabled){
        List<ConvenienceFeeModel> list = new ArrayList<>();
        ConvenienceFeeModel convBank = new ConvenienceFeeModel();
        convBank.setPaymentType(ConvenienceFeeModel.PaymentTypeEnum.BANK); // mandatory payment type when convenience fee is 'Yes'
        convBank.setFlatFee("2.00");
        convBank.setPercentFee("0.00");
        list.add(convBank);

        //Additional convenience Fee payment types must match the selections in CardPaymentModel cardTypes field
        if(cardPaymentEnabled) {
            ConvenienceFeeModel convCrdt = new ConvenienceFeeModel();
            convCrdt.setPaymentType(ConvenienceFeeModel.PaymentTypeEnum.CRDT);
            convCrdt.setPercentFee("0.02");
            convCrdt.setFlatFee("0.00");
            list.add(convCrdt);

            ConvenienceFeeModel convDebt = new ConvenienceFeeModel();
            convDebt.setPaymentType(ConvenienceFeeModel.PaymentTypeEnum.DEBT);
            convDebt.setFlatFee("2.00");
            convDebt.setPercentFee("0.00");
            list.add(convDebt);
        }
        return list;
    }

    public static CardPaymentModel generateCardPaymentModel(){
        CardPaymentModel cardPaymentModel = new CardPaymentModel();
        List<CardPaymentModel.CardNetworksEnum> cardNetworks = new ArrayList<>();
        cardNetworks.add(CardPaymentModel.CardNetworksEnum.MAST);
        cardNetworks.add(CardPaymentModel.CardNetworksEnum.VISA);
        cardPaymentModel.setCardNetworks(cardNetworks);

        List<CardPaymentModel.CardTypesEnum> cardTypes = new ArrayList<>();
        cardTypes.add(CardPaymentModel.CardTypesEnum.CRDT);
        cardTypes.add(CardPaymentModel.CardTypesEnum.DEBT);
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
        con.setCategory(ConsumerAuthenticationModel.CategoryEnum.CODE);
        con.setCategoryLabel("Code Label");
        con.setDataType(ConsumerAuthenticationModel.DataTypeEnum.P);
        con.setMaxLength("3");
        con.setNotes("Code consumer auth");
        list.add(con);
        return list;
    }

    private static List<CreditorAliasModel> generateCreditorAliasModelList() {
        List<CreditorAliasModel> creditorAliasModels = new ArrayList<>();
        CreditorAliasModel creditorAliasModel = new CreditorAliasModel();
        creditorAliasModel.setAliasName("Creditor Alias1");
        creditorAliasModel.setAccountInfo("US7280000810340009783243");
        creditorAliasModel.setBankDetail("ACCT INFO DESC 1");
        creditorAliasModel.setRecordAction(CreditorAliasModel.RecordActionEnum.ADD);
        creditorAliasModels.add(creditorAliasModel);
        return creditorAliasModels;
    }

}
