package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TermsConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()) {
            // The device is offline.
            setContentView(R.layout.activity_fallback_screen);
        } else {
            // The device is online.
            setContentView(R.layout.activity_terms_conditions);
            Button btnGood, btnBad;
            btnBad = findViewById(R.id.btnNotGood);
            btnGood = findViewById(R.id.btnTryAgain);
            btnBad.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            });

            btnGood.setOnClickListener(v -> {
                finish(); // close the current activity
            });
        }
    }
}
