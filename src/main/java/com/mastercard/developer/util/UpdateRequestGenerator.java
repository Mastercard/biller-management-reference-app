package com.mastercard.developer.util;

import org.openapitools.client.model.*;

import java.util.ArrayList;
import java.util.List;

public class UpdateRequestGenerator {

    public static int BILLER_ID = 1971480623;

    public static List<BillerManagementRequest> generateRequest() {
        List<BillerManagementRequest> requestList = new ArrayList<>();

        // Update fields in general section
        requestList.add(generateUpdateGeneralRequest());

        // Edit existing servcie relationship
        requestList.add(generateUpdateEditServiceRelationshipRequest());

        // Delete existing service relationship and add new one
        requestList.add(generateUpdateAddDeleteServiceRelationshipRequest());

        // Edit existing consumer auth and Add a new one
        requestList.add(generateUpdateConsumerAuthenRequest());

        // Edit existing card payment
        requestList.add(generateUpdateCardPaymentRequest());

        // Edit service area
        requestList.add(generateUpdateServiceAreaRequest());

        // Edit Convenience Fee
        requestList.add(generateUpdateConvenienceFeeRequest());

        return requestList;
    }

    public static BillerManagementRequest generateUpdateReuqestBasic(){
        BillerManagementRequest request = new BillerManagementRequest();
        request.setAction("Update"); // required field
        String effectiveDate = DateUtil.getNextValidDate(); // valid effective date can't be weekends, or BPX Restricted Holidays
        request.setEffectiveDate(effectiveDate);

        GeneralModel general = new GeneralModel();
        general.setBillerId(String.valueOf(BILLER_ID++)); //existing biller id that needs updates
        request.setGeneral(general);
        return request;
    }

    public static BillerManagementRequest generateUpdateGeneralRequest(){
        BillerManagementRequest request = generateUpdateReuqestBasic(); //Only set value for the fields you want to update
        request.getGeneral().setBillerLogoUrl("www.updatedURL.com");
        request.getGeneral().setBillerShortName("Updated biller name");
        return request;
    }

    public static BillerManagementRequest generateUpdateEditServiceRelationshipRequest(){
        BillerManagementRequest request = generateUpdateReuqestBasic();
        request.setServiceRelationships(generateUpdateServiceRelationshipModelList("Update"));
        return request;
    }

    public static BillerManagementRequest generateUpdateAddDeleteServiceRelationshipRequest(){
        BillerManagementRequest request = generateUpdateReuqestBasic();
        request.setServiceRelationships(generateUpdateServiceRelationshipModelList("Add"));
        return request;
    }

    public static BillerManagementRequest generateUpdateConsumerAuthenRequest(){
        BillerManagementRequest request = generateUpdateReuqestBasic();

        List<ConsumerAuthenticationModel> list = new ArrayList<>();
        // The combination of category and category label is key field,
        // always need to add these 2 fields when updating, it shoud match to an existing record
        ConsumerAuthenticationModel conEdit = new ConsumerAuthenticationModel();
        conEdit.setRecordAction("Update");
        conEdit.setCategory("CODE");
        conEdit.setCategoryLabel("Code Label");
        conEdit.setMaxLength("10"); //update max length field
        conEdit.setNotes("Update max length");
        list.add(conEdit);

        ConsumerAuthenticationModel conAdd = new ConsumerAuthenticationModel();
        conAdd.setRecordAction("Add");
        conAdd.setCategory("IDEN");
        conAdd.setCategoryLabel("IDEN Label");
        conAdd.setDataType("A");
        conAdd.setMaxLength("10");
        conAdd.setNotes("Add new consumer auth");
        list.add(conAdd);

        request.setConsumerAuths(list);
        return request;
    }

