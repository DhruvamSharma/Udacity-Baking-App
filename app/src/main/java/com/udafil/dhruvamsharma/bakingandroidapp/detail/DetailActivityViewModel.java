package com.udafil.dhruvamsharma.bakingandroidapp.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;

public class DetailActivityViewModel extends AndroidViewModel {


    private RecipeModel recipeModel;
    private int stepPosition;

    private int windowIndex = 0;
    private long playBackPosition = 0;
    private boolean playWhenReady = true;

    private int mStepPositionPrevious = -1;
    private boolean isLandscape = false;

    public DetailActivityViewModel(@NonNull Application application) {
        super(application);

    }

    public void init() {
        windowIndex = 0;
        playBackPosition = 0;
        playWhenReady = false;
        mStepPositionPrevious = -1;

    }


    public boolean isLandscape() {
        return isLandscape;
    }

    public void setLandscape(boolean landscape) {
        isLandscape = landscape;
    }

    public int getWindowIndex() {
        return windowIndex;
    }

    public void setWindowIndex(int windowIndex) {
        this.windowIndex = windowIndex;
    }

    public long getPlayBackPosition() {
        return playBackPosition;
    }

    public void setPlayBackPosition(long playBackPosition) {
        this.playBackPosition = playBackPosition;
    }

    public boolean isPlayWhenReady() {
        return playWhenReady;
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.playWhenReady = playWhenReady;
    }


    public int getmStepPositionPrevious() {
        return mStepPositionPrevious;
    }

    public void setmStepPositionPrevious(int mStepPositionPrevious) {
        this.mStepPositionPrevious = mStepPositionPrevious;
    }


    public RecipeModel getRecipeModel() {
        return recipeModel;
    }

    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }

    public int getStepPosition() {
        return stepPosition;
    }

    public void setStepPosition(int stepPosition) {
        this.stepPosition = stepPosition;
    }




}
