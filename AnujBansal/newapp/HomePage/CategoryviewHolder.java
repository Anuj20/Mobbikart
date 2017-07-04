package com.Mobbikart.AnujBansal.newapp.HomePage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.Categories.CategoryPage;
import com.Mobbikart.AnujBansal.newapp.R;
import com.Mobbikart.AnujBansal.newapp.redundantActivities.StartActivity;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by gamer on 6/29/2017.
 */

public class CategoryviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView categoryName, categorySubtext;
    ImageView cat_image;
    private RecyclerView mRecyclerView;
    private static View.OnClickListener clickListener;
    private Context context;

    private ArrayList<Categories> productlist = new ArrayList<>();

    public CategoryviewHolder(View layoutview, Context context, ArrayList<Categories> productlist) {
        super(layoutview);

        this.context= context;
        this.productlist= productlist;
        layoutview.setOnClickListener(this);

        categoryName= (TextView) layoutview.findViewById(R.id.categoryname);
       // categorySubtext= (TextView) layoutview.findViewById(R.id.Subtext);

        cat_image= (ImageView) layoutview.findViewById(R.id.category_image);
    }

    @Override
    public void onClick(View v) {

        //int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        int position= getAdapterPosition();
        Categories categories= this.productlist.get(position);

        switch (position){
            case 0:
                context.startActivity(new Intent(context, CategoryPage.class));
                break;

            case 1:




        }

        Toast.makeText(context, "clicked: "+ position, Toast.LENGTH_SHORT).show();
    }
}
