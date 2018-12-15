package com.example.leonid.jetpack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import Objects.DataBaseManager;

public class FragmentNotificationButton extends AppCompatActivity {
    DataBaseManager dbm = new DataBaseManager();
    private  Toolbar toolbar;
    final static String TAG = "FragmentNotification";
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification_button);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = findViewById(R.id.main_title_text);
        title.setText("כיבוי/הדלקת התראות");
        ToggleButton tb = findViewById(R.id.notification_button);
        tb.setChecked(MainActivity.current_user.getEnable_notification());
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MainActivity.current_user.setEnable_notification(b);
                dbm.writeUserAdmin(MainActivity.current_user);
                Log.d(TAG,"onCheckedChanged " + b);
            }
        });
        Log.d(TAG,"after intent");

    }

}