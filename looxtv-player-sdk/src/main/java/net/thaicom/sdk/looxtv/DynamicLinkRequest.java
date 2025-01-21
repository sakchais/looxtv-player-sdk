package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.SerializedName;

public class DynamicLinkRequest {

    @SerializedName("dynamicLinkInfo")
    public DynamicLinkInfo dynamicLinkInfo;

    @SerializedName("suffix")
    public Suffix suffix;

    class DynamicLinkInfo {
        @SerializedName("domainUriPrefix")
        public String domainUriPrefix;

        @SerializedName("link")
        public String link;

    }

    class Suffix{
        @SerializedName("option")
        public String option;
    }

    public DynamicLinkRequest(String url) {
        dynamicLinkInfo = new DynamicLinkInfo();
        dynamicLinkInfo.domainUriPrefix="https://looxtvreg.page.link";
        dynamicLinkInfo.link=url;

        suffix=new Suffix();
        suffix.option="SHORT";


    }

}

