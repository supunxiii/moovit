package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView viewProfile, sunIcon, moonIcon;
    Button viewPasses, viewOldPasses, logOut;
    TextView busPassText;
    Switch nightDaySwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        logOut = findViewById(R.id.btnLogOut);
        viewProfile = findViewById(R.id.btnProfile);
        viewPasses = findViewById(R.id.btnNewPasses);
        viewOldPasses = findViewById(R.id.btnOldPasses);
        busPassText = findViewById(R.id.textView);
        sunIcon = findViewById(R.id.imageView4);
        moonIcon = findViewById(R.id.imageView5);



        viewProfile.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        });

        viewPasses.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RouteDate.class));
        });

        logOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginUser.class));
            finish();
            Toast.makeText(this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
        });

        viewOldPasses.setOnClickListener(v -> {
            openDownloadDirectory();
        });
    }

    private void openDownloadDirectory() {
        // Get the path to the external storage Download directory
        File downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Create an intent to open the download directory
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(downloadDirectory.getPath());
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "View Old Passes"));
    }

    @Override
    public void onBackPressed() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }
}
