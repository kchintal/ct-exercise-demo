package com.etsy.shop.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:demo-configuration.properties")
public class DemoConfiguration {

    @Value("${shopsApiUrl}")
    private String shopsApiUrl;

    @Value("${listingsApiUrl}")
    private String listingsApiUrl;

    public String getShopsApiUrl() {
        return shopsApiUrl;
    }

    public void setShopsApiUrl(String shopsApiUrl) {
        this.shopsApiUrl = shopsApiUrl;
    }

    public String getListingsApiUrl() {
        return listingsApiUrl;
    }

    public void setListingsApiUrl(String listingsApiUrl) {
        this.listingsApiUrl = listingsApiUrl;
    }
}
