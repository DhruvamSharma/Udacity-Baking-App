package com.udafil.dhruvamsharma.bakingandroidapp.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.udafil.dhruvamsharma.bakingandroidapp.data.RecipeRepository;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;

import java.io.IOException;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel{

    private RecipeRepository recipeRepository;
    private List<RecipeModel> model;

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


    public List<RecipeModel> getRecipeData() {

        if(model == null) {
            try {
                model = recipeRepository.getRecipeData(getApplication().getApplicationContext());
            } catch (IOException e) {
                model = null;
            }
            if (model == null) {
                Log.e("MainActivityViewModel", "model is empty");
            }

        }


        return model;
    }

}
