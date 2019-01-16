package com.example.lenovossd.parentwatch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lenovossd.parentwatch.Common.Common;
import com.example.lenovossd.parentwatch.Model.PhoneModel;
import com.example.lenovossd.parentwatch.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Conatcts extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference;
    String key;
    Toolbar mActionBarToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_conatcts );
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mRecyclerView = (RecyclerView)findViewById( R.id.recyclerView );
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        Intent intent = getIntent();
        key = intent.getStringExtra( "key" );
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Child PhoneBook");
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
        mReference = mFirebaseDatabase.getReference( Common.Child_information_tb1).child(key).child("PhoneBook" );
        mReference.addValueEventListener( new ValueEventListener() {
            public ArrayList<String> Userlist;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Userlist = new ArrayList<String>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                   // Userlist.add( String.valueOf( dsp.getValue() )); //add result into array list
                }
                Log.e("error",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<PhoneModel,PhoneAdapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter <PhoneModel, PhoneAdapter>(
                        PhoneModel.class,
                        R.layout.phone_layout,
                        PhoneAdapter.class,
                        mReference
                ) {
                    @Override
                    protected void populateViewHolder(PhoneAdapter viewHolder, PhoneModel model, int position) {
                        viewHolder.setDetails( getApplicationContext(),model.getName(),model.getNumber() );
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



