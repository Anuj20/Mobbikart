package com.Mobbikart.AnujBansal.newapp.login_Register;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.HomePage.HomeScreen;
import com.Mobbikart.AnujBansal.newapp.R;
import com.android.volley.AuthFailureError;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginHome extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    //google login
    GoogleApiClient googleApiClient;
    private static final int REQ_CODE= 1;

    //fb Login
    CallbackManager callbackManager;
    LoginButton loginButton;
    private SignInButton GoogleLogin;

    //Native login
    EditText loginemail, loginpassword;
    Button NativeLogin;
    String email, password;

    TextView signup;

    ProgressDialog mProgressDialog;

    AlertDialog.Builder builder;

    String login_url= "http://mobbikart.com/MobbiApp/login.php";

    Sessionmanagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());

        session = new Sessionmanagement(getApplicationContext());

        setContentView(R.layout.activity_login_home);

        loginemail= (EditText) findViewById(R.id.login_email);
        loginpassword= (EditText) findViewById(R.id.login_pass);
        signup= (TextView) findViewById(R.id.signuptext);

        NativeLogin= (Button) findViewById(R.id.loginButton);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading... please Wait");

        builder= new AlertDialog.Builder(this);

        // Login button Click Event
        NativeLogin.setOnClickListener(this);

        // Link to Register Screen
        signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                finish();
                startActivity(i);
            }
        });

       // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.facebook);
        loginButton.setOnClickListener(this);

        GoogleLogin= (SignInButton) findViewById(R.id.gmail);
        GoogleLogin.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleApiClient= new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this,
                new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "connection failed!", Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
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
                NativeLogin();
        }
    }

    private void NativeLogin() {
        email  = loginemail.getText().toString().trim();
        password= loginpassword.getText().toString().trim();

        // Check for empty data in the form
        if (email.isEmpty() || password.isEmpty()) {
            //NativeLogin(email, password);
            // Prompt user to enter credentials
            builder.setTitle("Something went wrong");
            displayAlert("Please enter valid credentials!");
            Toast.makeText(getApplicationContext(),
                    "Please enter all the credentials!", Toast.LENGTH_LONG).show();
        } else {
            showProgressDialog();
            // login user
            StringRequest stringRequest= new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        JSONObject jsonObject= jsonArray.getJSONObject(0);
                        String code= jsonObject.getString("code");
                        if(code.equals("login failed")){
                            builder.setTitle("Login Error");
                            displayAlert(jsonObject.getString("message"));
                        }
                        else {

                            String name=  jsonObject.getString("Name");
                            String email= jsonObject.getString("Email");
                            session.createLoginSession(name, email);
                            Intent intent= new Intent(LoginHome.this, HomeScreen.class);
                            intent.putExtra("clientname",name);
                            intent.putExtra("emailid",email);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Error "+ error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params= new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            LogRegSingleton.getInstance(LoginHome.this).addToRequestque(stringRequest);

           // Toast.makeText(getApplicationContext(),"You almost logged in", Toast.LENGTH_LONG).show();
        }

    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               loginemail.setText("");
                loginpassword.setText("");
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

   /* private void  NativeLogin(final String email, final String password) {
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
    }*/


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

                session.createLoginSession(fbname, "");
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
            //String img= account.getPhotoUrl().toString();

            session.createLoginSession(name, email);
            String img;
            if(account.getPhotoUrl()==null)
            {
                img= ("@drawable/mobbik");
            }else{
               img=account.getPhotoUrl().toString();
            }



            //Glide.with(this).load(img_url).into(prof_pic);
            hideProgressDialog();
           Intent in= new Intent(getApplicationContext(),HomeScreen.class );
           in.putExtra("username", name);
           in.putExtra("email",email);
           in.putExtra("prof_pic", img);
           startActivity(in);
        }
        else
            Toast.makeText(getApplicationContext(),"login failed", Toast.LENGTH_LONG).show();
        hideProgressDialog();
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
            mProgressDialog.show();
            //mProgressDialog.setMessage(getString(R.string.loading));
            }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
