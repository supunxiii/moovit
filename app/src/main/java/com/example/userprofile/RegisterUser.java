package com.example.userprofile;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterUser extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mUserName, mEmail, mPhone, mPassword;
    Button mRegisterBtn;
    TextView mLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

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
            setContentView(R.layout.activity_register_user);

            mUserName = findViewById(R.id.txtUsername);
            mEmail = findViewById(R.id.txtEmail);
            mPhone = findViewById(R.id.txtPhone);
            mPassword = findViewById(R.id.txtPassword);
            mRegisterBtn = findViewById(R.id.btnTryAgain);
            mLogin = findViewById(R.id.txtForgotPwd);

            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            progressBar = findViewById(R.id.progressBar);
            if (fAuth.getCurrentUser() != null) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            mRegisterBtn.setOnClickListener(v -> {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String username = mUserName.getText().toString();
                String phoneNumber = mPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required!");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password must be at least 6 characters long.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterUser.this, "Account Created", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid(); // getting the current user ID and assigning it to userID
                                DocumentReference documentReference = fStore.collection("moovit_users").document(userID);
                                Map<String, Object> user = new HashMap<>();  // storing user data in FireStore using a hashmap
                                user.put("username", username);
                                user.put("email", email);
                                user.put("phone", phoneNumber);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: user profile has been created for "+userID);
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                                Toast.makeText(RegisterUser.this, "Oops! An Error Occurred! " + errorMessage, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            });
            mLogin.setOnClickListener(v -> {
                        startActivity(new Intent(getApplicationContext(), LoginUser.class));
                    }
            );
        }

    }
}
