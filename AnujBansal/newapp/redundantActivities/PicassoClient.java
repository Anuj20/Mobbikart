package com.Mobbikart.AnujBansal.newapp.redundantActivities;

import android.content.Context;
import android.widget.ImageView;

import com.Mobbikart.AnujBansal.newapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Oclemy on 6/5/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class PicassoClient {

    public static void downloadImage(Context c,String imageUrl,ImageView img)
    {
        if(imageUrl.length()>0 && imageUrl!=null)
        {
            Picasso.with(c).load(imageUrl).placeholder(R.drawable.placeholder).into(img);
        }else {
            Picasso.with(c).load(R.drawable.placeholder).into(img);
        }
    }

}
