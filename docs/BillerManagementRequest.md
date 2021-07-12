

# BillerManagementRequest

Request Model class for Biller Management API

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**action** | **String** | Biller Action, avaliable values are: Add, Update, Deactivate | 
**effectiveDate** | **String** | Effective Date, format MM/DD/YYYY | 
**general** | [**GeneralModel**](GeneralModel.md) |  | 
**serviceRelationships** | [**List&lt;ServiceRelationshipModel&gt;**](ServiceRelationshipModel.md) | Service Relationship List, currently only allow 1 service relationship per Biller | 
**cardPaymentSupport** | [**CardPaymentModel**](CardPaymentModel.md) |  |  [optional]
**convenienceFees** | [**List&lt;ConvenienceFeeModel&gt;**](ConvenienceFeeModel.md) | Convenience Fee List, only required when convenience fee flag in General is set to &#39;Yes&#39; |  [optional]
**serviceAreas** | [**ServiceAreaModel**](ServiceAreaModel.md) |  | 
**consumerAuths** | [**List&lt;ConsumerAuthenticationModel&gt;**](ConsumerAuthenticationModel.md) | Consumer Authentication List, maximum 3 consumer authentications allowed per Biller |  [optional]
**deactivation** | [**DeactivationModel**](DeactivationModel.md) |  |  [optional]



