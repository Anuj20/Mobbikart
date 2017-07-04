package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.R;

import java.util.ArrayList;

public class ProductDetails extends Activity  {

//    Prod passed_var = null;
    ArrayList<Prod> catalog;
    Context c;
    String productID;
    Prod selectedProduct;
    Prod sp;
    int pos;
    public ProductDetails(){}

    public ProductDetails( String productID, ArrayList<Prod> listViewCatalog, int position) {
        this.catalog = listViewCatalog;
        this.productID = productID;
        this.pos=position;
     //  ProductDetails.this.selectedProduct = this.catalog.get(position);

        //sp=catalog.get(position);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        selectedProduct = getIntent().getParcelableExtra("ID_Extra");


//        List<Prod> catalog = ShoppingCartHelper.getCartList();
//        List<Prod> catalog = ShoppingCartHelper.getCatalog(getResources());
//        Prod tempProduct = new Prod();
//        Prod productObj = null;
//        if (catalog.)
//        for (productObj:catalog)
//        {
//            //if(productObj.productID.equals(getIntent().getExtras().get(ShoppingCartHelper.PRODUCT_INDEX)))
//            if(productObj.productID.equals(getIntent().getExtras().get(String.valueOf(Parser.class))))
//            {
//                tempProduct = productObj;
//                break;
//            }
//        }
//
//        int productIndex = getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);
//
//        final Prod selectedProduct = tempProduct;//oduct = catalog.get(productIndex);

        // Set the proper image and text
        ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);
//        productImageView.setImageDrawable(Drawable.createFromPath(this.selectedProduct.getimg()));

        TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
        productTitleTextView.setText(selectedProduct.title);

        TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
        productDetailsTextView.setText(selectedProduct.description);

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
        productPriceTextView.setText("Price:$" + selectedProduct.price);

        // Update the current quantity in the cart
        TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);
        textViewCurrentQuantity.setText("Currently in Cart: " + ShoppingCartHelper.getProductQuantity(selectedProduct));

        // Save a reference to the quantity edit text
        final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Check to see that a valid quantity was entered
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(editTextQuantity.getText()
                            .toString());

                    if (quantity < 0) {
                        Toast.makeText(getBaseContext(),
                                "Please enter a quantity of 0 or higher",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(),
                            "Please enter a numeric quantity",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // If we make it here, a valid quantity was entered
                ShoppingCartHelper.setQuantity(selectedProduct, quantity);

                // Close the activity
                finish();
            }
        });
        // Disable the add to cart button if the item is already in the cart
        /*if(cart.contains(selectedProduct)) {
            addToCartButton.setEnabled(false);
            addToCartButton.setText("Item in Cart");
        }*/

    }
}