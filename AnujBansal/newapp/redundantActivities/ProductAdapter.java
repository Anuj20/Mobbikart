package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.Mobbikart.AnujBansal.newapp.R;

import java.util.ArrayList;

/**
 * Created by rohitkapoor on 23/07/16.
 */
public class ProductAdapter extends BaseAdapter {

    private ArrayList<Prod> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowQuantity;
    SparseBooleanArray mSelectedItemsIds = new SparseBooleanArray();
    boolean mcheckbox;
    Context c;


    public ProductAdapter(ArrayList<Prod> list, LayoutInflater inflater, boolean showQuantity, boolean checkbox) {
        mProductList = list;
        mInflater = inflater;
        mShowQuantity = showQuantity;
        mcheckbox=checkbox;
    }

    public ProductAdapter(Activity activity, ArrayList<Prod> mCartList, boolean showQuantity, boolean checkbox) {
        this.c = activity;
        this.mProductList = mCartList;
        mShowQuantity = showQuantity;
        mcheckbox=checkbox;

        mInflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        if(mProductList == null)
            return 0;

        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.items, parent,
                    false);//no parent false=null
            item = new ViewItem();
            TextView title = (TextView) convertView.findViewById(R.id.TextViewItem);
            ImageView img = (ImageView) convertView.findViewById(R.id.ImageViewItem);

            /*item.productImageView = (ImageView) convertView
                    .findViewById(R.id.ImageViewItem);

            item.productTitle = (TextView) convertView.findViewById(R.id.TextViewItem);

            item.productQuantity = (TextView) convertView
                    .findViewById(R.id.textViewQuantity);
            item.productcheckbox = (CheckBox) convertView.findViewById(R.id.checkBox);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }*/
            Prod spacecraft = mProductList.get(position);

            title.setText(spacecraft.getTitle());
            //  img.setImageDrawable(Drawable.createFromPath(spacecraft.getimg()));
            PicassoClient.downloadImage(c, spacecraft.getimg(), img);

        /*Prod curProduct = mProductList.get(position);

        item.productImageView.setImageDrawable(curProduct.productImage);
        item.productTitle.setText(curProduct.title);
/*


//       if (mShowQuantity) {
//            item.productQuantity.setText("Quantity: "
//                    + ShoppingCartHelper.getProductQuantity(spacecraft));
//        } else {
//            // Hid the view
//            //item.productQuantity.setVisibility(View.GONE);
//        }
//        if(!mcheckbox) {
//            item.productcheckbox.setVisibility(View.GONE);
//        } else {
//            if(spacecraft.selected == true)
//                item.productcheckbox.setChecked(true);
//            else
//                item.productcheckbox.setChecked(false);
//        }

*/


            //return convertView;
        }
    return convertView;
    }




    public void remove(Object object) {
        mProductList.remove(object);
        notifyDataSetChanged();
    }
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    // remove selection after unchecked
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    // Item checked on selection
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    // Get number of selected item
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
    class ViewItem {
        ImageView productImageView;
        TextView productTitle;
        TextView productQuantity;
        CheckBox productcheckbox;
    }



