package com.example.leonid.jetpack;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonid.jetpack.adapters.recycleAdapterDeliveries;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.here.android.mpa.common.GeoCoordinate;
//import com.here.android.mpa.routing.CoreRouter;
//import com.here.android.mpa.search.ErrorCode;
//import com.here.android.mpa.search.GeocodeRequest;
//import com.here.android.mpa.search.ResultListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import Objects.DataBaseManager;
import Objects.Delivery;
import Objects.DeliveryGuys;
import Objects.DeliveryGuysShift;
import Objects.Destination;
import Objects.GasStation;
import Objects.Packages;
import Objects.Restoraunt;
import Objects.UserAdmin;

import static Objects.DeliveryGuysShift.KindShift.DOUBLE;
import static Objects.DeliveryGuysShift.KindShift.EVENING;
import static Objects.DeliveryGuysShift.KindShift.MORNING;
import static Objects.DeliveryGuysShift.KindShift.NONE;

public class MainActivity extends AppCompatActivity implements recycleAdapterDeliveries.ItemClickListener{
DrawerLayout dLayout;
    private Toolbar toolbar;
    public static UserAdmin current_user;
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    public static  ViewPagerAdapter adapter;
    Fragment deliveries_fragment;
    Fragment delivery_guys_fragment;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;




public static final String TAG = "MainActivity";
DataBaseManager dbm = new DataBaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();
        get_current_user();

//        UserAdmin um = new UserAdmin("leonid","leonidf1991@gmail.com");
//        um.setEnable_notification(true);
//        dbm.writeUserAdmin(um);

//        Packages p1 = new Packages("לאוניד","0543639812","מודיעין מכבים רעות","עמק החולה","51","6","-","-","2018-09-30");
//            Packages p2 = new Packages("ולריה בלוחבוסטוב","0543621412","מודיעין מכבים רעות","עמק זבולון","51","7","-","-","2018-09-30");
//            Packages p3 = new Packages("יוסי קורדובה","0513456422","מכבים","דם המכבים","60","1","-","-","2018-09-30");
//    dbm.writePackage(p1);
//        dbm.writePackage(p2);
//        dbm.writePackage(p3);

            // String name, String index, double longt, double lat,int time_to_costumer,int time_to_prepare
//        Restoraunt r = new Restoraunt("אושי אושי","",35.017496,31.886661,60,15);
//        Restoraunt r2 = new Restoraunt("דומינוס קייזר","",34.997281,31.908044,60,15);
//        Restoraunt r3 = new Restoraunt("פיצה פרגו","",35.005647,31.885291,60,15);
//        dbm.writeRestoraunt(r);
//        dbm.writeRestoraunt(r2);
//        dbm.writeRestoraunt(r3);
//        DeliveryGuysShift dgs = new DeliveryGuysShift(1,"1","לאוניד","2018-09-23",NONE,MORNING,EVENING,DOUBLE,NONE,MORNING,DOUBLE);
//        dbm.writeDeliveryGuyShift(dgs);
//        GasStation gs = new GasStation("דור אלון יציאה ממודיעין",35.013791,31.909928);
//        GasStation gs2 = new GasStation("צומת שילת סונול יציאה ממודיעין",35.007356,31.921985);
//        GasStation gs3 = new GasStation("מיקה ישפרו",34.968058,31.890392);
//        dbm.writeGasStation(gs);
//        dbm.writeGasStation(gs2);
//        dbm.writeGasStation(gs3);

//        Integer index, String adressTo, String adressFrom, String timeInserted, String status, String comment,
//                String num_of_packets,String costumer_phone,String costumerName,
//                String city,String floor,String building, String entrance,String street,String apartment,
//                String business_name,String delivery_guy_index_assigned,
//        double source_cord_lat,double source_cord_long,double dest_cord_lat,double dest_cord_long,
//        String deliveryGuyName


