package com.bootaction.readingList;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {
    private String associateId;

    public String getAssociateId(){
        return associateId;
    }

}
