package com.example.lenovossd.parentwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Message extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_message );
        Toast.makeText( this,"this is Message ", Toast.LENGTH_LONG ).show();
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        Intent intent = getIntent();
        key = intent.getStringExtra( "key" );
        Toasty.success( this,key,Toast.LENGTH_LONG ).show();
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Child Messages");
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
