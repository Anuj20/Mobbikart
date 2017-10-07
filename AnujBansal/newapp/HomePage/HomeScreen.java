package com.Mobbikart.AnujBansal.newapp.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.AboutUs.Aboutus;
import com.Mobbikart.AnujBansal.newapp.Cart.Cart;
import com.Mobbikart.AnujBansal.newapp.Categories.CategoryPage;
import com.Mobbikart.AnujBansal.newapp.R;
import com.Mobbikart.AnujBansal.newapp.login_Register.LogRegSingleton;
import com.Mobbikart.AnujBansal.newapp.login_Register.LoginHome;
import com.Mobbikart.AnujBansal.newapp.login_Register.Sessionmanagement;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView Name, Email;
    ImageView ProfilePic;

    Sessionmanagement session;

    GoogleApiClient googleApiClient;

    private RecyclerView mRecyclerView;
    //private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridlayout;
    private CategoriesAdapter Cda;
    String userinfo_url= "http://192.168.1.5/db/UserinfoStore.php";


    String sharedprefname, sharedprefemail;

    //private List<Categories> productlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new Sessionmanagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        // name
        sharedprefname = user.get(Sessionmanagement.KEY_NAME);
        // email
        sharedprefemail = user.get(Sessionmanagement.KEY_EMAIL);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleApiClient= new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this,
                new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "connection failed!", Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mRecyclerView= (RecyclerView) findViewById(R.id.recy_category);

       // mLinearLayoutManager= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mGridlayout= new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(mGridlayout);

        final List<Categories> productlist = new ArrayList<>();
        Cda= new CategoriesAdapter(this, productlist);
       // mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
       // mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(Cda);
        Cda.notifyDataSetChanged();

        Categories cat = new Categories("FOOD AND AGRO", R.drawable.foodagro);
        productlist.add(cat);
        cat = new Categories("BIOTECH", R.drawable.biotech);
        productlist.add(cat);
        cat = new Categories("SOLAR", R.drawable.solar);
        productlist.add(cat);
        cat = new Categories("HANDICRAFTS", R.drawable.handicrafts);
        productlist.add(cat);
        cat = new Categories("ELECTRONICS", R.drawable.electronics);
        productlist.add(cat);
        cat = new Categories("LIFESTYLE", R.drawable.lifestyle);
        productlist.add(cat);
        cat = new Categories("BOOKS", R.drawable.books);
        productlist.add(cat);
        cat = new Categories("MUSIC", R.drawable.music);
        productlist.add(cat);

        //declaring navigation drawer(important before declaring navigation Textviews and all)
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView =  navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
       // View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home_screen);
        //headerView.findViewById(R.id.navigation_header_text);

        //declaring widgets stored in navigation drawer
        Name= (TextView)headerView. findViewById(R.id.usrame);
       //Name.setText("Anuj");
        Email= (TextView)headerView.findViewById(R.id.emailaddress);
      // Email.setText("anujbansal2050@gmail.com");
        ProfilePic= (ImageView)headerView. findViewById(R.id.profilePic);

       Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String getName = null;
        String getEmail = null;
        String getPicture;
        if(b!=null)
        {
            if(iin.hasExtra("username") || iin.hasExtra("email") && iin.hasExtra("fbprof_pic") ) {

                //b.getString("username");
                getName = iin.getStringExtra("username");
                Name.setText(getName);
                getEmail= iin.getStringExtra("email");
                Email.setText(getEmail);
                getPicture= iin.getStringExtra("prof_pic");
                Glide.with(this).load(getPicture).into(ProfilePic);
                WelcomeToast();

                // getPicture= iin.getStringExtra("prof_pic");
                //Glide.with(this).load(getPicture).into(ProfilePic);
               /* String googleName = (String) b.get("username");
                Name.setText(googleName);

                String googleemail = (String) b.get("email");
                Email.setText(googleemail);

                String google_image_link = getIntent().getStringExtra("prof_pic");
                Glide.with(this).load(google_image_link).into(ProfilePic);*/
            }
            if(iin.hasExtra("fbusername")  && iin.hasExtra("fbprof_pic")){

                getName = iin.getStringExtra("fbusername");
                Name.setText(getName);
                //getEmail= iin.getStringExtra("fbemail");
                Email.setText("Developer@mobbikart.com");
                Email.setVisibility(View.GONE);
                getPicture = iin.getStringExtra("fbprof_pic");
                Glide.with(this).load(getPicture).into(ProfilePic);
                WelcomeToast();
            }
            if (iin.hasExtra("clientname") && iin.hasExtra("emailid")){
                getName= iin.getStringExtra("clientname");
                Name.setText(getName);
                getEmail= iin.getStringExtra("emailid");
                Email.setText(getEmail);
               // getPicture= iin.getStringExtra("prof_pic");
                Glide.with(this).load(R.drawable.mobbik).into(ProfilePic);
                WelcomeToast();
            }
        }

        final String finalGetName = getName;
        final String finalGetEmail = getEmail;
        StringRequest stringRequest= new StringRequest(Request.Method.POST, userinfo_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("name", finalGetName);
                params.put("email", finalGetEmail);
                return params;
            }
        };
        LogRegSingleton.getInstance(HomeScreen.this).addToRequestque(stringRequest);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void WelcomeToast() {
        Toast.makeText(getApplicationContext(), "Welcome to Mobbikart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedprefname!= null) {
            Name.setText(sharedprefname);
            Email.setText(sharedprefemail);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Name.setText(sharedprefname);
        Email.setText(sharedprefemail);
    }

    //navigation menu create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home_drawer, menu);
        return true;
    }

    //navigation menu populate
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.nav_cart:
                startActivity(new Intent(getApplicationContext(), Cart.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String cat_name= (String) item.getTitle();

        if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), Aboutus.class));
           return true;

        } else if (id == R.id.nav_Food_agro) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);

        } else if (id == R.id.nav_Biotech) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);

        } else if (id == R.id.nav_solar) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);
        }
         else if (id == R.id.nav_handicrafts) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);
        }
        else if (id == R.id.nav_electronics) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);
        }
        else if (id == R.id.nav_lifestyle) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);
        }
        else if (id == R.id.nav_books) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);
        }
        else if (id == R.id.nav_music) {
            Intent Inte= new Intent(getApplicationContext(), CategoryPage.class);
            Inte.putExtra("categoryname", cat_name);
            startActivity(Inte);
        }
        else if (id == R.id.nav_share) {

            //TODO put playstore link
        }
        else if (id == R.id.nav_logout)
        {
            session.logoutUser();
            //google sign in
            Auth.GoogleSignInApi.signOut(googleApiClient);
            //fb sign out
            LoginManager.getInstance().logOut();
            finish();
            startActivity(new Intent(HomeScreen.this, LoginHome.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
