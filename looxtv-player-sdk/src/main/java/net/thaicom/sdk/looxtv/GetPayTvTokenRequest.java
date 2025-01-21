package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.SerializedName;

public class GetPayTvTokenRequest {

    @SerializedName("id_channel")
    public int id_channel;

    @SerializedName("uuid")
    public String uuid;

    @SerializedName("quality")
    public String quality;

    @SerializedName("duration")
    public int duration;

    public GetPayTvTokenRequest(int id_channel, String uuid, String quality, int duration){
        this.id_channel=id_channel;
        this.uuid=uuid;
        this.quality=quality;
        this.duration=duration;
    }
}

