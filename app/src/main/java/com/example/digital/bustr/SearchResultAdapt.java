package com.example.digital.bustr;

/**
 * Created by Digital on 2/14/2018.
 */

public class SearchResultAdapt {

    public class SearchResult {
        private String busNum, busType, time;

        public SearchResult() {
        }

        public SearchResult(String busNum, String busType, String time) {
            this.busNum = busNum;
            this.busType = busType;
            this.time = time;
        }

        public String getBusNum() {
            return busNum;
        }

        public void setBusNum(String name) {
            this.busNum = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getBusType() {
            return busType;
        }

        public void setGenre(String busType) {
            this.busType = busType;
        }
    }
}
