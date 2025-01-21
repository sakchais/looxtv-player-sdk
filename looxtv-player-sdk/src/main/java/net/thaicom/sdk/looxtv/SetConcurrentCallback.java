package net.thaicom.sdk.looxtv;

import java.util.ArrayList;

public interface SetConcurrentCallback {
    void onSuccess();
    void onFailure(Throwable t);
}
