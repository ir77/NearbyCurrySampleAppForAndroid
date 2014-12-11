package com.uraroji.android.nearbycurry;

import java.util.List;

public class ApiGourmetResponse {

    private Results results;

    public Results getResults() {
        return results;
    }

    public static class Results {
        private String api_version;
        private int results_available;
        private int results_returned;
        private int results_start;
        private List<Shop> shop;

        public String getApiVersion() {
            return api_version;
        }

        public int getResultsAvailable() {
            return results_available;
        }

        public int getResults_returned() {
            return results_returned;
        }

        public int getResultsStart() {
            return results_start;
        }

        public List<Shop> getShop() {
            return shop;
        }
    }

    public static class Shop {
        private String id;
        private String name;
        private String logo_image;
        private String name_kana;
        private String address;
        private String station_name;
        private double lat;
        private double lng;
        private String open;
        private String close;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLogoImage() {
            return logo_image;
        }

        public String getNameKana() {
            return name_kana;
        }

        public String getAddress() {
            return address;
        }

        public String getStationName() {
            return station_name;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public String getOpen() {
            return open;
        }

        public String getClose() {
            return close;
        }
    }
}
