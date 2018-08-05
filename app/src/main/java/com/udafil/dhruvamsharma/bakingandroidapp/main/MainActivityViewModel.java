package com.udafil.dhruvamsharma.bakingandroidapp.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.udafil.dhruvamsharma.bakingandroidapp.data.RecipeRepository;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;

public class MainActivityViewModel extends AndroidViewModel{

    private RecipeRepository recipeRepository;
    private RecipeModel[] model;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Initialization method for ViewModel
     * This methods gets the recipe data and assign it to the repository variable
     */
    public void init() {

        recipeRepository = RecipeRepository.getInstance();

    }


    public RecipeModel[] getRecipeData() {

        if(model == null) {
            model = recipeRepository.getRecipeData(getApplication().getApplicationContext());
            if (model == null) {
                Log.e("MainActivityViewModel", "model is empty");
            }
            Log.e("MainActivityViewModel", model.toString());
        }


        return model;
    }

}
