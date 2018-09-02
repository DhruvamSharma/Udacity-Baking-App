package com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.detail.DetailActivity;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class RecipeDetail extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener {

    private RecipeModel recipeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //setup activity
        setupActivity();

        //Setting up fragment
        setUpFragment();



    }

    /**
     * A method that sets up the intent
     */
    private void setupActivity() {

        Intent intent = getIntent();

        if(intent.hasExtra(getPackageName())) {
            recipeData = Parcels.unwrap(intent.getParcelableExtra(getPackageName()));
        }



    }

    /**
     * A method that sets up the Fragments and pass the
     */
    private void setUpFragment() {
        //Constructing step list in phone mode
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();

        //setting arguments for the fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(getPackageName(), Parcels.wrap(recipeData));
        recipeDetailFragment.setArguments( bundle );

        //Fragment Manager set up
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_fm, recipeDetailFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(int position) {

        Intent intent = new Intent(this, DetailActivity.class);
        Parcelable wrapped = Parcels.wrap(recipeData);
        intent.putExtra(getPackageName(), wrapped);
        intent.putExtra("position", position);
        startActivity(intent);

    }
}
