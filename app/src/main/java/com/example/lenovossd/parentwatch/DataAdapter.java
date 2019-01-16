package com.example.lenovossd.parentwatch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovossd.parentwatch.Model.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context mCtx;

    //we are storing all the products in a list
    private List<DataModel> DataList;
    String key;

    public DataAdapter(Context mCtx, List <DataModel> dataList,String Key) {
        this.mCtx = mCtx;
        DataList = dataList;
        key = Key;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.data_list_layout, null);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataModel Data = DataList.get(position);
        Log.e("Test","Name clicked : "+position);
        //binding the data with the viewholder views
    holder.textViewTitle.setText(Data.getTitle());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(Data.getImage()));

    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView textViewTitle;
        CircleImageView imageView;
        Context ctx;


        public DataViewHolder(@NonNull View itemView) {
            super( itemView );
            mView = itemView;
            textViewTitle = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            mView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() ==0){
                        Intent i = new Intent( mCtx.getApplicationContext(), Conatcts.class );
                        i.setFlags( FLAG_ACTIVITY_NEW_TASK );
                        i.putExtra( "key", key );
                        mCtx.startActivity( i );

                    }
                     if (getAdapterPosition() ==1){
                        Intent i = new Intent( mCtx.getApplicationContext(), GalleryImages.class );
                        i.setFlags( FLAG_ACTIVITY_NEW_TASK );
                         i.putExtra( "key", key );
                        mCtx.startActivity( i );

                    }
                    if (getAdapterPosition() ==2) {
                        Intent i = new Intent( mCtx.getApplicationContext(), CurrentLocation.class );
                        i.setFlags( FLAG_ACTIVITY_NEW_TASK );
                        i.putExtra( "key", key );
                        mCtx.startActivity( i );
                    }
                     if (getAdapterPosition() ==3){
                        Intent i = new Intent( mCtx.getApplicationContext(), Message.class );
                        i.setFlags( FLAG_ACTIVITY_NEW_TASK );
                         i.putExtra( "key", key );
                        mCtx.startActivity( i );
                    }
                }
            } );

        }

    }
}
