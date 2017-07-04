package com.Mobbikart.AnujBansal.newapp.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;


public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView Name, Email;
    ImageView ProfilePic;

    GoogleApiClient googleApiClient;

    private RecyclerView mRecyclerView;
    //private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridlayout;
    private CategoriesAdapter Cda;

    //private List<Categories> productlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView= (RecyclerView) findViewById(R.id.recy_category);

       // mLinearLayoutManager= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mGridlayout= new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(mGridlayout);

        final List<Categories> productlist = new ArrayList<>();
        Cda= new CategoriesAdapter(this, productlist);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(Cda);
        Cda.notifyDataSetChanged();

        String a = "@drawable/mobbik";
        String ba = "@drawable/b";
        String c = "@drawable/c";
        String d = "@drawable/d";
        String e = "@drawable/e";
        String f = "@drawable/f";

        int image1 = getResources().getIdentifier(a, null, getPackageName());
        int image2 = getResources().getIdentifier(ba, null, getPackageName());
        int image3 = getResources().getIdentifier(c, null, getPackageName());
        int image4 = getResources().getIdentifier(d, null, getPackageName());
        int image5 = getResources().getIdentifier(e, null, getPackageName());
        int image6 = getResources().getIdentifier(f, null, getPackageName());

        Categories cat = new Categories("food and agro", image1);
        productlist.add(cat);
        cat = new Categories("biotech", image2);
        productlist.add(cat);
        cat = new Categories("Solar", image3);
        productlist.add(cat);
        cat = new Categories("Handicrafts", image4);
        productlist.add(cat);
        cat = new Categories("Books", image5);
        productlist.add(cat);
        cat = new Categories("Music", image6);
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
        String getName, getEmail, getPicture;
        if(b!=null)
        {
            if(iin.hasExtra("username") && iin.hasExtra("email") && iin.hasExtra("prof_pic")) {

                getName = iin.getStringExtra("username");
                Name.setText(getName);
                getEmail= iin.getStringExtra("email");
                Email.setText(getEmail);
                getPicture= iin.getStringExtra("prof_pic");
                Glide.with(this).load(getPicture).into(ProfilePic);

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
                Email.setVisibility(View.GONE);
                getPicture = iin.getStringExtra("fbprof_pic");
                Glide.with(this).load(getPicture).into(ProfilePic);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private int preparedata() {
        return  2;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    //navigation menu create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent iin= getIntent();


        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_Food_agro) {

        } else if (id == R.id.nav_Biotech) {

        } else if (id == R.id.nav_solar) {

        }
         else if (id == R.id.nav_handicrafts) {

        } else if (id == R.id.nav_electronics) {

        } else if (id == R.id.nav_lifestyle) {

        } else if (id == R.id.nav_books) {

        } else if (id == R.id.nav_music) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout)
        {
            if(iin.hasExtra("username") && iin.hasExtra("email") && iin.hasExtra("prof_pic")) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // [START_EXCLUDE]
                                //updateUI(false);
                                // [END_EXCLUDE]
                                Toast.makeText(getApplicationContext(), "user logged out", Toast.LENGTH_LONG).show();
                            }
                        });
            }
            else if(iin.hasExtra("fbusername")  && iin.hasExtra("prof_pic")){
                //logout from fb
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
