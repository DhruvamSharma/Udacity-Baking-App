package com.udafil.dhruvamsharma.bakingandroidapp.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private TextView descriptionForText;
    private FloatingActionButton mChangeRecipeStepButton;
    private ImageView mNoFoodImage;

    private ImageView mFullScreenIcon;

    private DetailActivityViewModel mDetailActivityViewModel;


    /*** TODO
     * Handle the small member variable data in onSaveInstanceState or View Models in almost every activity.
     * @param savedInstanceState
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       //Getting the instance of View Model for This activity
        mDetailActivityViewModel = ViewModelProviders.of(this).get(DetailActivityViewModel.class);

        //Getting intent from Recipe Detail Activity
        Intent intent = getIntent();

        if(intent.hasExtra(getPackageName()) && intent.hasExtra("position") && savedInstanceState == null) {

            //setting data for the view model
            mDetailActivityViewModel.setStepPosition(
                    intent.getIntExtra("position", 0));
            mDetailActivityViewModel.setRecipeModel(
                    Parcels.unwrap(intent.getParcelableExtra(getPackageName())));

        }

        setUpActivity();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putBoolean("isPlaying", mDetailActivityViewModel.isPlayWhenReady());

    }

    /**
     * This method takes care of setting up the activity
     */
    private void setUpActivity() {

        mNoFoodImage = findViewById(R.id.no_video_image_detail_iv);

        mPlayerView = findViewById(R.id.video_view);
        descriptionForText = findViewById(R.id.description_for_step_tv);

        mChangeRecipeStepButton = findViewById(R.id.chnage_recipe_step_btn);
        mFullScreenIcon = mPlayerView.findViewById(R.id.exo_fullscreen_icon);


        handleActivityInteractions();

    }


    /**
     * This method handles all the interactions happening inside the activity
     */
    private void handleActivityInteractions() {

        mFullScreenIcon.setOnClickListener(view -> {

            toggleFullScreen();

        });


        // This portion of code handles the changing of recipe step
        mChangeRecipeStepButton.setOnClickListener((view -> {

            if(mDetailActivityViewModel.getStepPosition() < mDetailActivityViewModel.getRecipeModel().getSteps().size()-1) {

                mDetailActivityViewModel.setmStepPositionPrevious(mDetailActivityViewModel.getStepPosition());
                mDetailActivityViewModel.setStepPosition(mDetailActivityViewModel.getmStepPositionPrevious() + 1);
            }
            else {

                mDetailActivityViewModel.setStepPosition(0);
                mDetailActivityViewModel.setmStepPositionPrevious(-1);
            }

            Log.e("step position after", mDetailActivityViewModel.getStepPosition()+"");
            releasePlayer();
            initializePlayer();



        }));

    }


    /**
     * This method initializes the exo player as and when required
     */
    private void initializePlayer() {

        if(mDetailActivityViewModel.getRecipeModel() != null && mDetailActivityViewModel.getmStepPositionPrevious() != mDetailActivityViewModel.getStepPosition()) {

            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());

            mPlayerView.setPlayer(mExoPlayer);

            MediaSource mediaSource = buildMediaSource();

            mDetailActivityViewModel.setPlayWhenReady(true);

            /** TODO
             * Dynamically create constraints depending upon the whether the video is there or not!
             *
             **/


            if( mediaSource == null ) {

                mPlayerView.setVisibility(View.GONE);
                mNoFoodImage.setVisibility(View.VISIBLE);


            } else {

                mPlayerView.setVisibility(View.VISIBLE);
                mNoFoodImage.setVisibility(View.GONE);

                mExoPlayer.prepare(mediaSource, true, false);

                mExoPlayer.setPlayWhenReady(mDetailActivityViewModel.isPlayWhenReady());
                mExoPlayer.seekTo(mDetailActivityViewModel.getWindowIndex(),mDetailActivityViewModel.getPlayBackPosition());
            }


            descriptionForText.setText(mDetailActivityViewModel.getRecipeModel().getSteps().get(mDetailActivityViewModel.getStepPosition()).getDescription());

        }
        else {
            //TODO handle error condition

        }



    }

    /**
     * his method is called by initializePlayer method and returns a Media source
     * @return
     */
    private MediaSource buildMediaSource() {

        if (mDetailActivityViewModel.getRecipeModel().getSteps().get(mDetailActivityViewModel.getStepPosition()).getVideoURL().equals("")) {
            return null;
        }

        Uri uri = Uri.parse(mDetailActivityViewModel.getRecipeModel().getSteps().get(mDetailActivityViewModel.getStepPosition()).getVideoURL());

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Util.getUserAgent(this, getResources().getString(R.string.app_name)))).
                createMediaSource(uri);


    }


    /**
     * Toggle full screen in phones
     */
    private void toggleFullScreen() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mFullScreenIcon.setImageResource(R.drawable.ic_fullscreen_skrink);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mFullScreenIcon.setImageResource(R.drawable.ic_fullscreen_expand);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(getResources().getConfiguration().orientation == newConfig.orientation) {

        } else {

        }

    }

    /**
     * Capturing the playback position, playWhenReady and windowIndex when teh app goes offScreen
     * and releasing the shared resources.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {

            mDetailActivityViewModel.setPlayBackPosition(mExoPlayer.getCurrentPosition());
            mDetailActivityViewModel.setPlayWhenReady(false);
            mDetailActivityViewModel.setWindowIndex(mExoPlayer.getCurrentWindowIndex());

            mDetailActivityViewModel.setmStepPositionPrevious(mDetailActivityViewModel.getmStepPositionPrevious());
            mDetailActivityViewModel.setStepPosition(mDetailActivityViewModel.getStepPosition());

            //Toast.makeText(getApplicationContext(), mDetailActivityViewModel.getStepPosition()+" in release", Toast.LENGTH_SHORT).show();


            mExoPlayer.release();
            mExoPlayer = null;

        }
    }




    /**
     * Handling releasing player nd codecs properly and gaining them as soon as in onStart.
     * Since API 24, Multiwindow concept came into play so initializing the player in onStart rather than in onResume
     */
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    /**
     * Initializing player in onResume for API > 24
     */
    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /**
     * Releasing player in onResume before API 24
     */
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    /**
     *  Multiwindow concept.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
