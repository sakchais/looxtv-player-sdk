package net.thaicom.sdk.looxtv;

public interface DynamicLinkCallback {
    void onSuccess(String shortLink);

    void onFailure(Throwable t);

}
