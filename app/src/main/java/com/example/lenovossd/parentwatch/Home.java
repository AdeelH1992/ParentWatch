package com.example.lenovossd.parentwatch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovossd.parentwatch.Common.Common;
import com.example.lenovossd.parentwatch.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemSelectedListener{

        RecyclerView mRecyclerView;
        FirebaseDatabase mFirebaseDatabase;
        DatabaseReference mReference;
    AlertDialog waitingDialog;
    StorageReference storageReference;
    FirebaseStorage storage;
    TextView txtRiderName,txtStars;
    CircleImageView imageAvatar;
    RadioGroup radioGroup;
    RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        waitingDialog = new SpotsDialog( Home.this );
        mRecyclerView = (RecyclerView)findViewById( R.id.recyclerView );
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );




        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        mFirebaseDatabase =FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference( Common.Child_information_tb1).child( Common.Child_user_tb1);
        waitingDialog.show();
        mReference.addValueEventListener( new ValueEventListener() {
            public ArrayList<String> Userlist;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                Userlist = new ArrayList<String>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add( String.valueOf( dsp.getValue() )); //add result into array list
                }
                    Log.e("error",Userlist.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        View navigationHeaderView = navigationView.getHeaderView( 0 );

        txtRiderName = navigationHeaderView.findViewById( R.id.txtRiderName );

        txtRiderName.setText( String.format( "%s",Common.currentUser.getName() ) );
        txtStars = navigationHeaderView.findViewById( R.id.txtPhone );
        txtStars.setText( String.format( "%s",Common.currentUser.getPhone() ) );

        imageAvatar = navigationHeaderView.findViewById( R.id.imageAvatar );
        if (Common.currentUser.getAvatarUrl() != null && !TextUtils.isEmpty( Common.currentUser.getAvatarUrl() ))
        {            Picasso.get()
                   .load( Common.currentUser.getAvatarUrl() )
                    .into( imageAvatar );
        }



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User,ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter <User, ViewHolder>(
                        User.class,
                        R.layout.list_layout,
                        ViewHolder.class,
                        mReference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, User model, int position) {
                    viewHolder.setDetails( getApplicationContext(),model.getName(),model.getEmail(),model.getAvatarUrl(),model.getPhone(),model.getPassword(),model.getKey() );
                        waitingDialog.dismiss();
                    }
                };
        mRecyclerView.setAdapter( firebaseRecyclerAdapter );

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.home, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_signOut)
        {
            SignOut();
        }
        if (id == R.id.nav_add_childern)
        {
           addChildern();
        }
        else if (id == R.id.nav_UpdateInformation)
        {
            showUpdateInforamtionDialog();
        }
        return true;

    }

    private void addChildern() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder( Home.this );
        dialog.setTitle( "Add Childern" );
        dialog.setMessage( "Please fill all the Inforamtion" );
        LayoutInflater inflater = LayoutInflater.from( this );

        final View add_childern = inflater.inflate( R.layout.add_child,null );

        final MaterialEditText FName = add_childern.findViewById( R.id.first_name );
        final MaterialEditText LName = add_childern.findViewById( R.id.last_Name );
        final MaterialEditText Age = add_childern.findViewById( R.id.age );
        Spinner spinner = add_childern.findViewById( R.id.spinnerbloodgroup );
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( Home.this,R.array.bloodgroup,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter( adapter );
        spinner.setOnItemSelectedListener( this );

        radioGroup = add_childern.findViewById( R.id.radiosexgroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                int radioid = radioGroup.getCheckedRadioButtonId();
                radioButton = add_childern.findViewById( radioid );


            }
        });
        dialog.setView( add_childern );

        dialog.setPositiveButton( "Add childern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                String name = FName.getText().toString();
                String phone = LName.getText().toString();
                String age = Age.getText().toString();
                int radioid = radioGroup.getCheckedRadioButtonId();
                radioButton = add_childern.findViewById( radioid );
                Toast.makeText( Home.this,"selected radio Button : "+radioButton.getText(),Toast.LENGTH_LONG).show();



            }
        } );
        dialog.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface  dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        } );
        dialog.show();








    }
    public void checkButton(View v ){
        int radioid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById( radioid );
       // Toast.makeText( this,"selected radio Button : "+ radioButton.getText(),Toast.LENGTH_LONG).show();

    }



    private void showUpdateInforamtionDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this );
        dialog.setTitle( "Update Inforamtion" );
        dialog.setMessage("Please fill all the Inforamtion");

        LayoutInflater inflater= LayoutInflater.from(this);

        View update_info_layout=inflater.inflate(R.layout.layout_update_information,null);



        final MaterialEditText edtName = update_info_layout.findViewById( R.id.edt_Name );
        final MaterialEditText edtPhone = update_info_layout.findViewById( R.id.edt_Phone );
        final ImageView imgAvatar = update_info_layout.findViewById( R.id.imageAvatar );

        dialog.setView( update_info_layout );

        imgAvatar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImageandUpload();
            }
        } );

        dialog.setView( update_info_layout );
        dialog.setPositiveButton( "UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                final AlertDialog waitingDialog = new SpotsDialog( Home.this );
                waitingDialog.show();
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();

                Map<String,Object> update = new HashMap <>(  );
                if (!TextUtils.isEmpty(name))
                    update.put( "name",name );
                if (!TextUtils.isEmpty(phone))
                    update.put( "phone",phone );

                //update

                DatabaseReference riderInformation = FirebaseDatabase.getInstance().getReference(Common.Parent_information_tb1);
                riderInformation.child( FirebaseAuth.getInstance().getCurrentUser().getUid() )
                        .updateChildren( update ).addOnCompleteListener( new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        waitingDialog.dismiss();
                        if (task.isSuccessful())
                            Toasty.success( Home.this ,"Inforamtion Updated !",Toast.LENGTH_LONG,true).show();
                        else
                            Toasty.error( Home.this ,"Inforamtion wasn't Updated !",Toast.LENGTH_LONG,true).show();
                    }
                } );





            }
        } );

        dialog.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        } );

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)

        {
            final Uri saveUri = data.getData();
            Toast.makeText( this,saveUri.toString(),Toast.LENGTH_LONG).show();
            if (saveUri != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog( this );

                progressDialog.setMessage( "Uploading..." );
                progressDialog.show();

                String imageName = UUID.randomUUID().toString();

                final StorageReference imageFolder = storageReference.child( "images/"+imageName );

                imageFolder.putFile( saveUri )
                        .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();

                                imageFolder.getDownloadUrl().addOnSuccessListener( new OnSuccessListener <Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Map<String,Object> update = new HashMap <>(  );

                                        update.put( "avatarUrl",uri.toString() );

                                        DatabaseReference riderInformation = FirebaseDatabase.getInstance().getReference(Common.Parent_information_tb1);
                                        riderInformation.child( FirebaseAuth.getInstance().getCurrentUser().getUid() )
                                                .updateChildren( update ).addOnCompleteListener( new OnCompleteListener <Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    imageAvatar.setImageURI( saveUri );
                                                    Toasty.success( Home.this, "Image Was Uploaded", Toast.LENGTH_LONG, true ).show();
                                                }
                                                else
                                                    Toasty.error( Home.this ,"Image wasn't Updated !",Toast.LENGTH_LONG,true).show();
                                            }
                                        } ).addOnFailureListener( new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toasty.error( Home.this ,e.getMessage(),Toast.LENGTH_LONG,true).show();

                                            }
                                        } );
                                        ;                               }
                                } );

                            }
                        } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());

                        progressDialog.setMessage( "Uploaded " +progress +" %" );
                    }
                } );
            }
        }

    }

    private void ChooseImageandUpload() {
        Intent intent = new Intent(  );
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( Intent.createChooser(  intent,"Select Picture"),Common.PICK_IMAGE_REQUEST );
    }

    private void SignOut() {
        // Reset Remeber VALue
        Paper.init( this );
        Paper.book().destroy();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( Home.this,MainActivity.class );
        startActivity( intent );
        finish();
    }

    @Override
    public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition( position ).toString();
        Toast.makeText( parent.getContext(),text,Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onNothingSelected(AdapterView <?> parent) {

    }
}
