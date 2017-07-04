package com.Mobbikart.AnujBansal.newapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.HomePage.HomeScreen;
import com.Mobbikart.AnujBansal.newapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class LoginHome extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    private static final int REQ_CODE= 9001;

    CallbackManager callbackManager;
    LoginButton loginButton;
    private SignInButton GoogleLogin;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login_home);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.facebook);
        loginButton.setOnClickListener(this);

        GoogleLogin= (SignInButton) findViewById(R.id.gmail);
        GoogleLogin.setOnClickListener(this);

        GoogleSignInOptions googlesignInoptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleApiClient= new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googlesignInoptions).build();
    }


    public void move (View view){
       signOut();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gmail:
            GoogleSignIn();
            break;

            case R.id.facebook:
            FbSignin();
            break;
        }
    }

    private void FbSignin(){

        FacebookSdk.isInitialized();

        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile prof= Profile.getCurrentProfile();
                String fbname= prof.getName().toString();
               // String fbemail= prof.;
                String fbimg= prof.getProfilePictureUri(100, 100).toString();

                Intent fblogin= new Intent(getApplicationContext(),HomeScreen.class);
                fblogin.putExtra("fbusername", fbname);
                //fblogin.putExtra("fbemail",fbemail);
                fblogin.putExtra("fbprof_pic", fbimg);
                startActivity(fblogin);
                Toast.makeText(getApplicationContext(),"welcome to Mobbikart",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"error logging in, check your internet", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void GoogleSignIn() {
        Intent intent= Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
        showProgressDialog();

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                        Toast.makeText(getApplicationContext(),"user logged out", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "login failed",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
       // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            HandleResult(result);
        }

    }

    private void HandleResult(GoogleSignInResult result) {
        if(result.isSuccess())
        {
           GoogleSignInAccount account = result.getSignInAccount();
            String name= account.getDisplayName().toString();
            String email= account.getEmail().toString();
            String img= account.getPhotoUrl().toString();
            //Glide.with(this).load(img_url).into(prof_pic);
            updateUI(true);
           Intent in= new Intent(getApplicationContext(),HomeScreen.class );
           in.putExtra("username", name);
           in.putExtra("email",email);
           in.putExtra("prof_pic", img);
           startActivity(in);
           hideProgressDialog();
        }
        else
            Toast.makeText(getApplicationContext(),"login failed", Toast.LENGTH_LONG).show();
    }

   private void updateUI(boolean loginHome) {
        if(loginHome) {
            //do something
            //loginButton.setVisibility(View.GONE);
        }
            else
                loginButton.setVisibility(View.VISIBLE);
                //do somthing
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            //mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
