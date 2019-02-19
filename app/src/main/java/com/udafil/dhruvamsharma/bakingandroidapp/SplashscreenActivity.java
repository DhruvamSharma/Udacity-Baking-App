package com.udafil.dhruvamsharma.bakingandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udafil.dhruvamsharma.bakingandroidapp.data.RecipeRepository;
import com.udafil.dhruvamsharma.bakingandroidapp.main.MainActivity;

import java.io.IOException;
import java.util.Set;

public class SplashscreenActivity extends AppCompatActivity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Getting shared preference
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.RECIPE_DATA_PREFERENCE_FILE), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //if the shared preferences file is null, I need to first get the data and meanwhile
        //show the splash screen, but if there is data, we just move to MainActivity

        if(sharedPreferences.getBoolean("initialStart", true)) {
            try {

                editor.putBoolean("initialStart", false);
                editor.apply();
                RecipeRepository.getInstance().getRecipeData(getApplication().getApplicationContext());
                Toast.makeText(this, "in splashscreen", Toast.LENGTH_SHORT).show();
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
                new Handler().postDelayed(() -> {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashscreenActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }, SPLASH_TIME_OUT);
            } catch (IOException e) {
                e.printStackTrace();
                finish();
            }

        } else {
            Intent i = new Intent(SplashscreenActivity.this, MainActivity.class);
            startActivity(i);
        }




    }
}
