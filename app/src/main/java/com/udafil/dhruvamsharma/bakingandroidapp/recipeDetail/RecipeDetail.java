package com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.RecipeRepository;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.detail.DetailActivity;
import com.udafil.dhruvamsharma.bakingandroidapp.utils.GsonInstance;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

public class RecipeDetail extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener {

    private RecipeModel recipeData;
    private boolean mTwoPane = false;
    private Intent intent;
    private ImageView mNoFoodImage;
    private TextView descriptionForText;
    private FloatingActionButton mChangeStepButton;

    private PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private int mStepPosition = 0;
    private int mStepPositionPrevious = -1;

    private static int windowIndex = 0;
    private static long playBackPosition = 0;
    private static boolean playWhenReady = true;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    RecipeDetailFragment recipeDetailFragment;

    private final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //setup activity
        setupActivity();

        //Setting up fragment
        setUpFragment(savedInstanceState);

    }

    /**
     * A method that sets up the intent and cheks for tablet view
     */
    private void setupActivity() {

        intent = getIntent();

        if(intent.hasExtra(getPackageName())) {
            recipeData = Parcels.unwrap(intent.getParcelableExtra(getPackageName()));
        }

        mNoFoodImage = findViewById(R.id.no_video_image_iv);

        if( findViewById(R.id.tablet_right_view_fl) != null ) {
            mTwoPane = true;
            mPlayerView = findViewById(R.id.video_view);


            mChangeStepButton = findViewById(R.id.change_recipe_step_btn);

            descriptionForText = findViewById(R.id.description_for_step_tablet_tv);

            if(intent.hasExtra("position")) {
                mStepPosition = intent.getIntExtra("position", 0);

                initializePlayer();

                descriptionForText.setText(recipeData.getSteps().get(mStepPosition).getDescription());
            }


            //This button handles step change procedure
            mChangeStepButton.setOnClickListener(view -> {

                if(mStepPosition < recipeData.getSteps().size()-1) {

                    mStepPositionPrevious = mStepPosition;
                    mStepPosition = mStepPositionPrevious + 1;
                }
                else {

                    mStepPosition = 0;
                    mStepPositionPrevious = -1;
                }

                Log.e("step position after", mStepPosition+"");
                releasePlayer();
                initializePlayer();

            });



        } else {

            mTwoPane = false;
        }



    }


    /**
     * A method that sets up the Fragments and pass the recipe data
     */
    private void setUpFragment(Bundle savedInstanceState) {
        //Constructing step list in phone mode
        if(savedInstanceState != null) {
            recipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentByTag(SIMPLE_FRAGMENT_TAG);
        } else {

            recipeDetailFragment = new RecipeDetailFragment();
        }

        if(!recipeDetailFragment.isInLayout()) {

            //setting arguments for the fragment
            Bundle bundle = new Bundle();
            bundle.putParcelable(getPackageName(), Parcels.wrap(recipeData));
            bundle.putBoolean("isTwoPane", mTwoPane);
            recipeDetailFragment.setArguments( bundle );

            //Fragment Manager set up
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fm, recipeDetailFragment, SIMPLE_FRAGMENT_TAG)
                    .commit();

        }

    }

    @Override
    public void onFragmentInteraction(int position) {

        if(!mTwoPane) {
            Intent intent = new Intent(this, DetailActivity.class);
            Parcelable wrapped = Parcels.wrap(recipeData);
            intent.putExtra(getPackageName(), wrapped);
            intent.putExtra("position", position);
            startActivity(intent);
        } else {

            mStepPositionPrevious = mStepPosition;
            mStepPosition = position;
            initializePlayer();

        }


    }

    @Override
    public void onRecipeChange(Integer id) {

        recipeData = GsonInstance.getGsonInstance().fromJson(RecipeRepository.getInstance().getRecipe(id, this), RecipeModel.class);

        setUpFragment(null);

    }


    private void initializePlayer() {

        if(recipeData != null && mStepPositionPrevious != mStepPosition && mTwoPane) {

            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());

            mPlayerView.setPlayer(mExoPlayer);

            MediaSource mediaSource = buildMediaSource();

            if( mediaSource == null ) {
                mPlayerView.setVisibility(View.INVISIBLE);
                mNoFoodImage.setVisibility(View.VISIBLE);

            } else {
                mPlayerView.setVisibility(View.VISIBLE);
                mNoFoodImage.setVisibility(View.INVISIBLE);
                mExoPlayer.prepare(mediaSource, true, false);

                mExoPlayer.setPlayWhenReady(playWhenReady);
                mExoPlayer.seekTo(windowIndex,playBackPosition);
            }

        }
        else {
            //TODO handle error condition

        }



    }

    private MediaSource buildMediaSource() {

        if (recipeData.getSteps().get(mStepPosition).getVideoURL().equals("")) {
            return null;
        }

        Uri uri = Uri.parse(recipeData.getSteps().get(mStepPosition).getVideoURL());

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Util.getUserAgent(this, getResources().getString(R.string.app_name)))).
                createMediaSource(uri);


    }



    /**
     * Capturing the playback position, playWhenReady and windowIndex when teh app goes offScreen
     * and releasing the shared resources.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            playBackPosition = mExoPlayer.getCurrentPosition();
            windowIndex = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
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
        if (Util.SDK_INT > 23 && mTwoPane) {
            initializePlayer();
        }
    }

    /**
     * Initializing player in onResume for API > 24
     */
    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null && mTwoPane)) {
            initializePlayer();
        }
    }

    /**
     * Releasing player in onResume before API 24
     */
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23&& mTwoPane) {
            releasePlayer();
        }
    }

    /**
     *  Multiwindow concept.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23 && mTwoPane) {
            releasePlayer();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "myFragmentName", recipeDetailFragment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            //recipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
        }
    }
}
