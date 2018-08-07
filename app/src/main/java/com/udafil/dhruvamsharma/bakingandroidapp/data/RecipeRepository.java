package com.udafil.dhruvamsharma.bakingandroidapp.data;

import android.content.Context;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.utils.GsonInstance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Repository class to abstract the data sources for the view model.
 */
//TODO make it final back again
public class RecipeRepository {

    private static RecipeRepository sRecipeRepository;





    private RecipeRepository() {

    }



    /**
     * Reading JSON data from file in stream mode and sending the data to the view models.
     */
    public RecipeModel[] getRecipeData(Context context) {

        Reader reader;
        RecipeModel[] model;

        try {
            reader = new InputStreamReader(context.getAssets().open("recipe.json"));
            Gson gson = GsonInstance.getGsonInstance();
            model = gson.fromJson(reader, RecipeModel[].class);


            reader.close();

        }


        catch (UnsupportedEncodingException ex) {
            return null;
        }

        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return model;

    }


    /**
     * This methods makes the RecipeRepository class a singleton
     * @RecipeRepository
     */
    public static RecipeRepository getInstance() {

        if( sRecipeRepository == null ) {
            sRecipeRepository = new RecipeRepository();
        }

        return sRecipeRepository;
    }



}
