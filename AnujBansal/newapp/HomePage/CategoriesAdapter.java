package com.Mobbikart.AnujBansal.newapp.HomePage;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Mobbikart.AnujBansal.newapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gamer on 6/26/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryviewHolder> {

    private List<Categories> categories;
    private Context context;

    //ArrayList<Categories > categories= new ArrayList<>();


    public CategoriesAdapter (Context context, List<Categories> categories){

        this.categories = categories;
        this.context= context;

    }
   // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public CategoryviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutview = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
        //layoutview.setElevation(200);

        CategoryviewHolder pvh =new CategoryviewHolder(layoutview, context, (ArrayList<Categories>) categories);
        return pvh;
    }


    @Override
    public void onBindViewHolder(CategoryviewHolder holder, int position) {

        //holder.productdescription.setText(list.get(position).getDescription());
        // holder.Name.setText(list.get(position).getName());
        // holder.phone.setText(list.get(position).getPhone());
        holder.categoryName.setText(categories.get(position).getName());
        holder.cat_image.setImageResource(categories.get(position).getCategory_image());
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

}
