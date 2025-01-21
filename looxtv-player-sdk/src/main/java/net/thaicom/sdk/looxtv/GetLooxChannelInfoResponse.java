package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class GetLooxChannelInfoResponse {
    @SerializedName("code")  @Expose
    private Integer code;
    @SerializedName("data")  @Expose
    public ArrayList<FeedModel> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ArrayList<FeedModel> getData() {
        return data;
    }

    public void setData(ArrayList<FeedModel> data) {
        this.data = data;
    }
}
