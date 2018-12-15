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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonid.jetpack.adapters.recycleAdapterDeliveryGuys;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Delivery;
import Objects.DeliveryGuys;


public class ActiveDeliveryGuysActivity extends AppCompatActivity  implements recycleAdapterDeliveryGuys.ItemClickListener{

    public static final String TAG = "ActiveDeliveryGuysAct";
    private  Toolbar toolbar;
    public Delivery clicked_delivery = null;
    private ArrayList<DeliveryGuys> array = new ArrayList<>();
    private RecyclerView recyclerView;
    ArrayList<String> selected_indeces;
    String delivery_key;
    ActiveDeliveryGuysActivity this_context = this;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_delivery_guys_activity);
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

        Bundle b = getIntent().getExtras();
        delivery_key = b.getString("Delivery_Key");
        String delivery_guy_index = b.getString("Delivery_Guy_Index");
        selected_indeces = b.getStringArrayList("selected_indeces");
        //move to another activity
        if (delivery_guy_index!= null && !delivery_guy_index.equals(""))
        {
            Bundle b2 = new Bundle();
            Intent intent = new Intent(ActiveDeliveryGuysActivity.this, PendingDeliveriesForGuyActivity.class);
            b2.putString("Delivery_Key",delivery_key);
            b2.putString("Delivery_Guy_Index",delivery_guy_index);
        //    Log.d(TAG,"onTouch send to another intent Delivery_Key: " + delivery_key +" Delivery_Guy_index: " +d.getIndex_string());
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
        }

        //query for active deliveries
        Query q =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").orderByChild("is_active").equalTo(true);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    array.add(temp);
                    Log.d(TAG,"DeliveryGuy is :  " + temp.getName());
                }
                Log.d(TAG,"Done retrieving DeliveryGuy " + array.size());
                recycleAdapterDeliveryGuys adapter = new recycleAdapterDeliveryGuys(array,FragmentDeliveryGuys.profile_array, this_context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing
                Log.d(TAG,"on back pressed");
                finish();
//                triangle icon on the main android toolbar.
//                    // if this doesn't work as desired, another possibility is to call
//
//                            stopActivityTask();  // finish() here.
//                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void itemClicked(DeliveryGuys d) {

        Bundle b = new Bundle();
        Intent intent = new Intent(ActiveDeliveryGuysActivity.this, PendingDeliveriesForGuyActivity.class);
        b.putString("Delivery_Key",delivery_key);
        b.putString("Delivery_Guy_Index",d.getIndex_string());
        b.putStringArrayList("selected_indeces",selected_indeces);

        Log.d(TAG,"onTouch send to another intent Delivery_Key: " + delivery_key +" Delivery_Guy_index: " +d.getIndex_string());
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
//                d.addDelivery(clicked_delivery);
//                clicked_delivery.setStatus("B");
        Toast.makeText(getApplicationContext(), "Touch delivery guy: " + d.getName(), Toast.LENGTH_SHORT).show();
    }


}
