package net.thaicom.sdk.looxtv;

import java.util.ArrayList;

public interface ChannelInfoCallback {
    void onSuccess(ArrayList<ChannelInfo> channelInfos);
    void onFailure(Throwable t);
}
