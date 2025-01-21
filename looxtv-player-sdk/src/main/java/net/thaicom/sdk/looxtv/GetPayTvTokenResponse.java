package net.thaicom.sdk.looxtv;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPayTvTokenResponse {
    @Expose
    @SerializedName("code")
    private Integer code;

    @Expose
    @SerializedName("msg")
    private String msg;

    @Expose
    @SerializedName("token")
    private String token;

    @Expose
    @SerializedName("validFrom")
    private Integer validFrom;

    @Expose
    @SerializedName("validTo")
    private Integer validTo;

    @Expose
    @SerializedName("validDuration")
    private Integer validDuration;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getToken() {
        return token;
    }

    public Integer getValidFrom() {
        return validFrom;
    }

    public Integer getValidTo() {
        return validTo;
    }

    public Integer getValidDuration() {
        return validDuration;
    }
}

