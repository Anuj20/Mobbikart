package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rohitkapoor on 23/07/16.
 */
public class Prod extends ArrayList<Parcelable> implements Parcelable{

    public String productID;
    public String title;
    public String productImage;
    public String description;
    public double price;
    public boolean selected;

    public Prod()
    {

    }



   /* public Prod(String productID, String title, Drawable productImage, String description,
                   double price) {

        this.productID = productID;
        this.title = title;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
    }*/

    HashMap<String, String> mapRegData = new HashMap<String, String>();
    public HashMap<String, String> getMapRegData() {
        return mapRegData;}

    public String getproductID(String id) {
        return id;
    }
    public void setproductID(String id){
        this.productID = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getimg() {
        return productImage;
    }
    public void setimg(String img){
        this.productImage = img;
    }

    public String getdes(String des) {
        return des;
    }
    public void setdes(String des){
        this.description = des;
    }

    public double getprice(double price) {
        return price;
    }
    public void setprice(double price){
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productID);
        dest.writeString(title);
        dest.writeString(productImage);
        dest.writeString(description);
        dest.writeDouble(price);
        final int N = mapRegData.size();
        // dest.writeInt(N);
        if (N > 0) {
            for (Map.Entry<String, String> entry : mapRegData.entrySet()) {
                dest.writeString(entry.getKey());
                String dat = entry.getValue();
                dest.writeString(dat);
            }
        }

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Prod createFromParcel(Parcel in) {
            return new Prod(in);
        }
        public Prod[] newArray(int size) {
            return new Prod[size];
        }
    };
    private Prod(Parcel in) {
        productID = in.readString();
        title = in.readString();
        productImage = in.readString();
        description = in.readString();
        price = in.readDouble();

        final int N = in.readInt();
        for (int i = 0; i < N; i++) {
            String key = in.readString();
            String value = in.readString();
            mapRegData.put(key, value);
        }
    }
}
