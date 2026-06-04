package com.caio.fintrack.exception.globalException;

import org.springframework.http.HttpStatus;

public class RestErrorMessage {

    private String title;
    private Integer status;
    private String detail;

    public RestErrorMessage(String title, Integer status, String detail) {
        this.title = title;
        this.status = status;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
