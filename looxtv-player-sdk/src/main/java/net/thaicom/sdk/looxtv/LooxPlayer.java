package net.thaicom.sdk.looxtv;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.TrackSelectionOverride;
import androidx.media3.common.Tracks;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.ui.PlayerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LooxPlayer {

    private ExoPlayer player;
    private PlaybackListener playbackListener;
    private final DefaultTrackSelector trackSelector;

    private DefaultHttpDataSource.Factory httpDataSourceFactory;

    private static final String TAG = "LooxPlayer";

    private APIUtil apiUtil=new APIUtil();

    private String mDevice="androidtv";

    private String mDeviceId=null;

    private String mUUID="bdbdf1cb-1391-5ce6-2735-009ec8972735";

    private String mMemberid="10328";

    private String mUserAgent = "LOOXATV (Linux;Android) ExoPlayerLib";

    private String monoUrl = "https://vlb.thaicom.io/v2/MONO29TH/playlist.m3u8?uuid=tcmon-0d64966f4fb5cb41ae1344eeee6761b1";
    private String mediaUrl = "https://vlb.thaicom.io/v3/TV5_HD/playlist.m3u8";
    private String mediaUrl2 = "https://vlb.thaicom.io/v3/TPBS_HD/playlist.m3u8";

    private String shortLink="https://www.thaicom.net";

    private Context mContext;

    private String mFirebaseUid=null;

    private DeviceInfo mDeviceInfo=null;
    private MemberInfo mMemberInfo=null;

    private ArrayList<ChannelInfo> mChannelInfos=null;

    private int mNumConcurrent=0;

//    private PlayerView mPlayerView=null;
    private LooxPlayerView mPlayerView=null;

    Player.Listener mEventListener=null;

//    private DeviceLimitManager mDeviceLimitManager=new DeviceLimitManager();

    public LooxPlayer(Context context) {
        mContext = context;

        mDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        mUUID = getUuid(mDeviceId);

//        getDeviceInfo();
        getChannelInfo("");
        checkSubscription();

        // Create the HTTP DataSource Factory
        httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setUserAgent(mUserAgent);

        // Initialize the TrackSelector and Player
        trackSelector = new DefaultTrackSelector(context);
        player = new ExoPlayer.Builder(context)
                .setTrackSelector(trackSelector)
                .setMediaSourceFactory(new DefaultMediaSourceFactory(httpDataSourceFactory))
                .build();

        // Attach Player.Listener for playback events
        mEventListener=new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackListener != null) {
                    if (playbackState == Player.STATE_READY) {
                        playbackListener.onReady();
                    } else if (playbackState == Player.STATE_ENDED) {
                        playbackListener.onPlaybackEnded();
                    }
                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                if (playbackListener != null) {
                    playbackListener.onError(error.getMessage());
                }
            }
        };
        player.addListener(mEventListener);
    }

    // Playback control methods
    public void play() {
        if (player != null) player.play();
    }

    public void pause() {
        if (player != null) player.pause();
    }

    public void stop() {
        if (player != null) player.stop();
    }

    public void seekTo(long positionMs) {
        if (player != null) player.seekTo(positionMs);
    }

    public void setPlaybackSpeed(float speed) {
        if (player != null) player.setPlaybackSpeed(speed);
    }

    public void initialize(LooxPlayerView playerView) {
        // Attach ExoPlayer to the PlayerView

        if (player != null && playerView != null) {
            playerView.setPlayer(player);
            mPlayerView=playerView;
        }


    }

    public int playLive(int id){


        getConcurrent();
        if(mMemberInfo==null || !mMemberInfo.isAllowToPlay()){
            getShortLink(mUUID, mDeviceInfo==null ? null:mDeviceInfo.firebaseUid);
            return -1;
        }

        if(mNumConcurrent>=mMemberInfo.maxConcurrent){
            Log.d(TAG, "Reached Concurrent Limit: " + mNumConcurrent);
            return -2;
        }

        if(mChannelInfos==null || mChannelInfos.isEmpty()) return -3;

        String url= apiUtil.getUrlByChId(id, mChannelInfos);
        if(url.isEmpty()) return -3;

        apiUtil.getToken( id, mUUID, "B2", 28800, new TokenCallback() {
            @Override
            public void onSuccess(TokenInfo tokenInfo) {

                String token = tokenInfo.getToken();
                int validTo = tokenInfo.getValidTo();
                Log.d(TAG, "Token: " + token + ", Valid To: " + validTo);


                String videoUrl=String.format("%s?token=%s&device=%s&brand=%s&model=%s&uuid=%s&member=%s", url, token, mDevice, Build.BRAND , Build.MODEL, mUUID, mMemberid);

                // Build a MediaItem for the URL
                MediaItem mediaItem = MediaItem.fromUri(videoUrl);

                // Set the MediaItem and prepare the player
                player.setMediaItem(mediaItem);
                player.prepare();

                // Start playback
                player.play();
                setConcurrent();
//                mDeviceLimitManager.setUserOnlineStatus(mDeviceInfo.firebaseUid, true, true);

//                player.addMediaItem(MediaItem.fromUri(Uri.parse(monoUrl)));
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle the error
                Log.e(TAG, "Failed to get token: " + t.getMessage());
                Log.e(TAG, "Try to play token: " + t.getMessage());

                MediaItem mediaItem = MediaItem.fromUri(url);

                // Set the MediaItem and prepare the player
                player.setMediaItem(mediaItem);
                player.prepare();

                // Start playback
                player.play();


            }
        });

        return 0;

    }

    // Media management methods
    public void setMedia(String url) {
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
        player.setMediaItem(mediaItem);
        player.prepare();
    }

    public void addMedia(String url) {
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
        player.addMediaItem(mediaItem);
    }

    public void clearMedia() {
        player.clearMediaItems();
    }

    // Track management methods
    public void setPreferredAudioLanguage(String language) {
        trackSelector.setParameters(
                trackSelector.buildUponParameters().setPreferredAudioLanguage(language)
        );
    }

    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    public long getPlaybackPosition() {
        return player.getCurrentPosition();
    }

    public long getDuration() {
        return player.getDuration();
    }

    public void setVolume(float volume) {
        if (player != null) player.setVolume(volume);
    }

    public void setRepeatMode(int repeatMode) {
        if (player != null) player.setRepeatMode(repeatMode);
    }

    public void setMute(boolean isMuted) {
        setVolume(isMuted ? 0f : 1f);
    }

    // Lifecycle management
    public void release() {

        Log.d(TAG, "Releasing player");

        if (player != null) {
            if(mEventListener!=null) player.removeListener(mEventListener);
            player.stop();
        }

        if (mPlayerView != null) {
            mPlayerView.setPlayer(null);
        }
        if (player != null) {
            player.release();
            player = null;
        }
        clearConcurrent();
    }

    public void setPlaybackListener(PlaybackListener listener) {
        this.playbackListener = listener;
    }

    // Interface for external listeners
    public interface PlaybackListener {
        void onReady();

        void onPlaybackEnded();

        void onError(String errorMessage);

        void onPlayerReady();

        void onSubscriptionReady(String tag);


    }

    public Map<String, List<String>> getAvailableTracks() {
        Map<String, List<String>> tracksMap = new HashMap<>();

        if (player == null) {
            return tracksMap; // Return an empty map if the player is not initialized
        }

        Tracks currentTracks = player.getCurrentTracks();
        if (currentTracks != null) {
            for (Tracks.Group group : currentTracks.getGroups()) {
                String trackTypeName = getTrackTypeName(group.getType());
                List<String> trackDetails = new ArrayList<>();

                for (int i = 0; i < group.length; i++) {
                    Format format = group.getTrackFormat(i);

                    // Build track details string
                    StringBuilder details = new StringBuilder();
                    if (format.language != null) {
                        details.append("Language: ").append(format.language).append(", ");
                    }
                    if (format.bitrate > 0) {
                        details.append("Bitrate: ").append(format.bitrate / 1000).append(" kbps, ");
                    }
                    if (format.width > 0 && format.height > 0) {
                        details.append("Resolution: ").append(format.width).append("x").append(format.height).append(", ");
                    }
                    trackDetails.add(details.toString());
                }

                // Add to the map
                if (!trackDetails.isEmpty()) {
                    tracksMap.put(trackTypeName, trackDetails);
                }
            }
        }

        return tracksMap;
    }

    // Helper to get track type name
    private String getTrackTypeName(int trackType) {
        switch (trackType) {
            case C.TRACK_TYPE_VIDEO:
                return "Video";
            case C.TRACK_TYPE_AUDIO:
                return "Audio";
            case C.TRACK_TYPE_TEXT:
                return "Subtitle";
            default:
                return "Unknown";
        }
    }

    public boolean selectTrack(int trackType, int trackIndex) {
        if (player == null || player.getCurrentTracks() == null) {
            Log.w("MediaPlayerWrapper", "Player or tracks are not initialized.");
            return false;
        }

        Tracks currentTracks = player.getCurrentTracks();
        for (Tracks.Group group : currentTracks.getGroups()) {
            if (group.getType() == trackType) {
                if (trackIndex >= 0 && trackIndex < group.length) {
                    // Create TrackSelectionOverride for the desired track
                    TrackSelectionOverride override = new TrackSelectionOverride(
                            group.getMediaTrackGroup(),
                            trackIndex
                    );

                    // Apply the override
                    trackSelector.setParameters(
                            trackSelector.buildUponParameters().addOverride(override)
                    );
                    Log.d("MediaPlayerWrapper", "Selected track type: " + getTrackTypeName(trackType) + ", index: " + trackIndex);
                    return true;
                } else {
                    Log.w("MediaPlayerWrapper", "Invalid track index: " + trackIndex);
                    return false;
                }
            }
        }

        Log.w("MediaPlayerWrapper", "Track type not found: " + getTrackTypeName(trackType));
        return false;
    }

    private void getDeviceInfo(){

        apiUtil.getDeviceInfo(mUUID, new DeviceInfoCallback() {
            @Override
            public void onSuccess(DeviceInfo deviceInfo) {
                mDeviceInfo = deviceInfo;
                Log.d(TAG, "Device is registered");

                if (mDeviceInfo.firebaseUid != null)
                    getMemberStatus(mDeviceInfo.firebaseUid);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Device is not registered");
                playbackListener.onError("Device is not registered");
            }
        });
    }

    private void getMemberStatus(String fUid){
        apiUtil.getPaidStatus(fUid, new MemberPaidStatusCallback() {
            @Override
            public void onSuccess(MemberInfo memberInfo) {
                mMemberInfo = memberInfo;

                if(playbackListener != null && mMemberInfo!=null){
                    if(mMemberInfo.isAllowToPlay()) {
                        playbackListener.onSubscriptionReady(mMemberInfo.groupTags);
                    }
                    else {
                        playbackListener.onError("Subscription is expired");
                    }
                }


//                Log.d(TAG, String.format("groupTags: %s, isExpired: %s, maxConcurrent: %d, remainDay: %d",
//                        mMemberInfo.groupTags,
//                        mMemberInfo.isExpired?"true":"false",
//                        mMemberInfo.maxConcurrent,
//                        mMemberInfo.remainDay));

                getConcurrent();



            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void checkSubscription(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (mDeviceInfo == null || mDeviceInfo.firebaseUid == null){
                        getDeviceInfo();
                    } else if (mMemberInfo == null){
                        getMemberStatus(mDeviceInfo.firebaseUid);
                    }else if (mChannelInfos == null || mChannelInfos.isEmpty()){
                        getChannelInfo(mMemberInfo.groupTags);
                    }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void getChannelInfo(String groupTags){
        apiUtil.getLooxChannelInfo(groupTags, new ChannelInfoCallback() {
            @Override
            public void onSuccess(ArrayList<ChannelInfo> channelInfos) {
                Log.d(TAG, "Channel Info: " + channelInfos.size());

                mChannelInfos = channelInfos;

                for (  ChannelInfo channelInfo: channelInfos) {
                    Log.d(TAG, String.format("Channel: %d, %s",
                            channelInfo.chId,
                            channelInfo.urlLive
                            ));
                }
                if(mChannelInfos != null && !mChannelInfos.isEmpty())
                    playbackListener.onPlayerReady();


//                playLive(1);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public boolean isInitialized() {
        return (mChannelInfos != null && !mChannelInfos.isEmpty());
    }

    public void getShortLink(String deviceId, String firebaseUid) {

        String OS_name = "Android+TV";
        String deepLink="";

        if(firebaseUid==null || firebaseUid.isEmpty() || firebaseUid.equals("null")) {
            deepLink = "https://payment.thaicom.io/device_register?device_id=" + deviceId + "&brand=" + Build.BRAND + "&model=" + Build.MODEL + "&os=" + OS_name + "&os_version=" + Build.VERSION.RELEASE + "&app_version=0.0";
        }
        else {
            deepLink = "https://payment.thaicom.io/payment_big?fuid=" + firebaseUid;
        }

        Log.d(TAG, String.format("deepLink=%s", deepLink));
        String finalDeepLink = deepLink;
        apiUtil.getDynamicLinkFromRestApi(deepLink, new DynamicLinkCallback() {
            @Override
            public void onSuccess(String shortLink) {
                Log.d(TAG, "Short Link: " + shortLink);
                showQRCode(shortLink!=null?shortLink: finalDeepLink);
            }

            @Override
            public void onFailure(Throwable t) {
                showQRCode(finalDeepLink);
            }
        });
    }

    private void showQRCode(String shortLink){

        final boolean b = new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // pass the short link to the QRActivity
                Intent intent = new Intent(mContext, QrActivity.class);
                intent.putExtra("shortLink", shortLink);
                intent.putExtra("uUid", mUUID);
                intent.putExtra("firebaseUid", mDeviceInfo==null?"":mDeviceInfo.firebaseUid);
                if(mDeviceInfo!=null) {
                    intent.putExtra("memberId", mDeviceInfo.memberId);
                    intent.putExtra("email", mDeviceInfo.email);
                }else{
                    intent.putExtra("memberId", "");
                    intent.putExtra("email", "");
                }

                mContext.startActivity(intent);
            }
        }, 1000);
    }

    private void setConcurrent(){
        if(mDeviceInfo==null || mDeviceInfo.firebaseUid==null)
            return;
        apiUtil.setConcurrent(mDeviceInfo.firebaseUid, mUUID, true, true, null);
    }

    private void clearConcurrent(){
        if(mDeviceInfo==null || mDeviceInfo.firebaseUid==null)
            return;
        apiUtil.setConcurrent(mDeviceInfo.firebaseUid, mUUID, false, false, null);
    }

    private void getConcurrent(){
        if(mDeviceInfo==null || mDeviceInfo.firebaseUid==null)
            return;

        apiUtil.getConcurrent(mDeviceInfo.firebaseUid, mUUID, new ConcurrentCallback() {
            @Override
            public void onSuccess(int noConcurrent) {
                Log.d(TAG, "Number Concurrent: " + noConcurrent);
                mNumConcurrent = noConcurrent;
//                mNumConcurrent = 10;
            }


            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Get Concurrent Failed: " + t.getMessage());
            }
        });
    }

    private String getUuid(String deviceID) {
        return String.format("%s-%s-%s-%s-%s",
                deviceID.toLowerCase().substring(0, 8),
                deviceID.toLowerCase().substring(8, 12),
                deviceID.toLowerCase().substring(12),
                deviceID.toLowerCase().substring(0, 4),
                deviceID.toLowerCase().substring(4)
        );
    }

    public void registerDevice() {
        if(!QrActivity.isRunning())
            getShortLink(mUUID, mDeviceInfo==null ? null:mDeviceInfo.firebaseUid);
    }

}

