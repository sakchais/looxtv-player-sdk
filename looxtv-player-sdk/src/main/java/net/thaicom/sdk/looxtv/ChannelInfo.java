package net.thaicom.sdk.looxtv;

public class ChannelInfo {
    int chId;
    String urlLive;
//    String chName;

    public ChannelInfo(){
        chId=0;
        urlLive="";
    }

    public int getChId() {
        return chId;
    }

    public String getUrlLive() {
        return urlLive;
    }


}
