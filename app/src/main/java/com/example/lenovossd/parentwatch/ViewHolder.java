package com.example.lenovossd.parentwatch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovossd.parentwatch.Common.Common;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ViewHolder(@NonNull View itemView) {
        super( itemView );
        mView = itemView;
    }                                                             // no of parametr to show result
    public void setDetails(final Context ctx , String name , String email, String Avatarurl, String phone , String password , final String key ){
        TextView Name = mView.findViewById( R.id.Name );
        TextView Phone = mView.findViewById( R.id.Phone);
        CircleImageView imageView = mView.findViewById( R.id.imageView );
        TextView Password = mView.findViewById( R.id.password );
        TextView Email = mView.findViewById( R.id.email );
        final String Key = key;
        Name.setText( name);
        Phone.setText( phone );
        Password.setText( password );
        Email.setText( email );

        Picasso.get().load( Avatarurl ).into( imageView );
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Test","Name clicked : "+getAdapterPosition());
                Log.e("Test","Name clicked : "+Key.toString());

                Intent i = new Intent( ctx.getApplicationContext(),DataShownActivty.class );
                i.setFlags( FLAG_ACTIVITY_NEW_TASK );
                i.putExtra( "key",Key );
                ctx.startActivity(i);

            }
        });


    }
}
