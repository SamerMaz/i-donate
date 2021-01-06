package com.example.i_donate.DataModels;

import java.util.Date;

public class RequestDataModel {
        private String details;
        private Date date;
        private String phone;
        RequestDataModel(){}


        public RequestDataModel(String details,Date date, String phone) {
            this.details = details;
            this.date = date;
            this.phone = phone;
        }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

