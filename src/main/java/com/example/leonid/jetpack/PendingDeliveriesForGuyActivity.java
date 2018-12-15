package com.example.leonid.jetpack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.leonid.jetpack.adapters.recycleAdapterConst;
import com.example.leonid.jetpack.adapters.recycleAdapterDeliveryGuys;
import com.example.leonid.jetpack.adapters.recycleAdapterRoutes;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Objects.DataBaseManager;
import Objects.Delivery;
import Objects.DeliveryGuys;
import Objects.Destination;
import Objects.DirectionsJSONParser;
import Objects.DistanceDuration;


public class PendingDeliveriesForGuyActivity extends AppCompatActivity implements recycleAdapterRoutes.ItemClickListener{
    public PendingDeliveriesForGuyActivity(){}
    private Toolbar toolbar;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    PendingDeliveriesForGuyActivity this_context = this;
    ArrayList<String> selected_indeces;
    ArrayList<Delivery> to_assign_deliveries_array = new ArrayList<>();

    DeliveryGuys chosen_delivery_guy = null;
    Destination clicked_for_merge1 = null;
    View parent1 = null;
    View parent2 = null;
    private static final int REQUEST_PHONE_CALL = 1;
    DragController drag = null;
    Destination clicked_for_merge2 = null;
    Boolean is_select_deliveries_mode = false;
    ArrayList<DistanceDuration> durations_list = new ArrayList<>();
    ArrayList<Delivery> deliv_array = new ArrayList<>();
    private RecyclerView recyclerView;
    final static String TAG = "PendingDeliverForGuy";
    private DataBaseManager dbm = new DataBaseManager();
    private ArrayList<Destination> array = new ArrayList<>();
    public final double motorcycle_decrease = 0.85;
    recycleAdapterRoutes adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_deliveries_activity);
        Log.d(TAG, "on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        ImageView overlay = (ImageView) findViewById(R.id.overlay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        drag = new DragController(recyclerView, overlay, recycleAdapterConst.AdapterList.ROUTES);
        recyclerView.addOnItemTouchListener(drag);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);
        adapter = new recycleAdapterRoutes(array, this_context);
        recyclerView.setAdapter(adapter);





        Bundle b = getIntent().getExtras();
        final String delivery_key = b.getString("Delivery_Key");
        final String delivery_guy_key = b.getString("Delivery_Guy_Index");
        selected_indeces = b.getStringArrayList("selected_indeces");
        Log.d(TAG,"onCreate got from another intent Delivery_Index: " + delivery_key +" Delivery_Guy_index: " +delivery_guy_key);

        final Button assign_button = findViewById(R.id.button_assign_pending);
        final Button button_calculate_route_pending = findViewById(R.id.button_calculate_route_pending);

        Query q =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").orderByChild("index_string").equalTo(delivery_guy_key);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                array.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    TextView title = findViewById(R.id.main_title_text);
                    title.setText( temp.getName());
                    deliv_array = temp.getDeliveries();
                    chosen_delivery_guy = temp;
                    set_other_buttons();
                    Log.d(TAG,"DeliveryGuy is :  " + temp.getName());
                }
                //itetrate all over the guy deliveries and display them
                for(Destination d : chosen_delivery_guy.getDestinations())
                {
                    Log.d(TAG,"dest:" +d.getIndex_string());
                    array.add(d);
                }

                Log.d(TAG,"Done retrieving Destinations old " + array.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        if (!delivery_key.equals(""))
        {

            Query q2 =  FirebaseDatabase.getInstance().getReference("Deliveries"); //becouse of selected
            q2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        Delivery temp = new Delivery(ds.getValue(Delivery.class));
                        //check if the clicked on delivery guy is already assigned to the delivery
                        if (temp.getDelivery_guy_index_assigned().equals(chosen_delivery_guy.getIndex_string()) == false &&
                                (temp.getKey().equals(delivery_key) || selected_indeces.contains(temp.getIndexString())) )
                        {

                            array.add(new Destination(temp,false));
                            array.add(new Destination(temp,true));
                            to_assign_deliveries_array.add(temp);
                        }


                        assign_button.setClickable(true);
                        button_calculate_route_pending.setClickable(true);
                        Log.d(TAG,"Delivery is :  " + temp.getIndexString());
                    }
                    Log.d(TAG,"Done retrieving Destinations new" + array.size());
                    //   adapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


        Log.d(TAG,"onCreate");

        //assign the button
        assign_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Boolean update_delivery_guy_with_moved_delivery = false;
                String  index_update_Delivery_guy = "";
                for ( Delivery d : to_assign_deliveries_array) {
                   Delivery to_assign_delivery = d;
                        Log.d(TAG, "onclick Status: " + to_assign_delivery.getStatus());
                        if (to_assign_delivery.getStatus().equals("A")) {
                            to_assign_delivery.setStatus("B");
                        }
                        //its B
                        else {
                            Log.d(TAG, "status B detected on assigning, removing from previous delivery guy");
                            Log.d(TAG, "DeliveryGuyPrev: " + to_assign_delivery.getDelivery_guy_index_assigned() + " DeliveryInd: " + to_assign_delivery.getIndexString());
                            dbm.remove_delivery_from_dguy(to_assign_delivery.getDelivery_guy_index_assigned(), to_assign_delivery.getIndexString());
                            update_delivery_guy_with_moved_delivery = true;
                            index_update_Delivery_guy = to_assign_delivery.getDelivery_guy_index_assigned();
                        }

                        to_assign_delivery.setDelivery_guy_index_assigned(chosen_delivery_guy.getIndex_string());
                        to_assign_delivery.setDeliveryGuyName(chosen_delivery_guy.getName());
                        to_assign_delivery.setDeliveryGuyPhone(chosen_delivery_guy.getPhone());
                        to_assign_delivery.setJust_assigned_deliv(true);
                        chosen_delivery_guy.addDelivery(to_assign_delivery);
                        chosen_delivery_guy.setDestinations(array);
                        //calculate will update db
                        calculate_routes(true,to_assign_delivery,update_delivery_guy_with_moved_delivery,index_update_Delivery_guy);
                        Log.d(TAG, "writing delivery " + to_assign_delivery.getIndexString() + " guy is " + to_assign_delivery.getDeliveryGuyName());

                        Log.d(TAG, "writing guy " + chosen_delivery_guy.getName() + " deliveries: " + chosen_delivery_guy.getDeliveries().size());


                }

                    if (to_assign_deliveries_array.isEmpty())
                    {
                        calculate_routes(true,null,false,"");
                        Intent intent = new Intent(PendingDeliveriesForGuyActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

        }
     });


        button_calculate_route_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate_routes(false,null,false,"");

            }
        });




    }

    public void calculate_routes(Boolean update_db_after_assign,Delivery to_assign_delivery,Boolean update_delivery_guy_with_moved_delivery,String index_index_update_Delivery_guy)
    {
        durations_list.clear();
        Log.d(TAG,"on button_calculate_route_pending pressed");
        double iter_lat = chosen_delivery_guy.getLatetude();
        double iter_long = chosen_delivery_guy.getLongtitude();
        Log.d(TAG,"long: " + iter_long + " lat: " + iter_lat);
        Log.d(TAG,"array size:" + array.size());
        for (Destination d : array)
        {

            DistanceDuration dd = new DistanceDuration();
            LatLng source = new LatLng(iter_lat,iter_long);
            LatLng dest = new LatLng(d.getLatitude(),d.getLongitude());
            Log.d(TAG,"long2: " + d.getLongitude() + " lat2: " +d.getLatitude());
            dd.start_duration_exec(source,dest,PendingDeliveriesForGuyActivity.this,update_db_after_assign,array.size(),to_assign_delivery,update_delivery_guy_with_moved_delivery,index_index_update_Delivery_guy);
            durations_list.add(dd);
            iter_lat = d.getLatitude();
            iter_long = d.getLongitude();
            Log.d(TAG,"long: " + iter_long + " lat: " + iter_lat);
        }
    }
    public void set_other_buttons()
    {

        ImageButton call = findViewById(R.id.call_butt);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PendingDeliveriesForGuyActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PendingDeliveriesForGuyActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},
                            REQUEST_PHONE_CALL);
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + chosen_delivery_guy.getPhone() ));
                    startActivity(intent);
                }


            }
        });

        //String smsNumber = "91xxxxxxxxxx"; //without '+'


        ImageButton whatsapp = findViewById(R.id.whatsapp_butt);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_MAIN);
