package com.mastercard.developer.util;

import org.openapitools.client.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateRequestGenerator {

    public static int BILLER_ID = 1971480623;

    public static List<BillerManagementRequest> generateRequest() {
        List<BillerManagementRequest> requestList = new ArrayList<>();

        // Update fields in general section
        requestList.add(generateUpdateGeneralRequest());

        // Edit existing service relationship
        requestList.add(generateUpdateEditServiceRelationshipRequest());

        // Delete existing service relationship and add new one
        requestList.add(generateUpdateAddDeleteServiceRelationshipRequest());

        // Edit existing consumer auth and Add a new one
        requestList.add(generateUpdateConsumerAuthRequest());

        // Edit existing card payment
        requestList.add(generateUpdateCardPaymentRequest());

        // Edit service area
        requestList.add(generateUpdateServiceAreaRequest());

        // Edit Convenience Fee
        requestList.add(generateUpdateConvenienceFeeRequest());

        return requestList;
    }

    public static BillerManagementRequest generateUpdateRequestBasic(){
        BillerManagementRequest request = new BillerManagementRequest();
        request.setAction(BillerManagementRequest.ActionEnum.UPDATE); // required field
        String effectiveDate = DateUtil.getNextValidDate(); // valid effective date can't be weekends, or BPX Restricted Holidays
        request.setEffectiveDate(effectiveDate);

        GeneralModel general = new GeneralModel();
        general.setBillerId(String.valueOf(BILLER_ID++)); //existing biller id that needs updates
        request.setGeneral(general);
        return request;
    }

    public static BillerManagementRequest generateUpdateGeneralRequest(){
        BillerManagementRequest request = generateUpdateRequestBasic(); //Only set value for the fields you want to update
        request.getGeneral().setBillerLogoUrl("www.updatedURL.com");
        request.getGeneral().setBillerShortName("Updated biller name");
        return request;
    }

    public static BillerManagementRequest generateUpdateEditServiceRelationshipRequest(){
        BillerManagementRequest request = generateUpdateRequestBasic();
        request.setServiceRelationships(generateUpdateServiceRelationshipModelList("Update"));
        return request;
    }

    public static BillerManagementRequest generateUpdateAddDeleteServiceRelationshipRequest(){
        BillerManagementRequest request = generateUpdateRequestBasic();
        request.setServiceRelationships(generateUpdateServiceRelationshipModelList("Add"));
        return request;
    }

    public static BillerManagementRequest generateUpdateConsumerAuthRequest(){
        BillerManagementRequest request = generateUpdateRequestBasic();

        List<ConsumerAuthenticationModel> list = new ArrayList<>();
        // The combination of category and category label is key field,
        // always need to add these 2 fields when updating, it shoud match to an existing record
        ConsumerAuthenticationModel conEdit = new ConsumerAuthenticationModel();
        conEdit.setRecordAction(ConsumerAuthenticationModel.RecordActionEnum.UPDATE);
        conEdit.setCategory(ConsumerAuthenticationModel.CategoryEnum.CODE);
        conEdit.setCategoryLabel("Code Label");
        conEdit.setMaxLength("10"); //update max length field
        conEdit.setNotes("Update max length");
        list.add(conEdit);

        ConsumerAuthenticationModel conAdd = new ConsumerAuthenticationModel();
        conAdd.setRecordAction(ConsumerAuthenticationModel.RecordActionEnum.ADD);
        conAdd.setCategory(ConsumerAuthenticationModel.CategoryEnum.IDEN);
        conAdd.setCategoryLabel("IDEN Label");
        conAdd.setDataType(ConsumerAuthenticationModel.DataTypeEnum.A);
        conAdd.setMaxLength("10");
        conAdd.setNotes("Add new consumer auth");
        list.add(conAdd);

        request.setConsumerAuths(list);
        return request;
    }

    public static BillerManagementRequest generateUpdateCardPaymentRequest(){
        BillerManagementRequest request = generateUpdateRequestBasic();

        CardPaymentModel cardPaymentModel = new CardPaymentModel();
        List<CardPaymentModel.CardNetworksEnum> cardNetworks = new ArrayList<>();
        cardNetworks.add(CardPaymentModel.CardNetworksEnum.MAST); // only keep MAST as card network
        cardPaymentModel.setCardNetworks(cardNetworks);

        List<CardPaymentModel.CardTypesEnum> cardTypes = new ArrayList<>();
        cardTypes.add(CardPaymentModel.CardTypesEnum.CRDT); //only keep CRDT as card types
        cardPaymentModel.setCardTypes(cardTypes);

        request.setCardPaymentSupport(cardPaymentModel);
        return request;
    }

    public static List<ServiceRelationshipModel> generateUpdateServiceRelationshipModelList(String recordAction){
        List<ServiceRelationshipModel> list = new ArrayList<>();
        if(recordAction.equalsIgnoreCase("Update")){
            // BSP ID is the key field, if BSP ID doesn't change, then it's an 'Update' action
            ServiceRelationshipModel serviceUpdate = new ServiceRelationshipModel();
            serviceUpdate.setRecordAction(ServiceRelationshipModel.RecordActionEnum.UPDATE);
            serviceUpdate.setBspId("013001");
            serviceUpdate.setServiceType(ServiceRelationshipModel.ServiceTypeEnum.CL_EB); // downgrade service type to BPX_CL_EB
            List<ServiceRelationshipModel.SettlementServicesEnum> settlementServicesAdd = new ArrayList<>();
            settlementServicesAdd.add(ServiceRelationshipModel.SettlementServicesEnum.RPPS); //mandatory settlement service
            serviceUpdate.setSettlementServices(settlementServicesAdd);
            list.add(serviceUpdate);
        } else{
            //if BSP ID changed, then it's an 'Add' new and "Delete" old action
            ServiceRelationshipModel serviceAdd = new ServiceRelationshipModel();
            serviceAdd.setRecordAction(ServiceRelationshipModel.RecordActionEnum.ADD);
            serviceAdd.setBspId("013111"); // new servive relationship
            serviceAdd.setServiceType(ServiceRelationshipModel.ServiceTypeEnum.CL_EB_PAYC);
            List<ServiceRelationshipModel.SettlementServicesEnum> settlementServicesAdd = new ArrayList<>();
            settlementServicesAdd.add(ServiceRelationshipModel.SettlementServicesEnum.RPPS); //mandatory settlement service
            serviceAdd.setSettlementServices(settlementServicesAdd);
            serviceAdd.setCountries(Arrays.asList("USA")); //mandatory country
            list.add(serviceAdd);

            ServiceRelationshipModel serviceDelete = new ServiceRelationshipModel();
            serviceDelete.setRecordAction(ServiceRelationshipModel.RecordActionEnum.DELETE);
            serviceDelete.setBspId("013111"); // delete existing servive relationship
            list.add(serviceDelete);
        }
        return list;
    }

    public static BillerManagementRequest generateUpdateConvenienceFeeRequest(){
        BillerManagementRequest request = generateUpdateRequestBasic();

        List<ConvenienceFeeModel> list = new ArrayList<>();
        ConvenienceFeeModel convBank = new ConvenienceFeeModel();
        convBank.setRecordAction(ConvenienceFeeModel.RecordActionEnum.UPDATE);
        convBank.setPaymentType(ConvenienceFeeModel.PaymentTypeEnum.BANK); // mandatory payment type when convenience fee is 'Yes'
        convBank.setFlatFee("4.00"); //update flat fee to 4.00
        convBank.setPercentFee("0.00");
        list.add(convBank);

        ConvenienceFeeModel convCrdt = new ConvenienceFeeModel();
        convCrdt.setRecordAction(ConvenienceFeeModel.RecordActionEnum.ADD); //Add new CRDT fee
        convCrdt.setPaymentType(ConvenienceFeeModel.PaymentTypeEnum.CRDT);
        convCrdt.setPercentFee("0.02");
        convCrdt.setFlatFee("0.00");
        list.add(convCrdt);

        ConvenienceFeeModel convDebt = new ConvenienceFeeModel();
        convDebt.setRecordAction(ConvenienceFeeModel.RecordActionEnum.DELETE); //remove existing DEBT type
        convDebt.setPaymentType(ConvenienceFeeModel.PaymentTypeEnum.DEBT);
        convDebt.setPercentFee("0.02");
        convDebt.setFlatFee("0.00");
        list.add(convDebt);
        request.setConvenienceFees(list);

        return request;
    }

    public static BillerManagementRequest generateUpdateServiceAreaRequest(){
        BillerManagementRequest request = generateUpdateRequestBasic();

        ServiceAreaModel serviceAreaModel = new ServiceAreaModel();
        serviceAreaModel.setZipCodes("23456,78901"); //Update to this zip codes list
        request.setServiceAreas(serviceAreaModel);
        return request;
    }
}
