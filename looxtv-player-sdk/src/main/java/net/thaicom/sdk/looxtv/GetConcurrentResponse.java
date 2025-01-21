package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetConcurrentResponse {
    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    public ArrayList<ConcurrentInfo> data;


    public Integer getCode() {
        return code;

    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<ConcurrentInfo> getData() {
        return data;
    }
}
