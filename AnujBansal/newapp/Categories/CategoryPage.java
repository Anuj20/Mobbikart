package com.Mobbikart.AnujBansal.newapp.Categories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Mobbikart.AnujBansal.newapp.HomePage.HomeScreen;
import com.Mobbikart.AnujBansal.newapp.R;

public class CategoryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(this, HomeScreen.class);
        startActivity(i);
    }
}
