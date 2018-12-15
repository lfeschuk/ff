package com.example.leonid.jetpack;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonid.jetpack.Interfaces.MyDialogCloseListener;
import com.example.leonid.jetpack.UIClasses.MyDatePickerFragment;
import com.example.leonid.jetpack.adapters.recycleAdapterDeliveries;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Delivery;
import Objects.Destination;

public class SortAllPreviousDeliveriesActivity extends AppCompatActivity implements recycleAdapterDeliveries.ItemClickListener,MyDialogCloseListener {
    private  Toolbar toolbar;
    SortAllPreviousDeliveriesActivity this_context = this;
    String date_from = null;
    String date_to = null;
    public Boolean is_business_sort;
     Button b_date_to;
     Button b_date_from;
    public Boolean sort_only_by_date = true;
    public String business_name_to_sort = "";
    public String index_string_to_sort = "";


    private ArrayList<Delivery> array = new ArrayList<>();
    public static final String TAG = "SortDeliveriesActivity";
    private RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_previous_deliveries_activity);
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
        set_other_UI();



        TextView title = findViewById(R.id.main_title_text);
        title.setText("סינון משלוחים");
        Log.d(TAG,"after intent");





    }
    public void set_other_UI()
    {
      final  EditText e1 = findViewById(R.id.sort_by_business);
      final EditText e2 = findViewById(R.id.sort_by_index);
      final Button b = findViewById(R.id.button_sort);

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                e2.setClickable(false);
                business_name_to_sort = editable.toString();
                is_business_sort = true;
                sort_only_by_date = false;


            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                e1.setClickable(false);
                index_string_to_sort = editable.toString();
                is_business_sort = false;
                sort_only_by_date = false;

            }
        });
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
                    do_database_query_and_display(sort_only_by_date);


                }
                else
                {
                    Toast.makeText(SortAllPreviousDeliveriesActivity.this, "הכנס בבקשה תאריכים",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void showDatePicker(Boolean first_date ) {
        MyDatePickerFragment  newFragment = new MyDatePickerFragment();
        Bundle args = new Bundle();
        args.putBoolean("is_first", first_date);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public void do_database_query_and_display( final Boolean sort_only_by_date)
    {
        Log.d(TAG,"do_database_query_and_display is only by date " + sort_only_by_date + " date from: " + date_from + " dateTo: " + date_to);
        Log.d(TAG,"do_database_query_and_display " + index_string_to_sort + " " + business_name_to_sort);

       Query mDatabase = FirebaseDatabase.getInstance().getReference("Deliveries_Old").orderByChild("date").startAt(date_from).endAt(date_to);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                array.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Delivery temp = new Delivery(ds.getValue(Delivery.class));
                    if (sort_only_by_date)
                    {
                        array.add(temp);
                    }
                   else if(is_business_sort)
                    {
                        if (temp.getBusiness_name().equals(business_name_to_sort))
                        {
                            array.add(temp);
                        }
                    }
                    else if(!is_business_sort)
                    {
                        if (temp.getIndexString().equals(index_string_to_sort))
                        {
                            array.add(temp);
                        }
                    }
                    else
                    {
                        Log.d(TAG,"error in do_database_query_and_display :  " + temp);
                    }
                    Log.d(TAG,"DeliveryOld is :  " + temp);
                }
                Log.d(TAG,"Done retrieving Deliveries " + array.size());
                recycleAdapterDeliveries adapter = new recycleAdapterDeliveries(array, this_context);
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
    public void itemClicked(Delivery delivery) {

    }

    @Override
    public void itemClicked(String index, View parent) {

    }

    @Override
    public void itemLongClick(String index, View tv_marked) {

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


}
