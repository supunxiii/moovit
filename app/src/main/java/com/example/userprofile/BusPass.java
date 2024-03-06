package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BusPass extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    TextView bPassUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_bus_pass);

        TextView bPassRoute, bPassDuration, bPassNIC, bPassPayment;
        Button btnPassDownload;

        bPassRoute = findViewById(R.id.passRoute);
        bPassDuration = findViewById(R.id.passDuration);
        bPassNIC = findViewById(R.id.passNIC);
        bPassPayment = findViewById(R.id.passPayment); // will be used after the payment logic has been configured
        btnPassDownload = findViewById(R.id.btnDownloadPass);

        String NICPass = getIntent().getStringExtra("keyNIC");
        String RoutePass = getIntent().getStringExtra("keyRoute");
        String DurationPass = getIntent().getStringExtra("keyDuration");

        bPassNIC.setText(NICPass);
        bPassRoute.setText(RoutePass);
        bPassDuration.setText(DurationPass);

        firebaseFirestore = FirebaseFirestore.getInstance();
        bPassUsername = findViewById(R.id.passUsername);
        // get the current user's ID
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        // fetch the username from Firestore
        firebaseFirestore.collection("moovit_users").document(userID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        bPassUsername.setText(username);
                    }
                })
                .addOnFailureListener(e -> {
                    bPassUsername.setText("Couldn't receive your username. Try again some other time.");
                });

        // Set a click listener for the btnDownloadPass button
        btnPassDownload.setOnClickListener(v -> {
            // Capture the screen as a bitmap
            Bitmap screenshotBitmap = captureScreen();

            // Save the bitmap as a PNG file
            saveBitmapAsPNG(screenshotBitmap);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }

    private Bitmap captureScreen() {
        // get the root view of the activity
        View rootView = getWindow().getDecorView().getRootView();

        // create a bitmap with the same size as the root view
        Bitmap screenshotBitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);

        // create a canvas using the bitmap
        Canvas canvas = new Canvas(screenshotBitmap);

        // draw the root view onto the canvas
        rootView.draw(canvas);

        return screenshotBitmap;
    }

    private void saveBitmapAsPNG(Bitmap bitmap) {
        // get the directory where the screenshot will be saved
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // create a filename for the screenshot
        String filename = "Moovit_Pass.png";

        // create a file object with the directory and filename
        File file = new File(directory, filename);

        try {
            // create a file output stream
            FileOutputStream outputStream = new FileOutputStream(file);

            // compress the bitmap as PNG and write it to the output stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            // flush and close the output stream
            outputStream.flush();
            outputStream.close();

            // display a toast or perform any other desired action to indicate that the screenshot has been saved
            Toast.makeText(this, "Your Pass Has Been Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // handling the exception
        }
    }
}
