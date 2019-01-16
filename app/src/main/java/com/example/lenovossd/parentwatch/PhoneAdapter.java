package com.example.lenovossd.parentwatch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PhoneAdapter extends RecyclerView.ViewHolder {
    View mView;
    public PhoneAdapter(@NonNull View itemView) {
        super( itemView );
        mView = itemView;
    }
    public void setDetails(final Context ctx , String name , String number  ){
        TextView Name = mView.findViewById( R.id.Name );
        TextView Number = mView.findViewById( R.id.Number);


        Name.setText( name);
        Number.setText( number );





    }
}
