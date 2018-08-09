package com.udafil.dhruvamsharma.bakingandroidapp.data;

import android.content.Context;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class to abstract the data sources for the view model.
 */
final public class RecipeRepository {

    private static RecipeRepository sRecipeRepository;





    private RecipeRepository() {

    }



    /**
     * Reading JSON data from file in stream mode and sending the data to the view models.
     */
    public List<RecipeModel> getRecipeData(Context context) throws IOException {

        InputStreamReader reader = null;
        JsonReader jsonReader;
        List<RecipeModel> model = new ArrayList<>();

        try {
            reader = new InputStreamReader(context.getAssets().open("recipe.json"), "UTF-8");
            Gson gson = GsonInstance.getGsonInstance();

            jsonReader = new JsonReader(reader);
            jsonReader.beginArray();
            while( jsonReader.hasNext() ) {

                model.add( gson.fromJson(reader, RecipeModel.class));

            }


            jsonReader.endArray();

        }
        catch (IllegalStateException | JsonSyntaxException exception) {
            return null;
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }

        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        finally {
            if (reader != null) {
                reader.close();
            }
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
