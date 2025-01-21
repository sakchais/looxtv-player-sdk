package net.thaicom.sdk.looxtv;

import java.util.ArrayList;

public interface ConcurrentCallback {
    void onSuccess(int numConcurrent);
    void onFailure(Throwable t);
}
