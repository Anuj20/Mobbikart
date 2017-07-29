package com.Mobbikart.AnujBansal.newapp.Login.PhpPackages.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.HomePage.HomeScreen;
import com.Mobbikart.AnujBansal.newapp.Login.PhpPackages.app.AppConfig;
import com.Mobbikart.AnujBansal.newapp.Login.PhpPackages.app.AppController;
import com.Mobbikart.AnujBansal.newapp.Login.PhpPackages.helper.SQLiteHandler;
import com.Mobbikart.AnujBansal.newapp.Login.PhpPackages.helper.SessionManager;
import com.Mobbikart.AnujBansal.newapp.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginHome extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = Register.class.getSimpleName();

    GoogleApiClient googleApiClient;
    private static final int REQ_CODE= 9001;

    CallbackManager callbackManager;
    LoginButton loginButton;
    private SignInButton GoogleLogin;
    Button phpLogin;

    EditText loginemail, loginpassword;
    TextView signup;

    ProgressDialog mProgressDialog;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login_home);

        loginemail= (EditText) findViewById(R.id.login_email);
        loginpassword= (EditText) findViewById(R.id.login_pass);
        signup= (TextView) findViewById(R.id.signuptext);

        phpLogin= (Button) findViewById(R.id.loginButton);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginHome.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        phpLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = loginemail.getText().toString().trim();
                String password = loginpassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    NativeLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Link to Register Screen
        signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register.class);
                startActivity(i);
                finish();
            }
        });

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

            case R.id.loginButton:
                String email = loginemail.getText().toString().trim();
                String password = loginpassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    NativeLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
        }
    }

    private void  NativeLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String contact=user.getString("contact");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email,contact, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(LoginHome.this,
                                HomeScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                "json error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
                //String email= prof.getProperty("email").toString()
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
