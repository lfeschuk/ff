package com.example.leonid.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Objects.Delivery;

public class AddDeliveryActivity extends DeliveryDataActivity {
    public static final String TAG = "AddDeliveryActivity";
    String restoraunt;
    double longt;
    double lat;
    Delivery new_delivery = new Delivery();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_data_activity);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle b = getIntent().getExtras();
        restoraunt = b.getString("Restoraunt");
        longt = b.getDouble("long");
        lat = b.getDouble("lat");
        new_delivery.setSource_cord_lat(lat);
        new_delivery.setSource_cord_long(longt);
        new_delivery.setBusiness_name(restoraunt);
        Log.d(TAG,"after intent");
        set_delivery_table();
        set_buttons();

//TODO need to add index string new from db


        TextView title = findViewById(R.id.main_title_text);
        title.setText("הוסף משלוח ל -" + restoraunt);
//        mDatabase =  FirebaseDatabase.getInstance().getReference("Deliveries");
//        mDatabase.child(delivery_key).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Delivery temp = new Delivery(dataSnapshot.getValue(Delivery.class));
//                Log.d(TAG,"the delivery is " + temp.getIndexString() + " " + temp.getAdressFrom() + temp);
//                clicked_delivery = temp;
//                TextView title = findViewById(R.id.main_title_text);
//                title.setText("#" + clicked_delivery.getIndexString());
//                set_delivery_table(temp);
//                set_buttons(temp);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }
    @Override
    public void  set_buttons()
    {
        Button end_delivery_change_button = findViewById(R.id.end_delivery_change_button);
        end_delivery_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbm.writeDelivery(new_delivery);
                finish();
            }
        });

        Button change_status = findViewById(R.id.button_change_status);
       change_status.setVisibility(View.GONE);

        Button assign_button = findViewById(R.id.button_assign);
        assign_button.setVisibility(View.GONE);

        Button change_location_button = findViewById(R.id.change_location_button);
        change_location_button.setVisibility(View.GONE);
        Button button_call = findViewById(R.id.button_call);
        button_call.setVisibility(View.GONE);
    }
    @Override
    public void set_delivery_table()
    {
      //  Button pick_hour = findViewById(R.id.pick_hour_button); //do something later

        EditText num_of_pack = findViewById(R.id.num_of_pack_edit_text);

        num_of_pack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setNum_of_packets(charSequence.toString());
                //num_of_pack.setText(d.getNum_of_packets());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText comment = findViewById(R.id.comment_edit_text);

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setComment(charSequence.toString());
                // comment.setText(d.getComment());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        EditText phone_costumer_edit_text = findViewById(R.id.phone_costumer_edit_text);

        phone_costumer_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setCostumer_phone(charSequence.toString());
                // phone_costumer_edit_text.setText(d.getCostumer_phone());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText costumer_name_edit = findViewById(R.id.costumer_name_edit);

        costumer_name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setCostumerName(charSequence.toString());
             //   costumer_name_edit.setText(d.getCostumerName());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText costumer_city_edit = findViewById(R.id.costumer_city_edit);

        costumer_city_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setCity(charSequence.toString());
                //costumer_city_edit.setText(d.getCity());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText costumer_street_edit = findViewById(R.id.costumer_street_edit);

        costumer_street_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setStreet(charSequence.toString());
                //costumer_street_edit.setText(d.getStreet());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText costumer_floor_edit = findViewById(R.id.costumer_floor_edit);

        costumer_floor_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setFloor(charSequence.toString());
                // costumer_floor_edit.setText(d.getFloor());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText costumer_apartment_edit = findViewById(R.id.costumer_apartment_edit);

        costumer_apartment_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setApartment(charSequence.toString());
                //costumer_apartment_edit.setText(d.getApartment());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText costumer_building_edit = findViewById(R.id.costumer_building_edit);

        costumer_building_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setBuilding(charSequence.toString());
                //costumer_building_edit.setText(d.getBuilding());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText costumer_entrance_edit = findViewById(R.id.costumer_entrance_edit);

        costumer_entrance_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new_delivery.setEntrance(charSequence.toString());
                //  costumer_entrance_edit.setText(d.getEntrance());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
