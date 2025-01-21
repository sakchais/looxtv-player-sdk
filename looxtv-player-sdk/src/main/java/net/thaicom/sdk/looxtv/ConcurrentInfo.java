package net.thaicom.sdk.looxtv;

import android.util.TimeUtils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

public class ConcurrentInfo {
    private final long EXPIRE_TIME = TimeUnit.HOURS.toMillis(2);
    boolean online;
    boolean playing;
    String id;

    long timestamp;

    public boolean isPlaying(String deviceID) {
        Log.d("ConcurrentInfo", "Device: " + id + ", timestamp: " + timestamp + ", current:" + System.currentTimeMillis() + ", isPlaying: " + (((System.currentTimeMillis() - timestamp) < EXPIRE_TIME) && playing && !id.equals(deviceID)));
        return ((System.currentTimeMillis() - timestamp) < EXPIRE_TIME) && playing && !id.equals(deviceID);
    }
}
