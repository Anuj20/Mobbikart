package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rohitkapoor on 31/07/16.
 */
public class Download extends AsyncTask<Void,Void,String> {

    Activity activity;
    Context c;
    ListView listViewCatalog;
    String urlAdd;
    //final static String urlAddress="http://192.168.0.102/newapp/json_get_data.php";


    public Download(Context c, String urlAdd, ListView listViewCatalog) {
        this.c = c;
        activity=(Activity)c;
        this.urlAdd = urlAdd;
        this.listViewCatalog = listViewCatalog;
    }


    @Override
    protected String doInBackground(Void... params) {

        StringBuilder jsonData = new StringBuilder();

        try {
            URL url = new URL(urlAdd);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //CON PROP
//            con.setRequestMethod("GET");
//            con.setConnectTimeout(20000);
//            con.setReadTimeout(20000);
//            con.setDoInput(true);
//            con.setDoOutput(true);




            String line="";


            /*String Data = URLDecoder.decode("productID", "UTF-8") + "=" + URLDecoder.decode(Prod.productID, "UTF-8") + "&" +
                    URLDecoder.decode("title", "UTF-8") + "=" + URLDecoder.decode(Prod.title, "UTF-8") + "&" +
                    URLDecoder.decode("img", "UTF-8") + "=" + URLDecoder.decode(Prod.productImage, "UTF-8")+"&"+
                    URLDecoder.decode("description", "UTF-8") + "=" + URLDecoder.decode(Prod.description, "UTF-8")+"&"+
                    URLDecoder.decode("price", "UTF-8") + "=" + URLDecoder.decode(String.valueOf(Prod.price), "UTF-8");
*/


            while ((line = br.readLine()) != null) {
                jsonData.append(line + "\n");
            }

            br.close();
            is.close();
            con.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonData.toString().trim();
    }

    @Override
    protected void onPostExecute(String jsonData)
    {
//        super.onPostExecute(jsonData);

        if (jsonData == null) {
            Toast.makeText(c, "Unsuccessful,No data Retrieved", Toast.LENGTH_SHORT).show();
        } else {
            //PARSE
           // Parser parser = new Parser(c, jsonData, listViewCatalog);
           // parser.execute();
        }
    }
}
