package com.example.leonid.jetpack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonid.jetpack.Interfaces.MyDialogCloseListener;
import com.example.leonid.jetpack.UIClasses.MyDatePickerFragment;
import com.example.leonid.jetpack.adapters.recycleAdapterDeliveryGuys;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Objects.DeliveryGuyWorkTime;
import Objects.DeliveryGuys;

public class DisplayDeliveryGuyWorkingHours extends AppCompatActivity implements MyDialogCloseListener, AdapterView.OnItemSelectedListener {

    String date_first = null;
    String date_second = null;
    public Boolean edit_text_done = false;
    public Boolean date_done_first = false;
    public Boolean date_done_second = false;
    public Boolean done_retrieving = false;
    Button b_date_start;
    Button b_date_end;
    Button button_display_hours;
    double delivery_guy_salary = 0;
    ArrayList<String> array_names_to_disp = new ArrayList<>();
    ArrayList<String> array_index_String = new ArrayList<>();
    public String index_string_to_display = "";
    public static final String TAG = "DisplayDeliveryGuyWorkH";
    private  Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_delivery_guys_working_time);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retrieve_list_of_guys();

        b_date_start = findViewById(R.id.button_set_date_for_guys_hours_from);
        b_date_end = findViewById(R.id.button_set_date_for_guys_hours_to);
        button_display_hours = findViewById(R.id.button_display_hours);
        set_other_UI();




        TextView title = findViewById(R.id.main_title_text);
        title.setText("שעון נוכחות");
        Log.d(TAG,"after intent");

    }
    public void retrieve_list_of_guys()
    {
      Query  mDatabase =  FirebaseDatabase.getInstance().getReference("Delivery_Guys");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                     array_index_String.add(temp.getIndex_string());
                    array_names_to_disp.add(temp.getName());

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
        final Spinner sp = findViewById(R.id.get_delivery_guy_index_auto_compl);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array_names_to_disp);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(this);
    }
    public void set_other_UI()
    {

        b_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatePickerFragment newFragment = new MyDatePickerFragment();
                Bundle args = new Bundle();
                //doesnt matter here
                args.putBoolean("is_first", true);
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });
        b_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatePickerFragment newFragment = new MyDatePickerFragment();
                Bundle args = new Bundle();
                //doesnt matter here
                args.putBoolean("is_first", false);
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        button_display_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date_done_first && date_done_second && edit_text_done )
                {
                    do_database_query_and_display();
                }
                else
                {
                    Toast.makeText(DisplayDeliveryGuyWorkingHours.this, "הכנס בבקשה תאריכים",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void do_database_query_and_display( )
    {
     //   orderByChild("date").startAt(date_from).endAt(date_to);
        Log.d(TAG,"date first " + date_first + " date second " + date_second + " index " + index_string_to_display);
        Query mDatabase = FirebaseDatabase.getInstance().getReference("Delivery_Guys_Time").child(index_string_to_display).orderByChild("date").startAt(date_first).endAt(date_second);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String display = "";
                Date in = null;
                Date out = null;
                Boolean last_was_in = false;
                double total_hours = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuyWorkTime temp = new DeliveryGuyWorkTime(ds.getValue(DeliveryGuyWorkTime.class));
                   //array.add(temp);
                    Log.d(TAG,"start iterations  " +display  + " time from db " + temp.getTime());
                    if (temp.getIn_time())
                    {
                        if ( last_was_in)
                        {
                            Log.e(TAG,"erro happend two IN in a row");
                            return;
                        }
                        else
                        {
                            last_was_in = true;
                        }
                        display = display + temp.getName() +"כניסה: " + temp.getTime() +"\n";
                        Log.d(TAG,"in time  \n" +display );
                        try {
                            in =  new SimpleDateFormat("HH:mm").parse(temp.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        if (last_was_in)
                        {
                            last_was_in = false;
                        }
                        else
                        {
                            Log.e(TAG,"erro happend two OUT in a row");
                            return;
                        }
                        display = display + temp.getName() +"יציאה: " + temp.getTime()+"\n";
                        Log.d(TAG,"out time  \n" +display );
                        try {
                            out =  new SimpleDateFormat("HH:mm").parse(temp.getTime());
                            if ( out.before(in))
                            {
                                //error sendrr
                                Log.e(TAG,"erro happend two OUT is BEFORE previous IN in a row");
                                return;
                            }
                            long diff = out.getTime() - in.getTime();
                           double diff_hours = diff / (1000 * 60 * 60); //hours
                            display = display + "סה''כ שעות: " + diff_hours+"\n";
                            total_hours+= diff_hours;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    Log.d(TAG,"DeliveryOld is :  " + temp);
                }
                display = display + "סה''כ שכר: " + total_hours*delivery_guy_salary+ "ש''ח" +"\n";
                Log.d(TAG,"Done retrieving Working Hours  \n" +display );
                TextView display_data = findViewById(R.id.hours_text);
                display_data.setText(display);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        Query q =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").orderByChild("index_string").equalTo(index_string_to_display);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    delivery_guy_salary = temp.getSalary();
                    Log.d(TAG,"DeliveryGuy is :  " + temp.getName());
                }
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
    public void handleDialogClose(Boolean is_first, String date) {
        if(date == null)
        {
            return;
        }
        if (is_first)
        {
            date_done_first = true;
            date_first = date;
            b_date_start.setText(date);
        }
        else
        {
            date_done_second = true;
            date_second = date;
            b_date_end.setText(date);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        edit_text_done = true;
        index_string_to_display = array_index_String.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
