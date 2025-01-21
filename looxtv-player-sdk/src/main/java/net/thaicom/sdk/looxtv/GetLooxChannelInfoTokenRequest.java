package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetLooxChannelInfoTokenRequest {

    @SerializedName("tag")
    public ArrayList<String> tag;
    public GetLooxChannelInfoTokenRequest(ArrayList<String> groupsTag){
        this.tag = groupsTag;
    }

}
