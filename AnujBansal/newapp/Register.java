package com.Mobbikart.AnujBansal.newapp;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Mobbikart.AnujBansal.newapp.redundantActivities.Background;


public class Register extends AppCompatActivity {
    EditText name,email,pass,conpass,contact;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        pass= (EditText) findViewById(R.id.pass);
        conpass= (EditText) findViewById(R.id.conpass);
        contact= (EditText) findViewById(R.id.contact);
        register= (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("") || conpass.getText().toString().equals("") || contact.getText().toString().equals("")) {
                    AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(Register.this).setTitle("Missing Field").
                            setMessage("Field Cannot be Empty").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertdialogbuilder.show();

                } else if (!pass.getText().toString().equals(conpass.getText().toString()))

                {
                    AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(Register.this).setTitle("password mismatch").
                            setMessage("passwords are not same").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pass.setText("");
                            conpass.setText("");
                        }
                    });
                    alertdialogbuilder.show();
                } else {
                    Background background = new Background(Register.this);
                    background.execute("register", name.getText().toString(), email.getText().toString(), pass.getText().toString());
                }
            }
        });
    }

}
