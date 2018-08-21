package com.udafil.dhruvamsharma.bakingandroidapp.detail;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;

public class DetailActivity extends AppCompatActivity {

    private PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private static int windowIndex = 0;
    private static long playBackPosition = 0;
    private static boolean playWhenReady = true;

    private RecipeModel recipeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPlayerView = findViewById(R.id.video_view);

        initializePlayer();

    }


    private void initializePlayer() {

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(playWhenReady);
        mExoPlayer.seekTo(windowIndex,playBackPosition);


        Uri uri = Uri.parse(recipeModel.getSteps().get(0).getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("BakingAndroidApp")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }


}