//                PackageManager managerclock = getPackageManager();
//                i = managerclock.getLaunchIntentForPackage("com.whatsapp");
//                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                startActivity(i);
//                try {
//                    Intent sendIntent = new Intent("android.intent.action.MAIN");
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.setType("text/plain");
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                    sendIntent.putExtra("jid", "972546357503" + "@s.whatsapp.net");
//                    sendIntent.setPackage("com.whatsapp");
//                    startActivity(sendIntent);
//                } catch(Exception e) {
//                    Toast.makeText(PendingDeliveriesForGuyActivity.this, "Error\n" + e.toString(), Toast.LENGTH_SHORT).show();
//                }
                String phone = parse_number_to_int(chosen_delivery_guy.getPhone());
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public String parse_number_to_int(String phone)
    {
        return phone.replaceFirst("0","972");
    }
    public void updateUiCallback(Boolean update_db_after_assign,Delivery to_assign_delivery
            ,Boolean update_delivery_guy_with_moved_delivery,String index_index_update_Delivery_guy)
    {

        Calendar cal = Calendar.getInstance();
        Date curr_iter_date = cal.getTime();
        Log.d(TAG,"now is " + curr_iter_date.getHours() + ":" + curr_iter_date.getMinutes());
        Date new_date = new Date();
        for (int i=0;i<array.size();i++)
        {
            Log.d(TAG,"duration time:   " +(durations_list.get(i).getDuration()));
            Log.d(TAG,"curr iter time:   " + curr_iter_date.getTime() + " we want to add " + (durations_list.get(i).getDuration()*60*1000*motorcycle_decrease));
                new_date.setTime((long)(curr_iter_date.getTime()+(durations_list.get(i).getDuration()*60*1000*motorcycle_decrease)));
                String hour;
                String minutes;
                if (new_date.getHours() <10)
                {
                    hour = "0" + new_date.getHours();
                }
                else
                {
                    hour = "" + new_date.getHours();
                }
                if (new_date.getMinutes() <10)
                {
                    minutes = "0" + new_date.getMinutes();

                }
                else
                {
                    minutes = "" + new_date.getMinutes();
                }
                String date_deliver = hour + ":" + minutes;
            Log.d(TAG,"time is  " + date_deliver);
                array.get(i).setTimeDeliver(date_deliver);
                curr_iter_date = new_date;
        }
        recyclerView.setAdapter(adapter);
        if (update_db_after_assign)
        {
            Log.d(TAG,"here");
            if (to_assign_delivery != null) {
                dbm.writeDeliveryGuy(chosen_delivery_guy);
                dbm.writeDelivery(to_assign_delivery);
            }
            else
            {
                dbm.writeDeliveryDestArray(chosen_delivery_guy,array);
            }
            setAproxTime();


            //check if need update the delivery guy which delivery was taken from him
            if ( update_delivery_guy_with_moved_delivery)
            {
                to_assign_delivery = null;
                update_delivery_guy_with_moved_delivery = false;
                get_delivery_guy_and_update_his_duration(index_index_update_Delivery_guy);
            }
//            to_assign_delivery.setTime_aprox_deliver();
//            chosen_delivery_guy.addDelivery(to_assign_delivery);
            else {


                Intent intent = new Intent(PendingDeliveriesForGuyActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }

    }

    public void get_delivery_guy_and_update_his_duration(String index_update_Delivery_guy)
    {
        DatabaseReference q =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").child(index_update_Delivery_guy);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if (dataSnapshot.exists())
               {

                    DeliveryGuys temp = new DeliveryGuys(dataSnapshot.getValue(DeliveryGuys.class));
                    deliv_array = temp.getDeliveries();
                    chosen_delivery_guy = temp;
                    Log.d(TAG," get_delivery_guy_and_update_his_duration DeliveryGuy is :  " + temp.getName());
                }
                //itetrate all over the guy deliveries and display them
                array.clear();
                for(Destination d : chosen_delivery_guy.getDestinations())
                {
                    array.add(d);
                }
                calculate_routes(true,null,false,"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void setAproxTime()
    {
        for (Destination d : array)
        {
            Delivery to_update = getDelivery(d);
            if (d.getTo_costumer())
            {
                mDatabase.child("Deliveries").child(to_update.getKey()).child("time_aprox_deliver").setValue(d.getTimeDeliver());
            }
            else
            {
                mDatabase.child("Deliveries").child(to_update.getKey()).child("time_aprox_deliver_to_rest").setValue(d.getTimeDeliver());
            }
        }
        mDatabase.child("Delivery_Guys").child(chosen_delivery_guy.getIndex_string()).child("timeBeFree").setValue(array.get(array.size() - 1).getTimeDeliver());
        Log.d(TAG,"write to db");
    }

    public Delivery getDelivery(Destination des)
    {
        for (Delivery d : deliv_array)
        {
            if (d.getIndexString().equals(des.getIndex_string()))
            {
                return d;
            }
        }
        return null;
    }

    @Override
    public void itemClicked(Destination d,View parent) {
        Intent intent = new Intent(PendingDeliveriesForGuyActivity.this, DeliveryDataActivity.class);
        Bundle b = new Bundle();
        Delivery out = getDelivery(d);
        b.putString("Delivery_Index",out.getKey());
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

    }
    private static Boolean check_merge_condition(Destination first,Destination second)
    {
        Boolean cond = first.getBusiness_name().equals(second.getBusiness_name())  &&
                first.getAdressFrom().equals(second.getAdressFrom()) &&
                first.getTo_costumer().equals(second.getTo_costumer() &&
                first.getTo_costumer() == false);
        return cond;
    }
    @Override
    public void onItemMoved(Destination d, int index) {
        if (array.size() <= 1)
        {
            return;
        }
        Log.d(TAG,"onItemMoved index:" + index);
        Destination first = null;
        Destination second = null;
        if (index > 0  && check_merge_condition(array.get(index),array.get(index + 1)))
        {
            first = array.get(index);
            second = array.get(index + 1);

        }
        else if( index == array.size() -1 && check_merge_condition(array.get(index),array.get(index - 1)) )
        {
            first = array.get(index - 1);
            second = array.get(index);
        }
        else if (check_merge_condition(array.get(index),array.get(index - 1)))
        {
            first = array.get(index - 1);
            second = array.get(index);
        }
         else if (check_merge_condition(array.get(index),array.get(index + 1)))
        {
            first = array.get(index);
            second = array.get(index + 1);
        }
        else
        {
            return;
        }

        for(String s : second.getMerged_indeces())
        {
            first.addMerged(s);
        }
        first.addMerged(second.getDelivery_index());
        first.setIs_merged(true);
        array.remove(second);
        adapter.notifyDataSetChanged();

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



}
