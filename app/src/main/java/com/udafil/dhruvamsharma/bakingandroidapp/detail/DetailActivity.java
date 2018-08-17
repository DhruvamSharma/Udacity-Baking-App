package com.udafil.dhruvamsharma.bakingandroidapp.detail;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;

import com.udafil.dhruvamsharma.bakingandroidapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //Setting up animations
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpWindowsAnimation();
        }

    }

    //Method to be called only when viewed on Lollipop and gretaer devices.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpWindowsAnimation() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setEnterTransition(slide);

        getWindow().setReturnTransition(slide);
    }
}
