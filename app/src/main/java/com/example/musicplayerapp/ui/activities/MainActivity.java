package com.example.musicplayerapp.ui.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.musicplayerapp.R;
import com.example.musicplayerapp.databinding.ActivityMainBinding;
import com.example.musicplayerapp.utils.GoogleApiClientsUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GoogleSignInClient client;

    private ActivityResultLauncher<Intent> mStartIntent =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        GoogleSignInResult loginResult = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
                        if(loginResult.isSuccess()) {
                            redirectToDashBoard();
                        }
                    }

                }
            });

    private GoogleApiClient.OnConnectionFailedListener signInFailedListener = new GoogleApiClient
            .OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initGoogleSignIn();
        initwidgets();
    }

    private void initwidgets() {
        uiClickEvents();
    }

    private boolean isUserSignedIn() {
        GoogleSignInAccount signedInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        return signedInAccount != null;
    }

    private void uiClickEvents() {
        binding.btnSignIn.setOnClickListener(view -> {
            startGoogleSignInProcess();
        });
    }

    private void startGoogleSignInProcess() {
//        Intent intent = Auth.GoogleSignInApi.getSignInIntent(client);
        Intent intent = client.getSignInIntent();
        mStartIntent.launch(intent);
    }

    private void initGoogleSignIn() {

        if (!isUserSignedIn()) {
            GoogleSignInOptions options = GoogleApiClientsUtils.getInstance().getGoogleSignInOptions();
        /*client = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, signInFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();*/

            client = GoogleSignIn.getClient(getApplicationContext(), options);
        } else {
            redirectToDashBoard();
        }

    }

    private void redirectToDashBoard() {
        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
        startActivity(intent);
        finish();
    }
}