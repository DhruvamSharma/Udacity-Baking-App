package com.udafil.dhruvamsharma.bakingandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.leakcanary.LeakCanary;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());


    }
}
