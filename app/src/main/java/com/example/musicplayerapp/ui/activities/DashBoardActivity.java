package com.example.musicplayerapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.musicplayerapp.R;
import com.example.musicplayerapp.databinding.ActivityDashboardBinding;
import com.example.musicplayerapp.utils.GoogleApiClientsUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class DashBoardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private GoogleSignInAccount lastSignedInAccount;
    private GoogleSignInOptions options;
    private GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        initGoogleCLient();
        initGoogleAuth();
    }

    private void initGoogleCLient() {
        options = GoogleApiClientsUtils.getInstance().getGoogleSignInOptions();
        client = GoogleSignIn.getClient(getApplicationContext(), options);
    }

    private void initGoogleAuth() {
        lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (lastSignedInAccount == null) {
            redirectToMain();
        }

        binding.btnSignOut.setOnClickListener(view -> {
            client.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    redirectToMain();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DashBoardActivity.this, "Failed to sign out", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void redirectToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}