    public static BillerManagementRequest generateUpdateCardPaymentRequest(){
        BillerManagementRequest request = generateUpdateReuqestBasic();

        CardPaymentModel cardPaymentModel = new CardPaymentModel();
        List<String> cardNetworks = new ArrayList<>();
        cardNetworks.add("MAST"); // only keep MAST as card network
        cardPaymentModel.setCardNetworks(cardNetworks);

        List<String> cardTypes = new ArrayList<>();
        cardTypes.add("CRDT"); //only keep CRDT as card types
        cardPaymentModel.setCardTypes(cardTypes);

        request.setCardPaymentSupport(cardPaymentModel);
        return request;
    }

    public static List<ServiceRelationshipModel> generateUpdateServiceRelationshipModelList(String recordAction){
        List<ServiceRelationshipModel> list = new ArrayList<>();
        if(recordAction.equalsIgnoreCase("Update")){
            // BSP ID is the key field, if BSP ID doesn't change, then it's an 'Update' action
            ServiceRelationshipModel serviceUpdate = new ServiceRelationshipModel();
            serviceUpdate.setRecordAction("Update");
            serviceUpdate.setBspId("013001");
            serviceUpdate.setServiceType("BPX_CL_EB"); // downgrade service type to BPX_CL_EB
            List<String> settlementServicesUpdate = new ArrayList<>();
            settlementServicesUpdate.add("RPPS"); //mandatory settlement service
            serviceUpdate.setSettlementServices(settlementServicesUpdate);
            list.add(serviceUpdate);
        } else{
            //if BSP ID changed, then it's an 'Add' new and "Delete" old action
            ServiceRelationshipModel serviceAdd = new ServiceRelationshipModel();
            serviceAdd.setRecordAction("Add");
            serviceAdd.setBspId("013111"); // new servive relationship
            serviceAdd.setServiceType("BPX_CL_EB_PAYC");
            List<String> settlementServicesAdd = new ArrayList<>();
            settlementServicesAdd.add("RPPS"); //mandatory settlement service
            serviceAdd.setSettlementServices(settlementServicesAdd);
            list.add(serviceAdd);

            ServiceRelationshipModel serviceDelete = new ServiceRelationshipModel();
            serviceDelete.setRecordAction("Delete");
            serviceDelete.setBspId("013111"); // new servive relationship
            serviceDelete.setServiceType("BPX_CL_EB_PAYC");
            list.add(serviceDelete);
        }
        return list;
    }

    public static BillerManagementRequest generateUpdateConvenienceFeeRequest(){
        BillerManagementRequest request = generateUpdateReuqestBasic();

        List<ConvenienceFeeModel> list = new ArrayList<>();
        ConvenienceFeeModel convBank = new ConvenienceFeeModel();
        convBank.setRecordAction("Update");
        convBank.setPaymentType("BANK"); // mandatory payment type when convenience fee is 'Yes'
        convBank.setFlatFee("4.00"); //update flat fee to 4.00
        convBank.setPercentFee("0.00");
        list.add(convBank);

        ConvenienceFeeModel convCrdt = new ConvenienceFeeModel();
        convCrdt.setRecordAction("Add"); //Add new CRDT fee
        convCrdt.setPaymentType("CRDT");
        convCrdt.setPercentFee("0.02");
        convCrdt.setFlatFee("0.00");
        list.add(convCrdt);

        ConvenienceFeeModel convDebt = new ConvenienceFeeModel();
        convDebt.setRecordAction("Delete"); //remove existing DEBT type
        convDebt.setPaymentType("DEBT");
        convDebt.setPercentFee("0.02");
        convDebt.setFlatFee("0.00");
        list.add(convDebt);
        request.setConvenienceFees(list);

        return request;
    }

    public static BillerManagementRequest generateUpdateServiceAreaRequest(){
        BillerManagementRequest request = generateUpdateReuqestBasic();

        ServiceAreaModel serviceAreaModel = new ServiceAreaModel();
        serviceAreaModel.setZipCodes("23456,78901"); //Update to this zip codes list
        request.setServiceAreas(serviceAreaModel);
        return request;
    }
}
