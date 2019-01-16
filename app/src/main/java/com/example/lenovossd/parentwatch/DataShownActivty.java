package com.example.lenovossd.parentwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.lenovossd.parentwatch.Model.DataModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DataShownActivty extends AppCompatActivity {
    List<DataModel> productList;

    RecyclerView recyclerView;
    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_data_shown_activty );
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        getSupportActionBar().setTitle("MOBILE DATA");
        Intent intent = getIntent();
        String key = intent.getStringExtra( "key" );
        Toasty.success( this,key, Toast.LENGTH_LONG ).show();
        productList = new ArrayList<>();

        productList.add(
                new DataModel(
                        1,
                        "PhoneBook",
                        R.drawable.ic_perm_contact_calendar_black_24dp

                       ));

        productList.add(
                new DataModel(
                        1,
                        "Gallery",
                        R.drawable.ic_image_black_24dp
                        ));

        productList.add(
                new DataModel(
                        1,
                        "Child Current Location",
                        R.drawable.ic_pin_drop_black_24dp
                        ));
        productList.add(
                new DataModel(
                        1,
                        "Message",
                        R.drawable.ic_sms_black_24dp

                ));

        DataAdapter adapter = new DataAdapter(this, productList,key);


        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent( DataShownActivty.this,Home.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
        finish();
    }
}