        //getAddressFromLocation(31.919018100000002,34.984552799999996);
//        Delivery delivery = new Delivery(Long.valueOf(1),"עמק האלה","מרכז שמשוני","23:00","D",
//                "אל תאחרו","3","0525410912","לאוניד כהן","מודיעין","1",
//                "54","1","עמק האלה","6","ביג אפל פיצה","",
//                31.911910,35.001865,31.913116,35.007167,"","2018-09-22");
//       dbm.writeDeliveryOld(delivery);
//         delivery = new Delivery(2,"עמק החולה","דומינוס שמשוני","23:10","A",
//                "אל תאחרו בבקשה","1","0525410944","יוסי לוי","מודיעין","1",
//                "54","1","עמק החולה","3","דומינוס פיצה שמשוני","",
//                 31.893120,34.960698,31.895772,35.016540,"");
//        dbm.writeDelivery(delivery);
       // dbm.writeMessage();
//          public DeliveryGuys(String name, String timeBeFree, ArrayList<Delivery> deliveries, double latetude, double longtitude, String picture,String index_string,Boolean is_active,
//        long index,double salary,String phone,Boolean sent_start_shift_report) {
//        String name, String timeBeFree, ArrayList<Delivery> deliveries, double latetude, double longtitude, String picture,String index_string,Boolean is_active,
//        long index,double salary,String phone,Boolean sent_start_shift_report,ArrayList< Destination > destinations
//        DeliveryGuys deliveryGuy = new DeliveryGuys("לאוניד","12:00",new ArrayList<Delivery>(),  31.893120,34.960698,"please be hurry","3",true,3,30,"0525410912",true,new ArrayList<Destination>());
//         dbm.writeDeliveryGuy(deliveryGuy);
//        DeliveryGuys deliveryGuy2 = new DeliveryGuys("אביאל קוגן","12:00",new ArrayList<Delivery>(),  31.893120,34.960698,"please be hurry","4",true,4,30,"0525641938",true,new ArrayList<Destination>());
//        dbm.writeDeliveryGuy(deliveryGuy2);
//        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + 31.8903 + "," +  35.0104 + "&mode=w");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);

        //here is previous code
       Log.d(TAG,"send to Db");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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


        private void requestPermissions() {

        final List<String> requiredSDKPermissions = new ArrayList<String>();
        requiredSDKPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        requiredSDKPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requiredSDKPermissions.add(Manifest.permission.INTERNET);
        requiredSDKPermissions.add(Manifest.permission.ACCESS_WIFI_STATE);
        requiredSDKPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);

