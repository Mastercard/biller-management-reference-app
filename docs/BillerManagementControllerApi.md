# BillerManagementControllerApi

All URIs are relative to *https://api.mastercard.com/billpay-exchange/biller-management*

Method | HTTP request | Description
------------- | ------------- | -------------
[**processBillerUploadPostRequestUsingPOST**](BillerManagementControllerApi.md#processBillerUploadPostRequestUsingPOST) | **POST** /billers | Add, edit or deactivate one or multiple Billers in Biller Pay Exchange


<a name="processBillerUploadPostRequestUsingPOST"></a>
# **processBillerUploadPostRequestUsingPOST**
> List&lt;BillerManagementResponse&gt; processBillerUploadPostRequestUsingPOST(billerManagementRequest)

Add, edit or deactivate one or multiple Billers in Biller Pay Exchange

### Example
```java
// Import classes:
//import com.mastercard.developer.biller_management_client.ApiException;
//import com.mastercard.developer.biller_management_client.api.BillerManagementControllerApi;


BillerManagementControllerApi apiInstance = new BillerManagementControllerApi();
List<BillerManagementRequest> billerManagementRequest = [{"action":"Add","effectiveDate":"04/01/2020","general":{"billerId":"0001234567","billerLogoUrl":"www.wandaauto.com","billerShortName":"Wanda Auto","convenienceFee":"Yes","estimatedPostingHour":"10","termsAndConditions":"Terms And Conditions"},"serviceRelationships":[{"bspId":"000123","serviceType":"BPX_CL_EB_PAYC"}],"cardPaymentSupport":{"cardEnabled":"Yes","cardNetworks":["MAST","VISA"],"cardTypes":["CRDT"]},"convenienceFees":[{"paymentType":"BANK","flatFee":"2.0","percentFee":"0.0"},{"paymentType":"CRDT","flatFee":"2.0","percentFee":"0.0"}],"serviceAreas":{"zipCodes":"63301,63302,63303"},"consumerAuths":[{"category":"IDEN","categoryLabel":"Iden Label","dataType":"A","maxLength":"2","notes":"Max length 2 for IDEN category"}]},{"action":"Update","effectiveDate":"05/01/2020","general":{"billerId":"0001234568","billerLogoUrl":"www.alexauto.com","termsAndConditions":"Updated Terms And Conditions"},"consumerAuths":[{"recordAction":"Add","category":"IDEN","categoryLabel":"Iden Label","dataType":"A","maxLength":"2","notes":"Max length 2 for IDEN category"}]},{"action":"Deactivate","effectiveDate":"06/01/2020","general":{"billerId":"0001234569"},"deactivation":{"deactivationReasonCode":"BCON","deactivationNotes":"Deactivate Biller 0001234568"}}]; // List<BillerManagementRequest> | Billers to be uploaded to Biller Pay Exchange
try {
    List<BillerManagementResponse> result = apiInstance.processBillerUploadPostRequestUsingPOST(billerManagementRequest);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BillerManagementControllerApi#processBillerUploadPostRequestUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **billerManagementRequest** | [**List&lt;BillerManagementRequest&gt;**](List.md)| Billers to be uploaded to Biller Pay Exchange |

### Return type

[**List&lt;BillerManagementResponse&gt;**](BillerManagementResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

