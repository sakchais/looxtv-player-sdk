package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.SerializedName;

public class GetConcurrentRequest {
    @SerializedName("fuid")
    public String fuid;

    public GetConcurrentRequest(String fuid) {
        this.fuid = fuid;
    }
}
