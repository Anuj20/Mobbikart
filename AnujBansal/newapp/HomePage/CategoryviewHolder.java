package com.Mobbikart.AnujBansal.newapp.HomePage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Mobbikart.AnujBansal.newapp.Categories.CategoryPage;
import com.Mobbikart.AnujBansal.newapp.R;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

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
        cat_image= (ImageView) layoutview.findViewById(R.id.category_image);
    }

    @Override
    public void onClick(View v) {

        //int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        int position= getAdapterPosition();
        Categories categories= this.productlist.get(position);
        String cat_name= categories.getName().toString();

        Intent Inte= new Intent(context, CategoryPage.class);
        Inte.putExtra("categoryname", cat_name);
        context.startActivity(Inte);

       // Toast.makeText(context, "clicked: "+ position, Toast.LENGTH_SHORT).show();
    }
}
