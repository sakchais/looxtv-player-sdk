package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.SerializedName;

public class GetInfoByDeviceIDRequest {

    @SerializedName("deviceId")
    public String deviceId;

    public GetInfoByDeviceIDRequest(String deviceId) {
        this.deviceId = deviceId;

    }
}
