package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.SerializedName;

public class SetConcurrentRequest {
    @SerializedName("fuid")
    public String fuid;

    @SerializedName("did")
    public String did;

    @SerializedName("online")
    public boolean online;

    @SerializedName("playing")
    public boolean playing;

    @SerializedName("timestamp")
    public long timestamp;


    public SetConcurrentRequest(String fuid, String did, boolean online, boolean playing, long timestamp) {
        this.fuid = fuid;
        this.did = did;
        this.online = online;
        this.playing = playing;
        this.timestamp = timestamp; //System.currentTimeMillis()
    }
}