        ActivityCompat.requestPermissions(this,
                requiredSDKPermissions.toArray(new String[requiredSDKPermissions.size()]),
                REQUEST_CODE_ASK_PERMISSIONS);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                for (int index = 0; index < permissions.length; index++) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {

                        /**
                         * If the user turned down the permission request in the past and chose the
                         * Don't ask again option in the permission request system dialog.
                         */
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                                permissions[index])) {
                            Toast.makeText(this,
                                    "Required permission " + permissions[index]
                                            + " not granted. Please go to settings and turn on for sample app",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this,
                                    "Required permission " + permissions[index] + " not granted",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }

                /**
                 * All permission requests are being handled.Create map fragment view.Please note
                 * the HERE SDK requires all permissions defined above to operate properly.
                 */
              //  m_mapFragmentView = new MapFragmentView(this);
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this);

      //  Geocoder gc = new Geocoder(context);
        if(geocoder.isPresent()){
            List<Address> list = null;
            try {
               // list = geocoder.getFromLocationName("עמק האלה 103 מודיעין",1);
                list = geocoder.getFromLocation(latitude,longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = list.get(0);
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            StringBuffer str = new StringBuffer();
            str.append("Name: " + address.getLocality() + "\n");
            str.append("Sub-Admin Ares: " + address.getSubAdminArea() + "\n");
            str.append("Admin Area: " + address.getAdminArea() + "\n");
            str.append("Country: " + address.getCountryName() + "\n");
            str.append("Country Code: " + address.getCountryCode() + "\n");
            String strAddress = str.toString();
            address.getAddressLine(0);
     //      Log.d(TAG,"lat: " + lat + " long: " + lng);
            Log.d(TAG,"addr: " +  address.getAddressLine(0));




        }

    }


    public void get_current_user()
    {
       Query query =  FirebaseDatabase.getInstance().getReference("User_Admin").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    UserAdmin temp = new UserAdmin(ds.getValue(UserAdmin.class));
                    current_user = temp;
                            Log.d(TAG,"UserAdmin is :  " + temp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        deliveries_fragment = new FragmentDeliveries();
        delivery_guys_fragment = new FragmentDeliveryGuys();

        adapter.addFragment(deliveries_fragment, "משלוחים");
        adapter.addFragment(delivery_guys_fragment, "שליחים");
        viewPager.setAdapter(adapter);
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



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment frag = null; // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
// check selected menu item's id and replace a Fragment Accordingly
                //refresh
                if (itemId == R.id.nav_1) {
                    frag = null;
                    //deliveries oin road
                } else if (itemId == R.id.nav_2) {
                    frag = null;
                    Log.d(TAG,"maps checked");
                    //delayed
                } else if (itemId == R.id.nav_3) {
                    frag = null;
                    //sort deliveries
                } else if (itemId == R.id.nav_4) {
                    frag = null;
                    //presence time
                } else if (itemId == R.id.nav_15) {
                    frag = null;
                    //errors in shift
                } else if (itemId == R.id.nav_5) {
                    frag = null;
                    //notification button
                } else if (itemId == R.id.nav_6) {
                    frag = null;
                    //gas station
                } else if (itemId == R.id.nav_7) {
                    frag = null;
                    //packages
                } else if (itemId == R.id.nav_8) {
                    frag = null;
                } else if (itemId == R.id.nav_9) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_10) {
                    frag = new ThirdFragment();
                    //shifts
                } else if (itemId == R.id.nav_11) {
                    frag = null;
                } else if (itemId == R.id.nav_12) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_13) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_14) {
                    frag = new ThirdFragment();
                }
// display a toast message with menu item's title
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, frag); // replace a Fragment with Frame Layout
                  //  transaction.addToBackStack(frag.getClass().getSimpleName()); //maybe null instead
                  //  transaction.addToBackStack(deliveries_fragment.getClass().getSimpleName());
                    transaction.remove(deliveries_fragment);
                //    transaction.addToBackStack(BACK_STACK_ROOT_TAG); //maybe null instead
                    transaction.commit(); // commit the changes
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }
                else if(itemId == R.id.nav_1 )
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                else if(itemId == R.id.nav_2 )
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, MapsActivityNew.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                else if (itemId == R.id.nav_3)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, DelayedDeliveryActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                else if (itemId == R.id.nav_4)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, SortAllPreviousDeliveriesActivity.class);
                    startActivity(intent);
                }
                else if (itemId == R.id.nav_15)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, DisplayDeliveryGuyWorkingHours.class);
                    startActivity(intent);
                }
                else if (itemId == R.id.nav_6)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, FragmentNotificationButton.class);
                    startActivity(intent);

                }
                else if (itemId == R.id.nav_7)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, SendDeliveryToGasStationActivity.class);
                    startActivity(intent);

                }
                else if (itemId == R.id.nav_11)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, DeliveryGuysShiftActivity.class);
                    startActivity(intent);

                }
                else if (itemId == R.id.nav_5)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, ShiftErrorActivity.class);
                    startActivity(intent);
                }
                else if (itemId == R.id.nav_8)
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, PackagesActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });
    }
    public static void set_title_for_adapter(int pos,int num)
    {
        Log.d(TAG,"set_title_for_adapter  pos: " + pos + " num: " + num);
        String set_text = " ";
        if (pos == 0)
        {
            set_text = "משלוחים" + "(" + num + ")";
        }
        else if (pos == 1)
        {
            set_text = "שליחים" + "(" + num + ")";
        }
        else
        {
           Log.e(TAG,"error in set_title_for_adapter pos nor 0 or 1");
        }
       tabLayout.getTabAt(pos).setText(set_text);
    }
}
