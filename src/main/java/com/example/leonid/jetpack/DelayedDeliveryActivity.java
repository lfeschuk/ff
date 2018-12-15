package com.example.leonid.jetpack;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Objects.DataBaseManager;
import Objects.DelayedDelivery;
import Objects.Delivery;
import Objects.DeliveryGuys;

public class DelayedDeliveryActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    public static final String TAG = "DelayedDeliveryActivity";
    ArrayList<DelayedDelivery> delayed_array = new ArrayList<>();
    protected DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delayed_delivery);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mDatabase =  FirebaseDatabase.getInstance().getReference("Delayed_Delivery");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                delayed_array.clear();
                reset_table();
                for( DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DelayedDelivery temp = new DelayedDelivery(ds.getValue(DelayedDelivery.class));
                    delayed_array.add(temp);
                }
                Log.d(TAG,"delayed:delivery " + delayed_array.size());
                set_ui();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void set_ui()
    {
//        String timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
//        String dateStamp = new SimpleDateFormat("dd:").format(new Date());
        set_title();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

        final String sunday =  df.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        //next saturday
     //   c.add(Calendar.DATE,7);
        final String saturday =  df.format(c.getTime());

        Log.d(TAG,"sunday: " + sunday + " saturday: " + saturday);
        for(DelayedDelivery d : delayed_array)
        {

            if (d.getDelayed_date().compareTo(saturday) > 0 || d.getDelayed_date().compareTo(sunday) < 0)
            {
                Log.d(TAG,"delayedDAte: " + d.getDelayed_date());
                continue;
            }
            int day_index = get_day_index(d.getDelayed_date());
            int hour_index = get_hour_index(d.getDelayed_hour());
            Log.d(TAG,"day: " + day_index +" hour: " + hour_index);
            set_delayed_info(d,hour_index,day_index);

        }
    }
    int get_day_index(String date)
    {


            Calendar c = Calendar.getInstance();
            String[] date_String = date.split("-");
            //0 for january

            c.set(Integer.valueOf(date_String[0]),Integer.valueOf(date_String[1]) -1 ,Integer.valueOf(date_String[2]));


            return c.get(Calendar.DAY_OF_WEEK);


    }
    int get_hour_index(String hour)
    {
        String[] temp = hour.split(":");
        int hour_int = Integer.valueOf(temp[0]);
        int min = Integer.valueOf(temp[1]);
        int out =  hour_int - 9 ;
        if ( min < 30 && hour_int > 10)
        {
            out -- ;
        }
        if ( out > 14)
        {
            return 14;
        }
        if (out < 1)
        {
            return 1;
        }
        //starts from 1
        return out ;
    }
    public void set_delayed_info(DelayedDelivery dd,int i,int j)
    {
        //[i][j] in matrix
        String index = "delay_" + i + j;
        Resources res = getResources();
        int id = res.getIdentifier(index, "id", this.getPackageName());
        Log.d(TAG,"index:" + index + " id: " + id);
        TextView tv = findViewById(id);
        String get_current = tv.getText().toString();
        if (!get_current.equals(""))
        {
            get_current += "\n";
        }
        tv.setText(get_current + dd.getBusiness_name() + "\n" + dd.getDelayed_hour());
    }
    public void reset_table()
    {
        for(int i=1;i<=14;i++)
        {
            for ( int j=1; j<=7;j++)
            {
                String index = "delay_" + i + j;
                Resources res = getResources();
                int id = res.getIdentifier(index, "id", this.getPackageName());
                Log.d(TAG,"index:" + index + " id: " + id);
                TextView tv = findViewById(id);
                tv.setText("");
            }
        }
    }
    public void set_title()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("dd.MM");


        TextView tv_sunday = findViewById(R.id.delayed_sunday);
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        final String sunday =  df.format(c.getTime());
        tv_sunday.setText("ראשון" + "\n" + sunday);

        TextView tv_monday = findViewById(R.id.delayed_monday);
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        final String monday =  df.format(c.getTime());
        tv_monday.setText("שני" + "\n" + monday);

        TextView tv_thuesday = findViewById(R.id.delayed_thuesday);
        c.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
        final String tuesday =  df.format(c.getTime());
        tv_thuesday.setText("שלישי" + "\n" + tuesday);

        TextView tv_wed = findViewById(R.id.delayed_wednesday);
        c.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
        final String wednesday =  df.format(c.getTime());
        tv_wed.setText("רביעי" + "\n" + wednesday);

        TextView tv_thu = findViewById(R.id.delayed_thursday);
        c.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
        final String thursday =  df.format(c.getTime());
        tv_thu.setText("חמישי" + "\n" + thursday);

        TextView tv_fri = findViewById(R.id.delayed_friday);
        c.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
        final String friday =  df.format(c.getTime());
        tv_fri.setText("שישי" + "\n" + friday);

        TextView tv_satu = findViewById(R.id.delayed_saturday);
        c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        final String saturday =  df.format(c.getTime());
        tv_satu.setText("שבת" + "\n" + saturday);
    }

}