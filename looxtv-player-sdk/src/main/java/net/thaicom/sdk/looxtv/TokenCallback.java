package net.thaicom.sdk.looxtv;

public interface TokenCallback {
    void onSuccess(TokenInfo tokenInfo);
    void onFailure(Throwable t);
}
