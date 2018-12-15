package com.example.leonid.jetpack;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonid.jetpack.adapters.recycleAdapterDeliveryGuys;
import com.example.leonid.jetpack.adapters.recycleAdapterPackages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Delivery;
import Objects.DeliveryGuys;
import Objects.Packages;

public class PackagesActivity extends AppCompatActivity implements recycleAdapterPackages.ItemClickListener{

    public static final String TAG = "ActiveDeliveryGuysAct";
    private Toolbar toolbar;
    public Delivery clicked_delivery = null;
    private ArrayList<Packages> array = new ArrayList<>();
    private RecyclerView recyclerView;
    PackagesActivity this_context = this;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packages_activity);
        Log.d(TAG, "on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        ImageView overlay = (ImageView) findViewById(R.id.overlay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);

        //query for active deliveries
        Query q =  FirebaseDatabase.getInstance().getReference("Packages");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Packages temp = new Packages(ds.getValue(Packages.class));
                    array.add(temp);
                }
                Log.d(TAG,"Done retrieving Packages " + array.size());
                recycleAdapterPackages adapter = new recycleAdapterPackages(array, this_context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        TextView title = findViewById(R.id.main_title_text);
        title.setText("חבילות");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    @Override
    public void itemClicked(Packages p) {

    }
}
