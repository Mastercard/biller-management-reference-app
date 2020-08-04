package com.mastercard.developer.config;

import com.mastercard.developer.exception.ServiceException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;

@Getter
@Setter
@ConfigurationProperties(prefix = "mastercard.api")
public class MastercardProperties {

    private String basePath;

    private String consumerKey;

    private String keystoreAlias;

    private String keystorePassword;

    private Resource keyFile;


    @PostConstruct
    public void initialize() throws ServiceException {
        if (null == keyFile || StringUtils.isEmpty(consumerKey)) {
            throw new ServiceException(".p12 file or consumerKey does not exist, please add details in application.properties");
        }
    }
}
