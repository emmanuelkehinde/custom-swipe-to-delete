package com.emmanuelkehinde.custom_swipe_to_delete.model;

import org.json.JSONArray;

public class RequestResult {

    private boolean status;
    private JSONArray data;
    private String message;

    public RequestResult() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
