package com.apiexample_sep.dto;

import java.util.Date;

public class ErrorDto {

    private Date date;
    private String massage;
    private String url;

    public Date getDate() {
        return date;
    }

    public String getMassage() {
        return massage;
    }

    public String getUrl() {
        return url;
    }

    public ErrorDto(Date date, String massage, String url) {
        this.date = date;
        this.massage = massage;
        this.url = url;


    }
}
