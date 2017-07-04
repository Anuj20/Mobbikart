package com.Mobbikart.AnujBansal.newapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.Mobbikart.AnujBansal.newapp.Login.LoginHome;
import com.Mobbikart.AnujBansal.newapp.redundantActivities.Cart;
import com.Mobbikart.AnujBansal.newapp.redundantActivities.Download;
import com.Mobbikart.AnujBansal.newapp.redundantActivities.First;
import com.Mobbikart.AnujBansal.newapp.redundantActivities.Prod;
import com.Mobbikart.AnujBansal.newapp.redundantActivities.Products;

import java.util.List;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,First.OnFragmentInteractionListener,Products.OnFragmentInteractionListener,SearchView.OnQueryTextListener {

    private List<Prod> mProductList;
    AlertDialog.Builder builder;
    final static String urlAddress="http://192.168.1.7/newapp/json_get_data.php";
    public static final String ID_Extra= "com.example.rohan.newapp.Home._ID";
    ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchResults = (ListView)findViewById(R.id.ListViewCatalog);


        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fragment fragment = null;
        Class fragmentClass = null;

        /*fragmentClass = First.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Obtain a reference to the product catalog
        new Download(Home.this, urlAddress, (ListView)mProductList).execute();


        // Create the list
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        assert listViewCatalog != null;
//        listViewCatalog.setAdapter((ListAdapter) mlist);
//        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false,false));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//           new ProductDetails(getBaseContext(), mProductList.get(position).productID, (ArrayList<Prod>) mProductList, position);
//           Intent i = new Intent(getBaseContext(),ProductDetails.class);
//           startActivity(i);

//                new ProductDetails(getBaseContext(),mProductList.get(position).productID,listViewCatalog,position);
//                Intent i = new Intent(getBaseContext(),ProductDetails.class);
//                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, mProductList.get(position).productID);
//                i.putExtra(ID_Extra,position);
//                startActivity(i);
            }
        });

        Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
        viewShoppingCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent viewShoppingCartIntent = new Intent(getBaseContext(),Cart.class);
                startActivity(viewShoppingCartIntent);
            }
        });
    }

    private void updateUI(Home home) {

    }

    /*private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            String message = getIntent().getStringExtra("data");
           mProductList = getIntent().getParcelableExtra(message);
            //mProductList=getIntent().getIntegerArrayListExtra("result");


            // Create the list
            ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
            listViewCatalog.setAdapter((ListAdapter) mProductList);
//        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false,false));

            listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetails.class);
                    productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, mProductList.get(position).productID);
                    startActivity(productDetailsIntent);
                }
            });

            Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
            viewShoppingCart.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Intent viewShoppingCartIntent = new Intent(getBaseContext(),Cart.class);
                    startActivity(viewShoppingCartIntent);
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }*/

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String loginmsg=(String)msg.obj;
            if(loginmsg.equals("NOTSUCCESS")) {
                Intent intent = new Intent(getApplicationContext(), LoginHome.class);
                intent.putExtra("LoginMessage", "Logged Out");
                startActivity(intent);
                removeDialog(0);
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_id).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(this, Home.class)));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                //Toast.makeText(activity, String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 3)
                {

           //        MyAsyncTask m= (MyAsyncTask) new MyAsyncTask().execute(newText);
                    new Download(Home.this, urlAddress, (ListView)searchResults).execute();
//                    searchResults = (ListView) getIntent().getParcelableExtra("ID_Extra");
//                    searchResults.setVisibility(View.VISIBLE);

                }
                else
                {

                   // searchResults.setVisibility(View.INVISIBLE);
                }



                return false;
            }


        });
        return true;
    }








    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.shopping_cart_id:Intent i = new Intent(getApplicationContext(),Cart.class);
                startActivity(i);
        }

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;
        switch(item.getItemId()){


            case R.id.nav_home:fragmentClass = First.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Home");
                break;

            case R.id.nav_products:
                fragmentClass = Products.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Products");

                break;
            case R.id.nav_log:
                builder=new AlertDialog.Builder(this);
                builder.setTitle("Do you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences
                        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
                        SharedPreferences.Editor editor=mPreferences.edit();
                        editor.remove("UserName");
                        editor.remove("PassWord");
                        editor.commit();
                        Message myMessage=new Message();
                        myMessage.obj="NOTSUCCESS";

                        handler.sendMessage(myMessage);
                        finish();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


        }
        // Handle navigation view item clicks here.
      /*  int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_products) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_log) {



        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

