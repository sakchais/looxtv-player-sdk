package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.SerializedName;

public class GetMemberPaidStatusRequest {
    @SerializedName("id_firebase_uid")
    public String id_firebase_uid;


    public GetMemberPaidStatusRequest(String id_firebase_uid) {
        this.id_firebase_uid = id_firebase_uid;
    }

}
