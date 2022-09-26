package com.example.musicplayerapp.utils;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;

import com.google.api.services.drive.DriveScopes;


public class GoogleApiClientsUtils {

    private static GoogleApiClientsUtils INSTANCE = null;
    private static GoogleSignInOptions googleSignInOptions = null;

    public static GoogleApiClientsUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GoogleApiClientsUtils();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public GoogleSignInOptions getGoogleSignInOptions() {
        if (googleSignInOptions == null) {
            googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestProfile()
                    .requestScopes(new Scope(DriveScopes.DRIVE))
                    .requestEmail().build();
        }
        return googleSignInOptions;
    }
}
