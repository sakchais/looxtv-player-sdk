package net.thaicom.app.looxtvplayerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.ui.PlayerView;

import net.thaicom.sdk.looxtv.LooxPlayer;
//import net.thaicom.sdk.looxtv.LooxPlayerView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private LooxPlayer player;

    private LooxPlayer.PlaybackListener playbackListener=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // Initialize PlayerView
        PlayerView playerView = (PlayerView) findViewById(R.id.player_view);
        player = new LooxPlayer(this);
        player.initialize(playerView);
        playbackListener=new LooxPlayer.PlaybackListener() {
            @Override
            public void onReady() {
                Log.d(TAG, "onReady");
            }

            @Override
            public void onPlaybackEnded() {
                Log.d(TAG, "onPlaybackEnded");
            }

            @Override
            public void onError(String errorMessage) {
                Log.d(TAG, "onError: "+errorMessage);
                if( errorMessage.contains("Device is not registered") ||
                        errorMessage.contains("Subscription is expired")){
                    player.registerDevice();
                }
            }

            @Override
            public void onPlayerReady() {
                Log.d(TAG, "onPlayerReady");
            }

            @Override
            public void onSubscriptionReady(String tag) {
                Log.d(TAG, "onSubscriptionReady: tag="+tag);
                player.playLive(202);
            }
        };
        player.setPlaybackListener(playbackListener);
    }
}