package com.example.lenovossd.parentwatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GalleryAdapter extends RecyclerView.ViewHolder
{      View mView;
    public GalleryAdapter(@NonNull View itemView) {
        super( itemView );
        mView = itemView;
    }
    public void setDetails(final Context ctx , String image  ){
       ImageView imageView = mView.findViewById( R.id.imageView );


        Picasso.get().load( image ).into( imageView );





    }
}
