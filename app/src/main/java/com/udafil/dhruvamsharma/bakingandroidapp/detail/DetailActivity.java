package com.udafil.dhruvamsharma.bakingandroidapp.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private static int windowIndex = 0;
    private static long playBackPosition = 0;
    private static boolean playWhenReady = true;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();


    private RecipeModel recipeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPlayerView = findViewById(R.id.video_view);

        Intent intent = getIntent();

        if(intent.hasExtra(getPackageName())) {
            recipeModel = Parcels.unwrap(intent.getParcelableExtra(getPackageName()));
        }

        initializePlayer();

    }


    private void initializePlayer() {

        if(recipeModel != null && mExoPlayer == null) {

            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());

            mPlayerView.setPlayer(mExoPlayer);

            MediaSource mediaSource = buildMediaSource();
            mExoPlayer.prepare(mediaSource, true, false);

            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(windowIndex,playBackPosition);

        }
        else {
            //TODO handle error condition
        }



    }

    private MediaSource buildMediaSource() {

        Uri uri = Uri.parse(recipeModel.getSteps().get(0).getVideoURL());

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Util.getUserAgent(this, getResources().getString(R.string.app_name)))).
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
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playBackPosition = mExoPlayer.getCurrentPosition();
            windowIndex = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
