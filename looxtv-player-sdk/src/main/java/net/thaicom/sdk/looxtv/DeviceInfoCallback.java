package net.thaicom.sdk.looxtv;

public interface DeviceInfoCallback {
    void onSuccess(DeviceInfo deviceInfo);
    void onFailure(Throwable t);
}
