package com.example.lenovossd.parentwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.lenovossd.parentwatch.Common.Common;
import com.example.lenovossd.parentwatch.Model.GalleryModel;
import com.example.lenovossd.parentwatch.Model.PhoneModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class GalleryImages extends AppCompatActivity {
    String key;
    Toolbar mActionBarToolbar;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gallery_images );
        Toast.makeText( this,"this is Galley images ", Toast.LENGTH_LONG ).show();
        Intent intent = getIntent();
        key = intent.getStringExtra( "key" );
        Toasty.success( this,key,Toast.LENGTH_LONG ).show();
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mRecyclerView = (RecyclerView)findViewById( R.id.recyclerView );

        layoutManager = new GridLayoutManager( this,2 );
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( layoutManager );
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Child Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(),Home.class );
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(i);
                finish();
            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference( Common.Child_information_tb1).child(key).child("Gallery" );

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<GalleryModel,GalleryAdapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter <GalleryModel, GalleryAdapter>(
                        GalleryModel.class,
                        R.layout.gallery_list,
                        GalleryAdapter.class,
                        mReference
                ) {


                    @Override
                    protected void populateViewHolder(GalleryAdapter viewHolder, GalleryModel model, int position) {
                        viewHolder.setDetails( getApplicationContext(),model.getImage() );



                    }
                };
        mRecyclerView.setAdapter( firebaseRecyclerAdapter );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent( getApplicationContext(),Home.class );
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i);
        finish();
    }
}
