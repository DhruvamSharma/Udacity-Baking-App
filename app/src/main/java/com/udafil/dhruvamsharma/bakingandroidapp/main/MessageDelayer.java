package com.udafil.dhruvamsharma.bakingandroidapp.main;

import android.content.Context;
import android.support.annotation.Nullable;

import com.udafil.dhruvamsharma.bakingandroidapp.data.RecipeRepository;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.main.IdlingResource.RecipeIdlingResource;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;

public class MessageDelayer {

    private static final int DELAY_MILLIS = 5000;

    interface DelayerCallback {
        void onDone(List<RecipeModel> mRecipe);
    }


    static void processMessage(final String message, final DelayerCallback callback,
                               @Nullable final RecipeIdlingResource idlingResource, Context context) {
        // The IdlingResource is null in production.
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        // Delay the execution, return message via callback.
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed( ()-> {

            if(idlingResource != null) {
                idlingResource.setIdleState(true);
            }

        }, DELAY_MILLIS);


//        try {
//            RecipeRepository.getInstance().getRecipeData(context);
//            RecipeRepository.getInstance().getRecipeSet(context);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
