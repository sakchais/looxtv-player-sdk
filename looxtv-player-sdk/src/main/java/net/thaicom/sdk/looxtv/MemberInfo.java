package net.thaicom.sdk.looxtv;

public class MemberInfo {
    boolean isExpired;
    int maxConcurrent;

    int remainDay;

    String groupTags;

    String expiredDate;

    public MemberInfo(){
        isExpired = false;
        maxConcurrent = 0;
        remainDay = 0;
        groupTags = "";
        expiredDate = "";

    }

    public boolean isAllowToPlay(){
        return remainDay>0 && maxConcurrent > 0;
    }

}
