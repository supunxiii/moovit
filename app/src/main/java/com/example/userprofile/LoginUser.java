package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.core.content.ContextCompat;
public class LoginUser extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mbtnLogin;
    TextView mCreateUser, forgetPwdLink, welcomeText;
    ProgressBar loginProgressBar;
    FirebaseAuth fAuth;
    Switch nightDaySwitch;

    ImageView menuIcon, sunIcon, moonIcon;


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
            setContentView(R.layout.activity_login_user);

            mEmail = findViewById(R.id.txtEmail);
            mPassword = findViewById(R.id.txtPassword);
            mbtnLogin = findViewById(R.id.btnTryAgain);
            mCreateUser = findViewById(R.id.txtRegisterUser);
            fAuth = FirebaseAuth.getInstance();
            loginProgressBar = findViewById(R.id.progressBar2);
            forgetPwdLink = findViewById(R.id.txtForgotPwd);


            welcomeText = findViewById(R.id.textView);
            nightDaySwitch = findViewById(R.id.nightDay);
            sunIcon = findViewById(R.id.imageView4);
            moonIcon = findViewById(R.id.imageView5);
            menuIcon = findViewById(R.id.imageView7);


                nightDaySwitch.setChecked(false);
                nightDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            welcomeText.setTextColor(getResources().getColor(R.color.white));
                            sunIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            moonIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            menuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }else{
                            welcomeText.setTextColor(getResources().getColor(R.color.black));
                            sunIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black));
                            moonIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black));
                            menuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black));
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }

                    }
                });


            mbtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
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
                    loginProgressBar.setVisibility(View.VISIBLE);

                    // authenticating the user with email and password
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginUser.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else{
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                                Toast.makeText(LoginUser.this, "Invalid username or password. Please check again." + errorMessage, Toast.LENGTH_SHORT).show();
                                loginProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            });
            mCreateUser.setOnClickListener(v -> {
                        startActivity(new Intent(getApplicationContext(), RegisterUser.class));
                    }
            );
            forgetPwdLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText resetMail = new EditText(v.getContext());
                    AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                    passwordResetDialog.setTitle("Want to reset your password?");
                    passwordResetDialog.setMessage("Enter your email to get password reset link:");
                    passwordResetDialog.setView(resetMail);

                    passwordResetDialog.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // getting the email and send pwd reset link
                            String mail = resetMail.getText().toString();
                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(LoginUser.this, "A password reset link has been sent to your email.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginUser.this, "Whoops!!! Reset link couldn't be send. Try again later." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // redirecting back to log in
                        }
                    });
                    passwordResetDialog.create().show();
                }
            });
        }

    }
}