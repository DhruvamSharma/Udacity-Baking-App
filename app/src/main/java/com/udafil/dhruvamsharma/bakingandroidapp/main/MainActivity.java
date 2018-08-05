package com.udafil.dhruvamsharma.bakingandroidapp.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;

import com.squareup.leakcanary.LeakCanary;
import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private RecipeListAdapter mAdapter;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*
        //LeakCanary setup to see the leaks in the application
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());*/


        //Viewmodel setup for the MainActivity
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init();

        //setting up the views in the activity
        setupActivity();

        //Setting up animations
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

    }



    /**
     * MainActivity setup method that displays data on the views
     */
    private void setupActivity() {

        //Getting data from the view model
        RecipeModel[] recipeData = viewModel.getRecipeData();

        //Setting up the recycler view
        mAdapter = new RecipeListAdapter(recipeData, this);

        binding.mainRecipeListRv.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.mainRecipeListRv.setLayoutManager(manager);







    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);


        getWindow().setReenterTransition(slide);
    }

}
