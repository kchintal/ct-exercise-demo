package com.etsy.shop.demo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ShopResource {

    private static final Logger logger = LogManager.getLogger(ShopResource.class);

    @Autowired
    private DemoConfiguration demoConfiguration;

    @RequestMapping(value = "/shops", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> findAllShops() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.set("cache-control", "no-cache");

        HttpEntity<String> request = new HttpEntity<String>(header);
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.exchange("https://openapi.etsy.com/v2/shops?api_key=dds0w59jy3m6p9aunvmepxms", HttpMethod.GET, request, String.class);
            //logger.info(" findAllShops API response: "+response.getBody());

        } catch (Exception ex) {
            System.out.println(" Exception occurred while calling findAllShops API: "+ ex.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/shops/{shopId}/listings/active", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> findAllShopListingsActive(@PathVariable("shopId") String shopId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.set("cache-control", "no-cache");

        HttpEntity<String> request = new HttpEntity<String>(header);
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.exchange("https://openapi.etsy.com/v2/shops/"+shopId+"/listings/active?sort_on=created&api_key=dds0w59jy3m6p9aunvmepxms", HttpMethod.GET, request, String.class);
            //logger.info(" getShop API response: "+response.getBody());

        } catch (Exception ex) {
            System.out.println(" Exception occurred while calling findAllShops API: "+ ex.getMessage());
        }
        return response;
    }
}
