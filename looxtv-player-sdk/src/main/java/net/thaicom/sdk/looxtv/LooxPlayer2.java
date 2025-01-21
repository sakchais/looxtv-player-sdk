package net.thaicom.sdk.looxtv;

import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;
import androidx.media3.common.TrackSelectionOverride;

import android.util.Log;
import android.content.Context;

import androidx.media3.common.C;
import androidx.media3.common.Tracks;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.ui.PlayerView;

public class LooxPlayer2 {

    private APIUtil apiUtil=new APIUtil();
    private ExoPlayer player;
    private DefaultTrackSelector trackSelector;

    private DefaultHttpDataSource.Factory httpDataSourceFactory;
    private static final String TAG = "LooxPlayer2";

    private String mDevice="androidtv";

    private String mUUID="cdbdf1cb-1391-5ce6-2735-009ec8972735";
    private String mMemberid="10328";

    private String mUserAgent = "LOOXATV (Linux;Android) ExoPlayerLib";

    private String monoUrl = "https://vlb.thaicom.io/v2/MONO29TH/playlist.m3u8?uuid=tcmon-0d64966f4fb5cb41ae1344eeee6761b1";

    //    public LooxPlayer2(Context context) {
//        // Initialize ExoPlayer
//        player = new ExoPlayer.Builder(context).build();
//    }
    public LooxPlayer2(Context context) {
        // Initialize ExoPlayer
//        player = new ExoPlayer.Builder(context).build();

        // Set the custom User-Agent


        // Create the HTTP DataSource Factory
        httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setUserAgent(mUserAgent);

        // Initialize TrackSelector
        trackSelector = new DefaultTrackSelector(context);

        // Initialize ExoPlayer with the TrackSelector
        player = new ExoPlayer.Builder(context)
                .setTrackSelector(trackSelector)
                .setMediaSourceFactory(new DefaultMediaSourceFactory(httpDataSourceFactory))
                .build();


        // Add a listener to retrieve video info
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_READY) {
                    // Player is ready to play; fetch video info
                    logVideoInfo();
                }
            }
        });
    }

    public void attachPlayerView(PlayerView playerView) {
        // Attach ExoPlayer to the PlayerView
        playerView.setPlayer(player);
    }

    public void playMedia(int chId) {

//        apiUtil.getToken( chId, mUUID, "B2", 28800, new TokenCallback() {
//            @Override
//            public void onSuccess(GetPayTvTokenResponse response) {
//                // Handle the successful response
//                String token = response.getToken();
//                long validTo = response.getValidTo();
//                Log.d(TAG, "Token: " + token + ", Valid To: " + validTo);
//
//                String videoUrl=String.format("%s?token=%s&device=%s&brand=%s&model=%s&uuid=%s&member=%s", mediaUrl, token, mDevice, Build.BRAND , Build.MODEL, mUUID, mMemberid);
//
//                // Build a MediaItem for the URL
//                MediaItem mediaItem = MediaItem.fromUri(videoUrl);
//
//                // Set the MediaItem and prepare the player
//                player.setMediaItem(mediaItem);
//                player.prepare();
//
//                // Start playback
//                player.play();
//
//                player.addMediaItem(MediaItem.fromUri(Uri.parse(monoUrl)));
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                // Handle the error
//                Log.e(TAG, "Failed to get token: " + t.getMessage());
//            }
//        });

    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void logVideoInfo() {
        if (player != null) {
            // Get duration
            long duration = player.getDuration(); // Milliseconds
            Log.d(TAG, "Duration: " + duration + " ms");

            // Get current video format
            if (player.getVideoFormat() != null) {
                int width = player.getVideoFormat().width;
                int height = player.getVideoFormat().height;
                int bitrate = player.getVideoFormat().bitrate;

                Log.d(TAG, "Resolution: " + width + "x" + height);
                Log.d(TAG, "Bitrate: " + bitrate + " bps");
            }

            // Get metadata (if available)
            MediaMetadata metadata = player.getMediaMetadata();
            String title = metadata.title != null ? metadata.title.toString() : "Unknown";
            Log.d(TAG, "Title: " + title);
        }
    }

    public void selectAudioTrack(int trackIndex) {
        Tracks currentTracks = player.getCurrentTracks();

        for (Tracks.Group group : currentTracks.getGroups()) {
            if (group.getType() == C.TRACK_TYPE_AUDIO) {
                if (trackIndex >= 0 && trackIndex < group.length) {
                    TrackSelectionOverride override = new TrackSelectionOverride(
                            group.getMediaTrackGroup(),
                            trackIndex
                    );
                    trackSelector.setParameters(
                            trackSelector.buildUponParameters()
                                    .addOverride(override)
                    );
                    Log.d(TAG, "Audio track selected: " + trackIndex);
                } else {
                    Log.d(TAG, "Invalid track index");
                }
                return;
            }
        }
    }
}
