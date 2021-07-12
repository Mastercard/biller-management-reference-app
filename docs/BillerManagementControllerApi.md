# BillerManagementControllerApi

All URIs are relative to *https://api.mastercard.com/billpay-exchange/biller-management*

Method | HTTP request | Description
------------- | ------------- | -------------
[**processBillerUploadPostRequestUsingPOST**](BillerManagementControllerApi.md#processBillerUploadPostRequestUsingPOST) | **POST** /billers | Add, Update or Deactivate one or multiple Billers in Biller Pay Exchange


<a name="processBillerUploadPostRequestUsingPOST"></a>
# **processBillerUploadPostRequestUsingPOST**
> List&lt;BillerManagementResponse&gt; processBillerUploadPostRequestUsingPOST(billerManagementRequest)

Add, Update or Deactivate one or multiple Billers in Biller Pay Exchange

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BillerManagementControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.mastercard.com/billpay-exchange/biller-management");

    BillerManagementControllerApi apiInstance = new BillerManagementControllerApi(defaultClient);
    List<BillerManagementRequest> billerManagementRequest = Arrays.asList(); // List<BillerManagementRequest> | Billers to be uploaded to Biller Pay Exchange
    try {
      List<BillerManagementResponse> result = apiInstance.processBillerUploadPostRequestUsingPOST(billerManagementRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BillerManagementControllerApi#processBillerUploadPostRequestUsingPOST");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **billerManagementRequest** | [**List&lt;BillerManagementRequest&gt;**](BillerManagementRequest.md)| Billers to be uploaded to Biller Pay Exchange |

### Return type

[**List&lt;BillerManagementResponse&gt;**](BillerManagementResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Successfully processed the list of Billers |  -  |
**403** | User Access Denied |  -  |
**500** | Internal Server Error, please contact Billpay Exchange Support Team |  -  |

