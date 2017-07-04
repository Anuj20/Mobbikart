package com.Mobbikart.AnujBansal.newapp.redundantActivities;

/**
 * Created by rohitkapoor on 27/07/16.
 */
public class ShoppingCartEntry {
    private Prod mProduct;
    private int mQuantity;

    public ShoppingCartEntry(Prod product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }

    public Prod getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}


