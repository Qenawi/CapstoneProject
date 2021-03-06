/**
 * Copyright Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.qenawi.ttasker_capstone.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.modle.Usersdataitem;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "SignInActivity525";
    private static final int RC_SIGN_IN = 9001;
    Usersdataitem user;
    private ProgressBar PG;
    private String push_key;
    private SignInButton mSignInButton;
    private int flag = 0;
    private GoogleApiClient mGoogleApiClient;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference dp_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        PG = (ProgressBar) findViewById(R.id.progressBar2);
        // Assign fields
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);

        // Set click listeners
        mSignInButton.setOnClickListener(this);
        // Configure Google Sign In            EROR ->requestIdToken(getString(R.string.default_web_client_id))
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        //---------------------
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.v(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, R.string.GSE, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("AIAAWBUYB", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Log.w("AIAAWBUYB", "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.Auf,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            PG.setVisibility(View.VISIBLE);
                            add_user_data();
                        }
                    }
                });
    }

    public void add_user_data() {
        Log.v("AIAAWBUYB", "add u d");
        dp_ref = FirebaseDatabase.getInstance().getReference().child("users");
        try {
            //   Toast.makeText(SignInActivity.this, "" + mFirebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            Query query = dp_ref.orderByChild("email").equalTo(mFirebaseAuth.getCurrentUser().getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    flag = (int) dataSnapshot.getChildrenCount();
                    if (0 == flag) {
                        Log.v("AIAAWBUYB", "add u d 1");
                        add_user_helper();
                        // create ne pop in test with email
                    }//if
                    else {
                        Log.v("AIAAWBUYB", "add u d 2");
                        finishX();
                        Toast.makeText(SignInActivity.this, R.string.wb, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// add_user_data

    void add_user_helper() {
        Log.v("AIAAWBUYB", "add u d 3");
        try {

            user = new Usersdataitem(mFirebaseAuth.getCurrentUser().getEmail(), mFirebaseAuth.getCurrentUser().getDisplayName(), mFirebaseAuth.getCurrentUser().getPhotoUrl().toString());
            String key = dp_ref.push().getKey();
            setSTored(key);
            dp_ref.child(key).setValue(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("AIAAWBUYB", "add u d 4");
        PG.setVisibility(View.GONE);
        //   creat_user_friend_list();// creat test
        finishX();

    }

    void finishX() {
        PG.setVisibility(View.GONE);
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    void setSTored(String e) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("eTa", e).apply();
    }

}//clas
