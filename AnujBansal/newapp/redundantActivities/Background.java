package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

import com.Mobbikart.AnujBansal.newapp.Home;
import com.Mobbikart.AnujBansal.newapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by rohitkapoor on 24/06/16.
 */
    public class Background extends AsyncTask<String,Void,String> {
    String log_url =  "http://192.168.1.7/newapp/login.php";
    String reg_url = "http://192.168.1.7/newapp/register.php";
    Context ctx;
    Activity activity;
    android.support.v7.app.AlertDialog.Builder builder;
    ProgressDialog progress;

    public Background(Context ctx) {

        this.ctx = ctx;
        activity = (Activity) ctx;
    }


    protected void onPreExecute() {
        super.onPreExecute();
        builder = new android.support.v7.app.AlertDialog.Builder(activity);
        progress = new ProgressDialog(ctx);
        progress.setTitle("Please wait");
        progress.setMessage("connecting to server");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }


    @Override
    protected String doInBackground(String... params) {

        String method = params[0];
        if (method.equals("register")) {
            try {

                URL url = new URL(reg_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);  //url connection steps
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


                OutputStream outputstream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                String name = params[1];
                String email = params[2];
                String pass = params[3];
                String Data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                bufferedwriter.write(Data);
                bufferedwriter.flush();
                bufferedwriter.close();
                outputstream.close();   //request to server steps
                InputStream inputStream;
                // get stream
                if (httpURLConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

//                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
          else if (method.equals("login"))
        {
            try {
                URL url = new URL(log_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);  //url connection steps
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                OutputStream outputstream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
                String email,pass;
                email=params[1];
                pass=params[2];
                String Data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                bufferedwriter.write(Data);
                bufferedwriter.flush();
                bufferedwriter.close();
                outputstream.close();
                InputStream inputStream ;
//                inputStream = httpURLConnection.getInputStream();
                if (httpURLConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line + "\n");

                }

                httpURLConnection.disconnect();
                Thread.sleep(5000);
                 String s=stringBuilder.toString().trim();
                return s;




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    protected void onPostExecute(String json) {
        try {
            progress.dismiss();


            JSONObject jsonObject =  new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response"); //server name
            JSONObject JO = jsonArray.getJSONObject(0);
            String code = JO.getString("code");
            String message = JO.getString("message"); //message from server

            if (code.equals("reg_true"))
            {

                showdialog("Registration Success", code, message);
            }

            else if (code.equals("reg_false"))
            {

                showdialog("Registration Failed", code, message);
            }


            else if (code.equals("login_true"))
            {
                showdialog("Login Success",code,message);
            }
            else if(code.equals("login_false"))
            {
                showdialog("Login Error..",code,message);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showdialog(String title, String code,String message) {
        builder.setTitle(title);
        if (code.equals("reg_true"))

        {

            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(activity,Home.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });

        }

        else if(code.equals("reg_false"))
        {
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    EditText name,email,pass,conpass,contact;
                    name= (EditText) activity.findViewById(R.id.name);
                    email= (EditText) activity.findViewById(R.id.email);
                    pass= (EditText) activity.findViewById(R.id.pass);
                    conpass= (EditText) activity.findViewById(R.id.conpass);
                    contact= (EditText) activity.findViewById(R.id.contact);
                    name.setText("");
                    email.setText("");
                    pass.setText("");
                    conpass.setText("");
                    contact.setText("");
                    dialog.dismiss();

                }
            });
        }

        else if (code.equals("login_true"))
        {

            Intent intent = new Intent(activity,Home.class);
            activity.startActivity(intent);
        }
        else if (code.equals("login_false"))
        {
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    EditText email,password;
                    email= (EditText) activity.findViewById(R.id.email);
                    //password= (EditText) activity.findViewById(R.id.password);
                    email.setText("");
                   // password.setText("");
                    dialog.dismiss();
                }
            });

        }
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}