package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by anupamchugh on 09/01/17.
 */

public class GetMemberPaidStatusResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    public ArrayList<PaidStatusModel> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ArrayList<PaidStatusModel> getData() {
        return data;
    }

    public void setData(ArrayList<PaidStatusModel> data) {
        this.data = data;
    }


}

