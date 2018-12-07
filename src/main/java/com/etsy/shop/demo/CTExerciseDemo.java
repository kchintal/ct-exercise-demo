package com.etsy.shop.demo;

import com.etsy.shop.demo.object.GetAllShopListings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.*;

public class CTExerciseDemo {

    private static String[] shopIdsArray =  {"19083267","19086361","19086255","19086227","19089303"};
    private static String outputDir = "/Users/kchintalapati/data/output/";

    public static void main(String [] args) {

        List<String> shopIdsList = Arrays.asList(shopIdsArray);
        ShopResource shopResource = new ShopResource();
        //shopResource.findAllShops();

        shopIdsList.forEach(shopId -> processShopListingsNew(shopResource, shopId));
    }

    private static void processShopListingsNew(ShopResource shopResource, String shopId) {

        List<Integer> listingIds = new ArrayList<Integer>();
        List<Integer> previousListingIds = new ArrayList<Integer>();

        try {

            ResponseEntity<String> shopListings = shopResource.findAllShopListingsActive(shopId);

            ObjectMapper mapper = new ObjectMapper();
            GetAllShopListings getAllShopListings = mapper.readValue(shopListings.getBody(), GetAllShopListings.class);

            File shopListingsFile = new File(outputDir + shopId + ".txt");

            if(!shopListingsFile.exists()) {
                // Initially Synchronize the shop listings to a file.
                mapper.writeValue(shopListingsFile, getAllShopListings);
                getAllShopListings.getListings().forEach(listing -> System.out.println(" + added listing "+listing.getListing_id()+ " \""+listing.getTitle()+"\""));
            } else {

                System.out.println(" Shop ID "+shopId);
                GetAllShopListings previousGetAllShopListings = mapper.readValue(shopListingsFile, GetAllShopListings.class);

                // populate List of listingIds, previousListingIds
                getAllShopListings.getListings().forEach(listing -> listingIds.add(listing.getListing_id()));
                previousGetAllShopListings.getListings().forEach(listing -> previousListingIds.add(listing.getListing_id()));

                // check if listing removed
                previousGetAllShopListings.getListings().forEach(listing -> {
                    if(!listingIds.contains(listing.getListing_id())) {
                        System.out.println(" - removed listing "+listing.getListing_id()+" \""+ listing.getTitle()+ "\"");
                    }
                });

                // check if listing added
                getAllShopListings.getListings().forEach(listing -> {
                    if(!previousListingIds.contains(listing.getListing_id())) {
                        System.out.println(" + added listing "+listing.getListing_id()+ " \""+listing.getTitle()+"\"");
                    }
                });

                if(previousListingIds.containsAll(listingIds) && listingIds.containsAll(previousListingIds)) {
                    System.out.println(" No Changes since last sync. ");
                }

                mapper.writeValue(shopListingsFile, getAllShopListings);
            }

        } catch (Exception ex) {
            System.out.println(" Exception in processShopListings: "+ex.getMessage());
        }
    }

//    private static void processShopListings(ShopResource shopResource, String shopId) {
//        BufferedWriter bufferedWriter = null;
//
//        try {
//
//            ResponseEntity<String> shopListings = shopResource.findAllShopListingsActive(shopId);
//
//            Map<String, Object> shopListingsMap = new Gson().fromJson(shopListings.getBody(), Map.class);
//
//            Object shopListingResultsObj = shopListingsMap.get("results");
//
//            if(shopListingResultsObj != null && shopListingResultsObj instanceof ArrayList) {
//                List<Object> shopListingsList = (List<Object>)shopListingResultsObj;
//
//                if(shopListingsList != null && shopListingsList.size() >0) {
//                    for(Object shopListingObj: shopListingsList) {
//                        //JsonObject shopListing = new Gson().fromJson(shopListingObj.toString(), JsonObject.class);
//                        System.out.println(" ================ CURRENT LISTING ID: "+shopListingObj.toString());
//                    }
//                }
//            }
//
//
//            File shopListingsFile = new File(outputDir + shopId + ".txt");
//
//            if (!shopListingsFile.exists()) {
//                shopListingsFile.createNewFile();
//            } else {
//                BufferedReader bufferedReader = new BufferedReader(new FileReader(shopListingsFile));
//                String previousShopListingsStr = "", newLine;
//                while ((newLine = bufferedReader.readLine()) != null) {
//                    previousShopListingsStr  = previousShopListingsStr + newLine;
//                }
//
//                Map<String, Object> previousShopListings = new Gson().fromJson(previousShopListingsStr, Map.class);
//
//                Object result = previousShopListings.get("results");
//
//                if(result != null && result instanceof ArrayList) {
//                    List<Object> previousListingsObj = (List<Object>)result;
//
//                }
//
//
//                if(result != null) {
//                    List<Object> results = new Gson().fromJson(result.toString(), ArrayList.class);
//
//                    System.out.println(" results: "+results);
//                }
//            }
//
//            Writer writer = new FileWriter(shopListingsFile);
//            bufferedWriter = new BufferedWriter(writer);
//
//            bufferedWriter.write(shopListings.getBody());
//
//
//        } catch (IOException ex) {
//            System.out.println(" IOException in processShopListings: "+ex.getMessage());
//
//        }catch (Exception ex) {
//            System.out.println(" Exception in processShopListings: "+ex.getMessage());
//        } finally {
//            try {
//
//                if (bufferedWriter != null) {
//                    bufferedWriter.close();
//                }
//            } catch (Exception ex) {
//                System.out.println(" Exception in finally processShopListings: "+ex.getMessage());
//            }
//        }
//    }
}
