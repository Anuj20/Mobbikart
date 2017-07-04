package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.Mobbikart.AnujBansal.newapp.Login.LoginHome;
import com.Mobbikart.AnujBansal.newapp.R;

public class StartActivity extends AppCompatActivity {
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        imageButton= (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext() , LoginHome.class);
                startActivity(intent);
            }
        });
    }

   /* @Override
    private void onBackpressed(){
        Toast.makeText(getApplicationContext(Context),"Press back again", Toast.LENGTH_SHORT);
        Toast.show();
         super.onBackPressed();*/


    }



