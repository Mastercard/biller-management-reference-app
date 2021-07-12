

# ServiceRelationshipModel

Biller Service Relationships Detail Model,

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**recordAction** | **String** | Record Action, avaliable values are: Add, Delete and Update, only required when Biller action is &#39;Update&#39; |  [optional]
**bspId** | **String** | BSP ID, 6 digit numeric value | 
**serviceType** | **String** | Biller Service Type, avaliable values are: BPX_CL_EB_PAYC, BPX_CL_EB, BPX_CL | 
**settlementServices** | **List&lt;String&gt;** | Settlement Services, avaliable values are: RPPS, MPGS, PDSS; RPPS is mandatory |  [optional]
**countries** | **List&lt;String&gt;** | Countries, alpha3 country code |  [optional]



