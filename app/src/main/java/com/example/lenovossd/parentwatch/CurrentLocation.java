package com.example.lenovossd.parentwatch;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.lenovossd.parentwatch.Common.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class CurrentLocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference;
    String key;
    Marker mUserMarker;
    Toolbar mActionBarToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
         key = intent.getStringExtra( "key" );
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(mActionBarToolbar);

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
        getSupportActionBar().setTitle("Child Location");




    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        
        // Add a marker in Sydney and move the camera
        mFirebaseDatabase =FirebaseDatabase.getInstance();
        Toasty.success( this,key, Toast.LENGTH_LONG ).show();
        mReference = mFirebaseDatabase.getReference( Common.Child_information_tb1).child( key ).child( "Location" ).child( "l" );
        mReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                Log.e("error1",dataSnapshot.getValue().toString());
                double latitude = (double) dataSnapshot.child( "0" ).getValue();
                double longitude= (double) dataSnapshot.child( "1" ).getValue();
                Log.e("error1", String.valueOf( latitude ) );
                Log.e("error1", String.valueOf( longitude ) );
                LatLng sydney = new LatLng(latitude,longitude);
                mUserMarker =   mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .icon( BitmapDescriptorFactory.fromResource( R.drawable.marker ) )
                        .title("Child Location"));
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target( new LatLng( latitude, longitude ) ).
                        tilt( 60 ).
                        zoom( 15 ).
                        bearing( 90 ).
                        build();
                mMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
                rotateMarker( mUserMarker, 360, mMap );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error( CurrentLocation.this,"Failed !"+databaseError,Toast.LENGTH_LONG ).show();

            }
        } );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent( getApplicationContext(),Home.class );
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i);
        finish();
    }

    private void rotateMarker(final Marker mcurrent, final float i, GoogleMap mMap) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float startRotation = mcurrent.getRotation();
        final long duration = 1500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post( new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation( (float) elapsed / duration );
                float rot = t * i + (1 - t) * startRotation;
                mcurrent.setRotation( -rot > 180 ? rot / 2 : rot );

                if (t < 1.0) {
                    handler.postDelayed( this, 16 );
                }
            }
        } );
    }
}
