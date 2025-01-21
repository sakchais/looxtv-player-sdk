package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DynamicLinkResponse {

    @Expose
    @SerializedName("shortLink")
    public String shortLink;

    public String getShortLink(){
        return shortLink;
    }
}

