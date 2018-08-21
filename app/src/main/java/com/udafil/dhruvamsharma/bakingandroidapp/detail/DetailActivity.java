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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
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

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            mPlayerView.setPlayer(mExoPlayer);


            Log.e(getPackageName(), recipeModel.getSteps().get(0).getVideoURL());

            Uri uri = Uri.parse(recipeModel.getSteps().get(0).getVideoURL());
            MediaSource mediaSource = buildMediaSource(uri);
            mExoPlayer.prepare(mediaSource);

            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(windowIndex,playBackPosition);

        }
        else
        Log.e(getPackageName(), "we are here!");


    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Util.getUserAgent(this, getResources().getString(R.string.app_name)))).
                createMediaSource(uri);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExoPlayer.stop();
        mExoPlayer.release();
    }
}
