package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class RouteDate extends AppCompatActivity {

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
            setContentView(R.layout.activity_route_date);

            Spinner routeSpinner = findViewById(R.id.spinnerRoute);
            Spinner durationSpinner = findViewById(R.id.spinnerDura);
            Button proceedBtn = findViewById(R.id.btnProceed);
            Button backBtn = findViewById(R.id.btnTryAgain);
            ImageView viewProfile = findViewById(R.id.btnProfile);

            viewProfile.setOnClickListener(v -> {
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                    }
            );
            proceedBtn.setOnClickListener(v -> {
                        startActivity(new Intent(getApplicationContext(), Payment.class));
                    }
            );
            backBtn.setOnClickListener(v -> {
                        finish(); // close the current activity
                    }
            );

            // Define an array of route names
            String[] routesNames = {"Colombo - Kandy", "Galle - Unawatuna", "Nuwara Eliya - Ella", "Trincomalee - Jaffna", "Sigiriya - Dambulla", "Matara - Mirissa", "Bentota - Hikkaduwa", "Mount Lavinia - Negombo", "Wadduwa - Kalutara", "Galle Face - Pettah"};

            // Create an ArrayAdapter using the route names array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, routesNames);

            // Set the layout style for the ArrayAdapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Set the adapter for the spinner
            routeSpinner.setAdapter(adapter);



            // Define an array of time periods
            String[] timePeriods = {"1 Month", "2 Months", "6 Months", "1 Year"};

            // Create an ArrayAdapter using the time periods array and a default spinner layout
            ArrayAdapter<String> adapterDuration = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timePeriods);

            // Set the layout style for the ArrayAdapter
            adapterDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Set the adapter for the spinner
            durationSpinner.setAdapter(adapterDuration);

            // TODO: perform payment calculation logic here
        }
    }
}