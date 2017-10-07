package com.Mobbikart.AnujBansal.newapp.Categories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.HomePage.HomeScreen;
import com.Mobbikart.AnujBansal.newapp.Product.ProductMain;
import com.Mobbikart.AnujBansal.newapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class CategoryPage extends AppCompatActivity {

    private ExpandableListView expandableList;
    private ExpandableListAdapter listAdapter;
    private List<String> ListHeader;
    private HashMap<String, List<String>> ListChild;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        TextView cat = (TextView) findViewById(R.id.text_cat);

        title = getIntent().getExtras().getString("categoryname");
        cat.setText(title);

        expandableList = (ExpandableListView) findViewById(R.id.exp_lv);

        preparelistdata();

        listAdapter = new ExpandableListAdapter(getApplicationContext(), ListHeader, ListChild);
        expandableList.setAdapter(listAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

               String childTitle= ListChild.get(ListHeader.get(groupPosition)).get(childPosition);
                Intent inte= new Intent(getApplicationContext(), ProductMain.class);
                inte.putExtra("childHeader",childTitle);
                startActivity(inte);

                Toast.makeText(getApplicationContext(),
                        get(groupPosition) + " : " + childTitle ,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(this, HomeScreen.class);
        finish();
        startActivity(i);
    }


    private void preparelistdata() {
        ListHeader = new ArrayList<String>();
        ListChild = new HashMap<String, List<String>>();

        switch (title){
            case "FOOD AND AGRO":
                // Adding child data
                ListHeader.add("Food Products");
                ListHeader.add("Personal care");
                ListHeader.add("Agro Products");
                ListHeader.add("Nutraceuticals");
                ListHeader.add("Hybrid seeds");
                ListHeader.add("fertilizers & Pesticides");

                // Adding child data
                List<String> foodprod = new ArrayList<String>();
                foodprod.add("Rice");
                foodprod.add("pen drives");
                foodprod.add("Pulses");
                foodprod.add("snacks");

                List<String> personalcare = new ArrayList<String>();
                personalcare.add("Toothpaste");
                personalcare.add("Shampoo");
                personalcare.add("Shop");
                personalcare.add("Handwash");
                personalcare.add("Facewash");
                personalcare.add("Facecream");
                personalcare.add("Natural oils");
                personalcare.add("Cleaning & Home Sanitory");

                List<String> Agro = new ArrayList<String>();
                Agro.add("Cereals");
                Agro.add("cut flowers");
                Agro.add("Hybrid seeds");
                Agro.add("Organic products");
                Agro.add("Oils");
                Agro.add("Nuts");

                List<String> nutraceuticals = new ArrayList<String>();
                nutraceuticals.add("Food supplement");
                nutraceuticals.add("Energy Drinks");
                nutraceuticals.add("Cold Drinks");
                nutraceuticals.add("Natural & Flavoured Drinks");
                nutraceuticals.add("Natural food & Proteins");
                nutraceuticals.add("Natural & Nutritious Foods");

                List<String> hybridseeds = new ArrayList<String>();
                hybridseeds.add("Rice");
                hybridseeds.add("Maize");
                hybridseeds.add("pulses");
                hybridseeds.add("vegetables");
                hybridseeds.add("Herbs");

                List<String> fertilizers = new ArrayList<String>();
                fertilizers.add("Vermicompost");
                fertilizers.add("Neemvermi");
                fertilizers.add("Biofertilizer");
                fertilizers.add("BioPesticide");

                ListChild.put(ListHeader.get(0), foodprod);
                ListChild.put(ListHeader.get(1),personalcare);
                ListChild.put(ListHeader.get(2),Agro);
                ListChild.put(ListHeader.get(3),nutraceuticals);
                ListChild.put(ListHeader.get(4),hybridseeds);
                ListChild.put(ListHeader.get(5),fertilizers);
                break;

            case "BIOTECH":

                ListHeader.add("Proteins");
                ListHeader.add("Aurvedic Products");
                ListHeader.add("Enzymes");
                ListHeader.add("Molecular kits");
                ListHeader.add("Bioinstruments");
                ListHeader.add("Sergical Devices");
                ListHeader.add("Medical Device");
                ListHeader.add("Medicines");

                List<String> proteins = new ArrayList<String>();
                proteins.add("Rice");
                proteins.add("Tea");
                proteins.add("Pulses");
                proteins.add("Snacks");

                List<String> ayur_prod = new ArrayList<String>();
                ayur_prod.add("Vermicompost");
                ayur_prod.add("Neemvermi");
                ayur_prod.add("Biofertilizer");
                ayur_prod.add("BioPesticide");

                List<String> Enzymes= new ArrayList<String>();
                Enzymes.add("Cereals");
                Enzymes.add("cut flowers");
                Enzymes.add("Hybrid seeds");
                Enzymes.add("Organic products");
                Enzymes.add("Oils");
                Enzymes.add("Nuts");

                List<String> mol_kits = new ArrayList<String>();
                mol_kits.add("Vermicompost");
                mol_kits.add("Biopesticide");
                mol_kits.add("Neemvermi");
                mol_kits.add("Biofertilizer BioPesticide");
                List<String> bioinstruments = new ArrayList<String>();
                bioinstruments.add("Food suplement");
                bioinstruments.add("Energy Drinks");
                bioinstruments.add("Cold Drinks");
                bioinstruments.add("Natural & Flavoured Drinks");
                bioinstruments.add("Natural food & Proteins");
                bioinstruments.add("Natural & Nutritious Foods");
                List<String> surg_devs = new ArrayList<String>();
                surg_devs.add("Rice");
                surg_devs.add("Rice");
                surg_devs.add("Rice");
                surg_devs.add("Rice");
                surg_devs.add("Rice");
                surg_devs.add("Rice");

                ListChild.put(ListHeader.get(0), proteins);
                ListChild.put(ListHeader.get(1), ayur_prod);
                ListChild.put(ListHeader.get(2), Enzymes);
                ListChild.put(ListHeader.get(3), mol_kits);
                ListChild.put(ListHeader.get(4), bioinstruments);
                ListChild.put(ListHeader.get(5), surg_devs);

                break;
            case "SOLAR":
                break;
            case "HANDICRAFTS":
                break;
            case "ELECTRONICS":
                break;
            case "LIFESTYLE" :
                break;
            case "BOOKS":
                break;
            case  "MUSIC":
                break;

        }
    }
}
