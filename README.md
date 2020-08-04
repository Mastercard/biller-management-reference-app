# Reference Implementation for Mastercard Biller Management Service

[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/Mastercard/biller-management-reference/blob/master/LICENSE)

## Table of Contents
- [Overview](#overview)
  * [Compatibility](#compatibility)
  * [References](#references)
- [Usage](#usage)
  * [Prerequisites](#prerequisites)
  * [Configuration](#configuration)
  * [Integrating with OpenAPI Generator](#integrating-with-openapi-generator)
  * [Build and Execute](#build-and-execute)
  * [Install as Dependency](#install-as-dependency)
- [Use Cases](#use-cases)
- [API Reference](#api-reference)
  * [Authorization](#authorization)
  * [Request Examples](#request-examples)
  * [Request and Response Models](#request-response-models)
  * [Recommendation](#recommendation)
- [Author](#author)
- [Support](#support)
- [License](#license)

## Overview <a name="overview"></a>
This is a reference application to demonstrate how Biller Management API can be used for the supported operations. Please see here for details on the API: [Mastercard Developers](https://developer.mastercard.com/documentation/biller-management/1).
This application illustrates connecting to the Biller Management API. To call these APIs, consumer key and .p12 files are required from your [Mastercard Developers](https://developer.mastercard.com/dashboard) project.

### Compatibility <a name="compatibility"></a>
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later

### References <a name="references"></a>
* [Mastercard’s OAuth Signer library](https://github.com/Mastercard/oauth1-signer-java)
* [Using OAuth 1.0a to Access Mastercard APIs](https://developer.mastercard.com/platform/documentation/using-oauth-1a-to-access-mastercard-apis/)

## Usage <a name="usage"></a>
### Prerequisites <a name="prerequisites"></a>
* [Mastercard Developers Account](https://developer.mastercard.com/dashboard) with access to Biller Management API
* A text editor or IDE
* [Spring Boot 2.2+](https://spring.io/projects/spring-boot)
* [Apache Maven 3.3+](https://maven.apache.org/download.cgi)
* Set up the `JAVA_HOME` environment variable to match the location of your Java installation.

### Configuration <a name="configuration"></a>
* Create an account at [Mastercard Developers](https://developer.mastercard.com/account/sign-up).  
* Create a new project and add `Biller Management` API to your project.   
* Configure project and download signing key. It will download the zip file.  
* Select `.p12` files from zip and copy it to `src/main/resources` in the project folder.
* Open `${project.basedir}/src/main/resources/application.properties` and configure below parameters.
* Update the following keys in application.properties file.
	- *mastercard.api.consumer-key*: This can be found in the project you created on developerZone
	- *mastercard.api.key-file*: Path where you saved your certs i.e., .p12 file you received while creating a project
	- *mastercard.api.keystore-password*: This is the password you get with Sandbox cert.
	- *mastercard.api.keystore-alias*: This is the alias you get with Sandbox cert.

* Example:

```
	mastercard.benefits.ref.app.url=https://sandbox.api.mastercard.com/billpay-exchange/biller-management
	mastercard.api.consumer-key=Abcdfefgjhilklmnopqrstuvwxyz-dxcq_zD7IiPa0df175e!22a7fddba5
	mastercard.api.key-file=C:\path\provided.p12
	mastercard.api.keystore-password=pwd
	mastercard.api.keystore-alias=alias
```
### Integrating with OpenAPI Generator <a name="integrating-with-openapi-generator"></a>
[OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) generates API client libraries from [OpenAPI Specs](https://github.com/OAI/OpenAPI-Specification). 
It provides generators and library templates for supporting multiple languages and frameworks.

See also:
* [OpenAPI Generator (maven Plugin)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-maven-plugin)
* [OpenAPI Generator (executable)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-cli)
* [CONFIG OPTIONS for java](https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/java.md)

#### OpenAPI Generator Plugin Configuration
```xml
<!-- https://mvnrepository.com/artifact/org.openapitools/openapi-generator-maven-plugin -->
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>${openapi-generator.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/biller-management-service.yaml</inputSpec>
                <generatorName>java</generatorName>
                <library>okhttp-gson</library>
                <generateApiTests>false</generateApiTests>
                <generateModelTests>false</generateModelTests>
                <configOptions>
                    <sourceFolder>src/gen/java/main</sourceFolder>
                    <dateLibrary>java8</dateLibrary>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```
For more information on how this client generator works please consult the official [Github repository](https://github.com/OpenAPITools/openapi-generator)

#### Generating The API Client Sources
Now that you have all the dependencies you need, you can generate the sources. To do this, use one of the following two methods:

`Using IDE`
* **Method 1**<br/>
  In IntelliJ IDEA, open the Maven window **(View > Tool Windows > Maven)**. Click the icons `Reimport All Maven Projects` and `Generate Sources and Update Folders for All Projects`

* **Method 2**<br/>
  In the same menu, navigate to the commands **({Project name} > Lifecycle)**, select `clean` and `compile` then click the icon `Run Maven Build`. 

`Using Terminal`
* Navigate to the root directory of the project within a terminal window and execute `mvn clean compile` command.

### Build and Execute <a name="build-and-execute"></a>
Once you’ve added the correct properties, we can build the application. We can do this by navigating to the project’s base directory from the terminal and running the following command

`mvn clean install`

When the project builds successfully you can then run the following command to start the project

`java -jar target/biller-management-client-1.0.0.jar`

### Install as Dependency <a name="install-as-denpendecy"></a>
To use this API client as dependency library in your own project:

* **Maven users**<br/>
  Add this dependency to your project's POM:
    ```xml
    <dependency>
      <groupId>com.mastercard.developer</groupId>
      <artifactId>biller-management-client</artifactId>
      <version>1.0.0</version>
      <scope>compile</scope>
    </dependency>
    ```
* **Gradle users**<br/>
  Add this dependency to your project's build file:
    
    ```groovy
    compile "org.openapitools:biller-management-client:1.0.0"
    ```

* **Others**<br/>
    At first generate the JAR by executing:
    
    ```shell
    mvn clean package
    ```
    Then manually install the following JARs:
    
    * `target/biller-management-client-1.0.0.jar`
    * `target/lib/*.jar`


## Use Cases <a name="use-cases"></a>
##### Case 1: [Onboard new Billers](https://developer.mastercard.com/documentation/biller-management/1#add-biller-example)
  - Biller Service Providers (BSP) can add their new billers into Billpay Exchange via the Biller Management API.
  - Sample request payload can be found: [Add Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-add.json)
  - Request "action" field will be "add"
  - Command line examples `java -jar target/biller-management-client-1.0.0.jar Add`.

##### Case 2: [Edit Existing Billers](https://developer.mastercard.com/documentation/biller-management/1#update-biller-example)
  - Biller Service Providers (BSP) can edit billers that belong to their profile via the Biller Management API.
  - Sample request payload can be found: [Update Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-update.json)
  - Request "action" field will be "update"
  - Command line examples `java -jar target/biller-management-client-1.0.0.jar Update`.

##### Case 3: [Deactivate Existing Billers](https://developer.mastercard.com/documentation/biller-management/1#deactivate-biller-example)
  - Biller Service Providers (BSP) can deactivate billers that belong to their profile via the Biller Management API.
  - Sample request payload can be found: [Deactivate Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-deactivate.json)
  - Request "action" field will be "deactivate"
  - Command line examples `java -jar target/biller-management-client-1.0.0.jar Deactivate`.

##### Case 4: [Multiple Biller Changes](https://developer.mastercard.com/documentation/biller-management/1#multiple-biller-example)
  - Biller Service Providers (BSP) can add, update and deactivate billers that belong to their profile in single request via the Biller Management API.
  - Sample request payload can be found: [Multiple Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-all.json)
  - Request "action" field value varies
  - Command line examples `java -jar target/biller-management-client-1.0.0.jar`. No args needed.

##### Case 5: [Error Handling](https://developer.mastercard.com/documentation/biller-management/1#error-codes)
  - The operation can fail for various reasons like formatting, field length exceeds, etc.
  - This use case just shows one of the example of such failures.
  - For the complete list of application specific error codes, refer to [Application Error Codes](https://developer.mastercard.com/documentation/biller-management/1#error-codes).
  - Also refer to model class [Errors](docs/Errors.md) for field level information.

## API Reference <a name="api-reference"></a>

To develop a client application that consumes a RESTful Biller Management API with Spring Boot, refer below documentation.

Class | URL Endpoint | HTTP Method | Request | Response | Description
------------ | ------------ | ------------- | ------------- | ------- | --------
*BillerManagementControllerApi* |`/billers`| **POST**| [BillerManagementRequest](docs/BillerManagementRequest.md) | [BillerManagementResponse](docs/BillerManagementResponse.md) | Add, edit or deactivate one or multiple Billers in Biller Pay Exchange

### Authorization <a name="authorization"></a>
The `com.mastercard.developer.interceptors` package will provide you with some request interceptor classes you can use when configuring your API client. These classes will take care of adding the correct `Authorization` header before sending the request.

### Request Examples <a name="request-examples"></a>
You can change the default input passed to APIs, modify values in following files,
* [Add Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-add.json)
* [Update Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-update.json)
* [Deactivate Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-deactivate.json)
* [Multiple Billers](https://github.com/Mastercard/biller-management-reference/blob/master/src/main/resources/payloads/biller-management-all.json)

### Request and Response Models <a name="request-response-models"></a>

 - [BillerManagementRequest](docs/BillerManagementRequest.md)
 - [BillerManagementResponse](docs/BillerManagementResponse.md)
 - [CardPaymentModel](docs/CardPaymentModel.md)
 - [ConsumerAuthenticationModel](docs/ConsumerAuthenticationModel.md)
 - [ConvenienceFeeModel](docs/ConvenienceFeeModel.md)
 - [DeactivationModel](docs/DeactivationModel.md)
 - [ErrorDetail](docs/ErrorDetail.md)
 - [Errors](docs/Errors.md)
 - [ErrorsList](docs/ErrorsList.md)
 - [GeneralModel](docs/GeneralModel.md)
 - [ServiceAreaModel](docs/ServiceAreaModel.md)
 - [ServiceRelationshipModel](docs/ServiceRelationshipModel.md)
 - [UserErrorModel](docs/UserErrorModel.md)

### Recommendation <a name="recommendation"></a>
It is recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author <a name="author"></a>

Bill_Pay_Development_Support@mastercard.com

## Support <a name="support"></a>
If you would like further information, please send an email to apisupport@mastercard.com


## License <a name="license"></a>
Copyright 2020 Mastercard
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
