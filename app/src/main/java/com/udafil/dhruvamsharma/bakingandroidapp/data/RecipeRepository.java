package com.udafil.dhruvamsharma.bakingandroidapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.Ingredients;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        com.google.gson.stream.JsonReader jsonReader = null;
        List<RecipeModel> model = new ArrayList<>();


        try {
            reader = new InputStreamReader(context.getAssets().open("recipe.json"), "UTF-8");
            Gson gson = GsonInstance.getGsonInstance();

            jsonReader = new com.google.gson.stream.JsonReader(reader);
            jsonReader.beginArray();
            RecipeModel recipeModel;
            while( jsonReader.hasNext() ) {
                recipeModel = gson.fromJson(jsonReader, RecipeModel.class);
                model.add( recipeModel );

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
            if (reader != null && jsonReader != null) {
                reader.close();
                jsonReader.close();

                storeRecipeDataInFile(context, model);
            }
        }


        return model;

    }


    /**
     * A method to store the preference data once it is retrieved
     * @param context
     */
    private void storeRecipeDataInFile(Context context, List<RecipeModel> model) {

        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.RECIPE_DATA_PREFERENCE_FILE), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> dataSet = new HashSet<>();

        for (int i = 0; i < model.size(); i++) {

            RecipeModel recipeModel = model.get(i);

            for (int j = 0; j < recipeModel.getIngredients().size(); j++) {

                Ingredients ingredients = recipeModel.getIngredients().get(j);
                dataSet.add(ingredients.getIngredients());

                if(j == recipeModel.getIngredients().size() -1) {
                    editor.putStringSet( context.getString(R.string.recipe_ingredients) + i, dataSet);

                }

            }

        }



        editor.apply();

    }

    public Set<String> getRecipeIngredients(int i, Context context) {

        Set<String> dataSet;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.RECIPE_DATA_PREFERENCE_FILE), Context.MODE_PRIVATE);
        dataSet = sharedPreferences.getStringSet(context.getString(R.string.recipe_ingredients) + i, null);
        return dataSet;

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
