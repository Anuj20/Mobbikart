package com.Mobbikart.AnujBansal.newapp.Product;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductMain extends AppCompatActivity {

    ProgressDialog Progress;
    List<Product> mDataset;
    public static final String JSON_URL= "http://mobbikart.com/MobbiApp/getCategoryProducts.php?category=" ;
    private RecyclerView productrecycler;
    private GridLayoutManager mGridLayout;
    private ProductAdapter pda;
    String ChildHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);

        Progress= new ProgressDialog(this);
        Progress.setMessage("Loading Products...");

        mDataset= new ArrayList<>();
        productrecycler= (RecyclerView) findViewById(R.id.recycler_product);
        productrecycler.setHasFixedSize(true);

        pda= new ProductAdapter(mDataset, getApplicationContext());
        mGridLayout= new GridLayoutManager(getApplicationContext(), 2);
        productrecycler.setLayoutManager(mGridLayout);



        productrecycler.setAdapter(pda);

        ChildHeader= getIntent().getExtras().getString("childHeader");
        //JSON_URL= "http://192.168.1.5/db/getdata.php?category="+ChildHeader;

        Fetchdata();

       // Uri uri=Uri.parse("R.drawable.image");

       // String  imageResource = String.valueOf(getResources().getIdentifier(ba, null, getPackageName()));
        //Drawable image = getResources().getDrawable(imageResource);

       // String image2 = String.valueOf(getResources().getIdentifier(ba, null, getPackageName()));


/*        Product prod = new Product("Sandisk", String.valueOf(R.drawable.a), "500", " this is Sandisk Description");
        mDataset.add(prod);

        prod = new Product("henko",String.valueOf(R.drawable.b), "1000", "this is henko Description");
        mDataset.add(prod);

        prod = new Product("tide", "drawable/c", "2000", " this is tide Description");
        mDataset.add(prod);

        prod = new Product("whirlpool", "drawable/d", "3000", " this is whirlpool Description");
        mDataset.add(prod);

        prod = new Product("pulse", "drawable/e", "4000", " this is pulse Description");
        mDataset.add(prod);

        prod = new Product("wwe raw", "drawable/f", "5000", " this is wwe raw Description");
        mDataset.add(prod);

        prod = new Product("smackdown live", "drawable/g", "6000", " this is Smackdown live Description");
        mDataset.add(prod);

        prod = new Product("love", "drawable/h", "7000", " this is love Description");
        mDataset.add(prod);*/

      //  pda.notifyDataSetChanged();
    }

    //http://192.168.1.5/db/getdata.php?category=Rice

    private void Fetchdata() {

        Progress.show();
        StringRequest stringRequest= new StringRequest(Request.Method.GET, JSON_URL+ChildHeader, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //success
                //JSONParse pj = new JSONParse(response);
                //pj.parseJSON();
               //mDataset= pj.getMovies();
               // Progress.dismiss();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("Product");

                    Progress.dismiss();
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject obj= jsonArray.getJSONObject(i);
                        Product productlist= new Product(obj.optString("Name"),
                                obj.optString("Image"), obj.optString("price"),obj.optString("Description") );
                        mDataset.add(productlist);
                    }
                    //productrecycler.setAdapter(pda);
                    pda.notifyDataSetChanged();

                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(), " catch error"+ e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("response", Log.getStackTraceString(e));
                }
               // pda.notifyDataSetChanged();
                Progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Progress.dismiss();
                Toast.makeText(ProductMain.this, "error parsing "+ error.toString(),
                        Toast.LENGTH_LONG).show();
                Log.e("errorResponse", Log.getStackTraceString(error));
            }
        }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
