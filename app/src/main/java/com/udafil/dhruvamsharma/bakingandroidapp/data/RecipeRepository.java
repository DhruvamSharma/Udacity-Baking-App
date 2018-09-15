package com.udafil.dhruvamsharma.bakingandroidapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.utils.GsonInstance;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Repository class to abstract the data sources for the view model.
 */
final public class RecipeRepository {

    private static RecipeRepository sRecipeRepository;
    private List<RecipeModel> model;

    private RecipeRepository() {

    }



    /**
     * Reading JSON data from file in stream mode and sending the data to the view models.
     */
    public void getRecipeData(Context context) throws IOException {


        //initializing FAN library for network requests
        AndroidNetworking.initialize(context);

        AndroidNetworking.get(context.getResources().getString(R.string.recipe_list_url))
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(RecipeModel.class, new ParsedRequestListener<List<RecipeModel>>() {
                    @Override
                    public void onResponse(List<RecipeModel> response) {

                        //Toast.makeText(context, response.get(0).getIngredients().get(0).getIngredient() + "here ", Toast.LENGTH_SHORT).show();

                        //method to set the response to the list because final variable couldn't do much!
                        setValue(response);
                        storeRecipeDataInFile(context, response);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });




    }


    /**
     * A method to store the preference data once it is retrieved.
     * Each model is converted to json string and then stred in a Set named dataSet.
     * @param context
     */
    private void storeRecipeDataInFile(Context context, List<RecipeModel> model) {

        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.RECIPE_DATA_PREFERENCE_FILE), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> dataSet = new HashSet<>();

        for (int i = 0; i < model.size(); i++) {

            RecipeModel recipeModel = model.get(i);
            String json = GsonInstance.getGsonInstance().toJson(recipeModel);

            dataSet.add(json);


        }

        editor.putStringSet( context.getString(R.string.recipe_ingredients), dataSet);



        editor.apply();

    }

    public String getRecipe(int modelPosition, Context context) {

        Set<String> dataSet;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.RECIPE_DATA_PREFERENCE_FILE), Context.MODE_PRIVATE);
        dataSet = sharedPreferences.getStringSet(context.getString(R.string.recipe_ingredients), null);


        Iterator<String> iterator;
        String response = null;
        int position = 1;


        if (dataSet != null) {

            iterator = dataSet.iterator();

            while(iterator.hasNext()) {

                response = iterator.next();

                if(position == modelPosition) {

                    break;
                }

                position++;
            }

        }


        return response;

    }

    public Set<String> getRecipeSet(Context context) {


        Set<String> dataSet;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.RECIPE_DATA_PREFERENCE_FILE), Context.MODE_PRIVATE);
        dataSet = sharedPreferences.getStringSet(context.getString(R.string.recipe_ingredients), null);

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


    public void setValue(List<RecipeModel> value) {
        this.model = value;
    }
}
