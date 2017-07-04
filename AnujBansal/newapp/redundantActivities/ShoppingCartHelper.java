package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rohitkapoor on 23/07/16.
 */
public class ShoppingCartHelper {
    static Context c;

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static ArrayList<Prod> catalog;
    private static Map<String, ShoppingCartEntry> cartMap = new HashMap<String, ShoppingCartEntry>();


    /*public static List<Prod> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<Prod>();
            catalog.add(new Prod("1", "Dead or Alive", res
                    .getDrawable(R.drawable.deadoralive),
                    "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
            catalog.add(new Prod("2", "Switch", res
                    .getDrawable(R.drawable.switchbook),
                    "Switch by Chip Heath and Dan Heath", 24.99));
            catalog.add(new Prod("3", "Watchmen", res
                    .getDrawable(R.drawable.watchmen),
                    "Watchmen by Alan Moore and Dave Gibbons", 14.99));
        }

        return catalog;
    }*/

    public static void setQuantity(Prod product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product.productID);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
//                removeProduct(product);

                Toast.makeText(c,"Enter Valid Quantity",Toast.LENGTH_SHORT).show();
                return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product.productID, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Prod product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product.productID);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(Prod product) {

        cartMap.remove(product.productID);
    }

    public static ArrayList<Prod> getCartList() {
        //List<Prod> cartList = new Vector<Prod>(cartMap.values().size());
        ArrayList<Prod> cartList = new ArrayList<>(cartMap.values().size());

        for(ShoppingCartEntry shoppingCartEntryObj : cartMap.values()) {
            cartList.add(shoppingCartEntryObj.getProduct());
        }

        return cartList;
    }









    /*public static List<Prod> getCart() {
        if(cart == null) {
            cart = new Vector<Prod>();
        }

        return cart;
    }*/

}

