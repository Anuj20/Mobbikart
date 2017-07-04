package com.Mobbikart.AnujBansal.newapp.HomePage;

/**
 * Created by gamer on 6/26/2017.
 */

public class Categories {

    private String Name;
            int Category_image;


    public Categories(String Name, int category_image){
        this.Name=Name;
        this.Category_image= category_image;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public int getCategory_image() {
        return Category_image;
    }

    public void setCategory_image(int category_image) {
        Category_image = category_image;
    }
}
