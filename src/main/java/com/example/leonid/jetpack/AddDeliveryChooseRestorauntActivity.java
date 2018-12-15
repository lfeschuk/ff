package com.example.leonid.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.DeliveryGuys;
import Objects.Restoraunt;

public class AddDeliveryChooseRestorauntActivity  extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener{
    public static final String TAG = "AddDeliveryChooseR";
    Button button_add_delivery;
    ArrayList<String> array_restoraunt_name = new ArrayList<>();
    ArrayList<Double> array_lon = new ArrayList<>();
    ArrayList<Double> array_lat = new ArrayList<>();
    double lat;
    double longt;
    String choosed_restoraunt;
    private  Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_delivery_choose_restoraunt_layout);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retrieve_list_of_restoraunts();
        set_other_UI();
        button_add_delivery = findViewById(R.id.button_add_delivery);
        button_add_delivery.setClickable(false);



        TextView title = findViewById(R.id.main_title_text);
        title.setText("הוסף משלוח");
        Log.d(TAG,"after intent");

    }
    public void retrieve_list_of_restoraunts()
    {
        Query mDatabase =  FirebaseDatabase.getInstance().getReference("Restoraunt");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Restoraunt temp = new Restoraunt(ds.getValue(Restoraunt.class));
                    array_restoraunt_name.add(temp.getName());
                    array_lon.add(temp.getLongt());
                    array_lat.add(temp.getLat());
                }
                set_spinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public void set_spinner()
    {
        final Spinner sp = findViewById(R.id.get_restoraunts);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array_restoraunt_name);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(this);
    }
    public void set_other_UI()
    {
        button_add_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                Intent intent = new Intent(AddDeliveryChooseRestorauntActivity.this, AddDeliveryActivity.class);
                b.putString("Restoraunt",choosed_restoraunt);
                b.putDouble("lat",lat);
                b.putDouble("long",longt);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choosed_restoraunt = array_restoraunt_name.get(i);
        longt = array_lon.get(i);
        lat = array_lat.get(i);
        button_add_delivery.setClickable(true);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
