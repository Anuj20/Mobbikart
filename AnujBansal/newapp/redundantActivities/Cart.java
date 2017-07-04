package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.Mobbikart.AnujBansal.newapp.R;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private ArrayList<Prod> mCartList;
    private ProductAdapter mProductAdapter;
    ArrayList<Prod> cartEntryProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        mCartList = ShoppingCartHelper.getCartList();

        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }


        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true,true);
        listViewCatalog.setAdapter(mProductAdapter);
        listViewCatalog.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewCatalog.setMultiChoiceModeListener(new  AbsListView.MultiChoiceModeListener() {


                                                       @Override
                                                       public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                                                           // TODO  Auto-generated method stub
                                                           return false;
                                                       }



                                                       @Override
                                                       public void onDestroyActionMode(ActionMode mode) {
                                                           // TODO  Auto-generated method stub

                                                       }

                                                       @Override
                                                       public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                                                           // TODO  Auto-generated method stub
                                                           mode.getMenuInflater().inflate(R.menu.multiple_delete, menu);
                                                           return true;

                                                       }
            @Override
            public boolean  onActionItemClicked(final ActionMode mode,
                                                MenuItem item) {
                // TODO  Auto-generated method stub
                switch  (item.getItemId()) {
                    case R.id.selectAll:
                        //
                        final int checkedCount  = mCartList.size();
                        // If item  is already selected or checked then remove or
                        // unchecked  and again select all
                        mProductAdapter.removeSelection();
                        for (int i = 0; i <  checkedCount; i++) {
                            listViewCatalog.setItemChecked(i,   true);
                            //  listviewadapter.toggleSelection(i);
                        }
                        // Set the  CAB title according to total checked items

                        // Calls  toggleSelection method from ListViewAdapter Class

                        // Count no.  of selected item and print it
                        mode.setTitle(checkedCount  + "  Selected");
                        return true;
                    case R.id.delete:
                        // Add  dialog for confirmation to delete selected item
                        // record.
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                Cart.this);
                        builder.setMessage("Do you  want to delete selected record(s)?");

                        builder.setNegativeButton("No", new  DialogInterface.OnClickListener() {

                            @Override
                            public void  onClick(DialogInterface dialog, int which) {
                                // TODO  Auto-generated method stub

                            }
                        });
                        builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {

                            @Override
                            public void  onClick(DialogInterface dialog, int which) {
                                // TODO  Auto-generated method stub
                                SparseBooleanArray  selected = mProductAdapter
                                        .getSelectedIds();


                                   for (int i =  (selected.size() - 1); i >= 0; i--) {
                                    if  (selected.valueAt(i)) {
                                        Object selecteditem =  mProductAdapter
                                                .getItem(selected.keyAt(i));
                                        // remove  selected items following the ids
                                        ShoppingCartHelper.removeProduct((Prod) selecteditem);
                                        mProductAdapter.remove(selecteditem);
                                        mProductAdapter.notifyDataSetChanged();

                                        listViewCatalog.setAdapter(mProductAdapter);
                                        double subTotal = 0;
                                        for(Prod cartEntryProduct : mCartList) {
                                            int quantity = ShoppingCartHelper.getProductQuantity(cartEntryProduct);
                                            subTotal += cartEntryProduct.price * quantity;
                                        }

                                        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
                                        productPriceTextView.setText("Subtotal: $" + subTotal);

                                    }
                                }

                                // Close CAB
                                mode.finish();
                                selected.clear();

                            }
                        });
                        AlertDialog alert =  builder.create();
                        alert.setTitle("Confirmation"); // dialog  Title
                        alert.show();
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void  onItemCheckedStateChanged(ActionMode mode,
                                                   int position, long id, boolean checked) {
                // TODO  Auto-generated method stub
                final int checkedCount  = listViewCatalog.getCheckedItemCount();
                // Set the  CAB title according to total checked items
                mode.setTitle(checkedCount  + "  Selected");
                // Calls  toggleSelection method from ListViewAdapter Class
                mProductAdapter.toggleSelection(position);
            }
        });

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {



                    Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetails.class);
//                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX,mCartList.get(position).productID);

                productDetailsIntent.putExtra("ID_Extra", (Parcelable) mCartList.get(position));

                startActivity(productDetailsIntent);
            }
        });




 /*                   Button b = (Button) findViewById(R.id.Button01);

            b.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         for (int i =mCartList.size()-1;i>=0;i--) {
                                             if(mCartList.get(i).selected = true){
                                           Prod t=  mCartList.get(i) ;
                                             ShoppingCartHelper.removeProduct(t);
                                                 mProductAdapter.remove(t);
                                                 }
                                             mProductAdapter.notifyDataSetChanged();
                                             listViewCatalog.setAdapter(mProductAdapter);
                                             double subTotal = 0;
                                             for(Prod cartEntryProduct : mCartList) {
                                                 int quantity = ShoppingCartHelper.getProductQuantity(cartEntryProduct);
                                                 subTotal += cartEntryProduct.price * quantity;
                                             }

                                             TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
                                             productPriceTextView.setText("Subtotal: $" + subTotal);
                                         }



                                     }


            });
*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if(mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }

        double subTotal = 0;
        for(Prod cartEntryProduct : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(cartEntryProduct);
            subTotal += cartEntryProduct.price * quantity;
        }

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: $" + subTotal);
    }

}
