package com.mastercard.developer.service;

import com.mastercard.developer.exception.ServiceException;
import org.openapitools.client.model.BillerManagementResponse;

import java.util.List;

public interface BillerManagementService {

    List<BillerManagementResponse> manageBillers(String scenario) throws ServiceException;
}
