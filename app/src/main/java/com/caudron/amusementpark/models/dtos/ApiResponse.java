package com.caudron.amusementpark.models.dtos;

import java.util.List;

    public class ApiResponse {
        private List<String> mData;

        public List<String> getData() {
            return mData;
        }

        public void setData(List<String> data) {
            mData = data;
        }
    }

