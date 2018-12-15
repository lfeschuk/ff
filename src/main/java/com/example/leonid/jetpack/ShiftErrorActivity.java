package com.example.leonid.jetpack;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonid.jetpack.Interfaces.MyDialogCloseListener;
import com.example.leonid.jetpack.UIClasses.MyDatePickerFragment;
import com.example.leonid.jetpack.adapters.recycleAdapterDeliveries;
import com.example.leonid.jetpack.adapters.recycleAdapterDeliveryErrors;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Delivery;
import Objects.DeliveryGuys;
import Objects.GasStation;
import Objects.Restoraunt;

public class ShiftErrorActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener,MyDialogCloseListener,recycleAdapterDeliveryErrors.ItemClickListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    ShiftErrorActivity this_context = this;
    String date_from = null;
    String date_to = null;
    Button b_date_to;
    Button b_date_from;
     Spinner show_restoraunt;
    public Boolean display_by_restoraunt_late;
    private ArrayList<Delivery> array = new ArrayList<>();
    private ArrayList<Restoraunt> array_restoraunt = new ArrayList<>();
    private ArrayList<String> restoraunt_to_disp = new ArrayList<>();
    String chosen_restoraunt = "";
    public static final String TAG = "ShiftErrorActivity";
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shift_errors);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);
        b_date_to = findViewById(R.id.button_date_to);
        b_date_from = findViewById(R.id.button_date_from);

        restoraunt_to_disp.add(chosen_restoraunt);
        retrieve_list_of_restoraunts();
        set_other_UI();




        TextView title = findViewById(R.id.main_title_text);
        title.setText("תקלות\\איחורים");
        Log.d(TAG,"after intent");

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

    public void retrieve_list_of_restoraunts()
    {
        Query mDatabase =  FirebaseDatabase.getInstance().getReference("Restoraunt");
        mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Restoraunt temp = new Restoraunt(ds.getValue(Restoraunt.class));
                    array_restoraunt.add(temp);
                    restoraunt_to_disp.add(temp.getName());

                }
                set_spinner_restoraunt();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
public void set_spinner_restoraunt()
{
    show_restoraunt = findViewById(R.id.show_restoraunt);
    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, restoraunt_to_disp);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    show_restoraunt.setAdapter(dataAdapter);
    show_restoraunt.setOnItemSelectedListener(this);
}
    public void set_other_UI()
    {
        ArrayList<String> sp_1_list = new ArrayList<>();
        sp_1_list.add("חברת משלוחים");
        sp_1_list.add("מסעדה");


        final Spinner sp_choose_rest_or_business = findViewById(R.id.choose_business_or_restoraunt);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sp_1_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_choose_rest_or_business.setAdapter(dataAdapter);
        sp_choose_rest_or_business.setOnItemSelectedListener(this);


        final Button b = findViewById(R.id.button_sort);


        b_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(false);

            }
        });
        b_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(true);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date_from != null && date_to != null)
                {
                    do_database_query_and_display(display_by_restoraunt_late);


                }
                else
                {
                    Toast.makeText(ShiftErrorActivity.this, "הכנס בבקשה תאריכים",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void showDatePicker(Boolean first_date ) {
        MyDatePickerFragment newFragment = new MyDatePickerFragment();
        Bundle args = new Bundle();
        args.putBoolean("is_first", first_date);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public void do_database_query_and_display( final Boolean late_by_restoraunt)
    {
        Log.d(TAG,"do_database_query_and_display is only by date " + late_by_restoraunt + " date from: " + date_from + " dateTo: " + date_to);
    //    Log.d(TAG,"do_database_query_and_display " + index_string_to_sort + " " + business_name_to_sort);
        Query mDatabase = FirebaseDatabase.getInstance().getReference("Deliveries_Old").orderByChild("date").startAt(date_from).endAt(date_to);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                array.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Delivery temp = new Delivery(ds.getValue(Delivery.class));
                    if (late_by_restoraunt && temp.getWas_late_restoraunt())
                    {
                        if( !chosen_restoraunt.equals("") && chosen_restoraunt.equals(temp.getBusiness_name()))
                        {
                            array.add(temp);
                        }
                        else if ( chosen_restoraunt.equals(""))
                        {
                            array.add(temp);
                        }

                    }
                    else if(!late_by_restoraunt && temp.getWas_late_deliveries())
                    {
                        array.add(temp);

                    }
                    Log.d(TAG,"DeliveryOld is :  " + temp);
                }
                if (array.size() == 0)
                {
                    Toast.makeText(this_context,"לא נמצאו תוצאות עבור " + (late_by_restoraunt?"עיכובי מסעדה":"איחורים חברת משלוחים"), Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG,"Done retrieving Deliveries " + array.size());
                recycleAdapterDeliveryErrors adapter = new recycleAdapterDeliveryErrors(array, this_context);
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
    public void handleDialogClose(Boolean is_first,String date) {
        if(date != null)
        {
            if ( is_first)
            {
                date_from = date;
                b_date_from.setText(date);
            }
            else
            {
                date_to = date;
                b_date_to.setText(date);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.equals(findViewById(R.id.choose_business_or_restoraunt)))
        {
            //choose by restoraunt
            if (i == 0)
            {
                display_by_restoraunt_late = false;
            }
            //by delivery business
            else
            {
                display_by_restoraunt_late = true;
                show_restoraunt.setVisibility(View.VISIBLE);
            }
        }
        else if (adapterView.equals(findViewById(R.id.show_restoraunt)))
        {
            chosen_restoraunt = restoraunt_to_disp.get(i);
         //   Log.d(TAG,"chose guy " + array_names_to_disp.get(i));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void itemClicked(Delivery delivery) {

    }
}
