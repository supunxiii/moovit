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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Payment extends AppCompatActivity {

    boolean isQRDownloadClicked = false;

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
            setContentView(R.layout.activity_payment);
            TextView viewTerms, edTxRoute, edTxDuration;
            ImageView viewUserProfile;
            Button btnPayConfirm, btnQRDownload;
            EditText edTxNIC, edTxCardNumber, edTxValidThru, edTxCVV;
            viewUserProfile = findViewById(R.id.btnProfilePayment);
            viewTerms = findViewById(R.id.txtViewTerms);
            btnPayConfirm = findViewById(R.id.btnPay);
            btnQRDownload = findViewById(R.id.btnDownload);
            edTxNIC = findViewById(R.id.txtNIC);
            edTxCardNumber = findViewById(R.id.txtCardNumber);
            edTxValidThru = findViewById(R.id.txtValidThru);
            edTxCVV = findViewById(R.id.txtCVV);
            edTxRoute = findViewById(R.id.txtRoute);
            edTxDuration = findViewById(R.id.txtDuration);

            btnPayConfirm.setEnabled(false); // disable the button initially
            btnPayConfirm.setBackgroundResource(R.drawable.button_disabled_background);

            viewTerms.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), TermsConditions.class));
            });

            viewUserProfile.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            });

            btnQRDownload.setOnClickListener(v -> {
                isQRDownloadClicked = true;
                btnPayConfirm.setEnabled(true);
                btnPayConfirm.setBackgroundResource(R.drawable.button_enabled_background);
            });

            btnPayConfirm.setOnClickListener(v -> {
                if (isQRDownloadClicked) {
                    String NIC = edTxNIC.getText().toString();
                    String Route = edTxRoute.getText().toString();
                    String Duration = edTxDuration.getText().toString();

                    Intent intent = new Intent(Payment.this, BusPass.class);
                    intent.putExtra("keyNIC", NIC);
                    intent.putExtra("keyRoute", Route);
                    intent.putExtra("keyDuration", Duration);
                    startActivity(intent);

                }
            });
        }
    }
}
