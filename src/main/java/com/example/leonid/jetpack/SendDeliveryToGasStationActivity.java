package com.example.leonid.jetpack;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Objects.DataBaseManager;
import Objects.Delivery;
import Objects.DeliveryGuys;
import Objects.GasStation;

public class SendDeliveryToGasStationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private Toolbar toolbar;

public Boolean date_done_first = false;
public Boolean date_done_second = false;
        Button button_send;

    ArrayList<String> array_names_to_disp = new ArrayList<>();
    ArrayList<DeliveryGuys> array_guys = new ArrayList<>();
    ArrayList<String> array_gas_stations = new ArrayList<>();
    ArrayList<GasStation> array_gas_objects = new ArrayList<>();
public DeliveryGuys dev_guy;
public String gas_station_name = "";
public static final String TAG = "SendDeliveryToGas";

protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_delivery_guy_to_gas_station);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retrieve_list_of_guys();


    button_send = findViewById(R.id.send_guy_to_gas_button);
        set_other_UI();




        TextView title = findViewById(R.id.main_title_text);
        title.setText("שלח שליח לתחנת דלק");
        Log.d(TAG,"after intent");

        }
public void retrieve_list_of_guys()
    {
        Query mDatabase =  FirebaseDatabase.getInstance().getReference("Delivery_Guys");
        mDatabase.addValueEventListener(new ValueEventListener()
        {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                array_guys.add(temp);
                array_names_to_disp.add(temp.getName());
                }
                set_spinner_guys();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                    System.out.println("The read failed: " + databaseError.getCode());
            }
        });
         mDatabase =  FirebaseDatabase.getInstance().getReference("GasStation");
        mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    GasStation temp = new GasStation(ds.getValue(GasStation.class));
                    array_gas_stations.add(temp.getName());
                    array_gas_objects.add(temp);

                }
                set_spinner_gas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
public void set_spinner_guys()
{
    final Spinner sp = findViewById(R.id.sp_choose_deliv_guy);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, array_names_to_disp);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(dataAdapter);
            sp.setOnItemSelectedListener(this);
}
public void set_spinner_gas()
    {
        final Spinner sp = findViewById(R.id.sp_choose_gas_station);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array_gas_stations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(this);
    }
public void set_other_UI()
{

    button_send.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
        if (date_done_first && date_done_second  )
        {
        do_database_query_and_display();
        }
        else
        {
        Toast.makeText(SendDeliveryToGasStationActivity.this, "הכנס בבקשה תאריכים",
        Toast.LENGTH_SHORT).show();
        }
        }
    });
}
public void do_database_query_and_display( )
{
    Calendar cal = Calendar.getInstance();
    Date curr_iter_date = cal.getTime();
    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    String time_now = df.format(curr_iter_date);
    Delivery gas_delivery =  new Delivery();
    gas_delivery.setIs_gas_sta(true);
    for(GasStation gs_iter : array_gas_objects)
    {
     if (gs_iter.getName().equals(gas_station_name))
     {
         gas_delivery.setGasStation(gs_iter);
         break;
     }
    }
    gas_delivery.setTimeInserted(time_now);
    gas_delivery.setDest_cord_lat(gas_delivery.getGasStation().getLat());
    gas_delivery.setDest_cord_long(gas_delivery.getGasStation().getLon());
    gas_delivery.setDelivery_guy_index_assigned(dev_guy.getIndex_string());
    gas_delivery.setDeliveryGuyName(dev_guy.getName());
    gas_delivery.setIndex(curr_iter_date.getTime());
    gas_delivery.setIndexString(String.valueOf(curr_iter_date.getTime()));
    dev_guy.addDelivery(gas_delivery);
    DeliveryGuys to_write_guy = new DeliveryGuys(dev_guy);

    DataBaseManager dbm = new DataBaseManager();
    dbm.writeDeliveryForGas(gas_delivery);
    dbm.writeDeliveryGuy(to_write_guy);
    finish();

}
@Override
public void onBackPressed() {
        super.onBackPressed();
        finish();
        }


@Override
public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
 if (adapterView.equals(findViewById(R.id.sp_choose_gas_station)))
 {
     date_done_first = true;
     gas_station_name = array_gas_stations.get(i);
     Log.d(TAG,"chose gas sta " + gas_station_name);
 }
 else
 {
     date_done_second = true;
     dev_guy = array_guys.get(i);
     Log.d(TAG,"chose guy " + array_names_to_disp.get(i));
 }


}

@Override
public void onNothingSelected(AdapterView<?> adapterView) {
}



}
