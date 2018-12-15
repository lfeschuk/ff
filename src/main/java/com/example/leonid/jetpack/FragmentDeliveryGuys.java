package com.example.leonid.jetpack;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.leonid.jetpack.adapters.recycleAdapterDeliveries;
import com.example.leonid.jetpack.adapters.recycleAdapterDeliveryGuys;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Objects.DataBaseManager;
import Objects.DeliveryGuys;
import Objects.ProfileImage;

public class FragmentDeliveryGuys extends Fragment implements recycleAdapterDeliveryGuys.ItemClickListener {
    public FragmentDeliveryGuys(){}
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView;
    FragmentDeliveryGuys this_fragment = this;
    final static String TAG = "FragmentDeliveriesGuy";
    private DataBaseManager dbm = new DataBaseManager();
    private ArrayList<DeliveryGuys> array = new ArrayList<>();
    public static ArrayList<ProfileImage> profile_array = new ArrayList<>();
    recycleAdapterDeliveryGuys adapter;
    String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout wrapper = new LinearLayout(getActivity()); // for example
        View fragment_view =  inflater.inflate(R.layout.fragment_recycle_list, wrapper, true);

        recyclerView = (RecyclerView) fragment_view.findViewById(R.id.list);
        ImageView overlay = (ImageView) fragment_view.findViewById(R.id.overlay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);
//        MainActivity.fab.setVisibility(View.GONE);



        ContextWrapper cw = new ContextWrapper(getActivity());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        path = directory.getAbsolutePath();


        //no need
      //  recyclerView.addOnItemTouchListener(new DragController(recyclerView, overlay));

        mDatabase =  FirebaseDatabase.getInstance().getReference("Delivery_Guys");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    final DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    DeliveryGuys exist = get_delivery_guy(temp.getIndex_string());
                    if (exist == null) {
                        array.add(temp);
                        Log.d(TAG, "DeliveryGuy is :  " + temp.getName());
                        try {
                            Bitmap loaded = loadImageFromStorage(path, temp.getIndex_string());

                            if (loaded != null) {
                                Log.d(TAG, "retrieve image from local");
                                ProfileImage pi = new ProfileImage(loaded, temp.getIndex_string());
                                profile_array.add(pi);
                                Log.d(TAG, "here");
                            } else {
                                final DatabaseReference mDatabase_ = FirebaseDatabase.getInstance().getReference("InfoAttached").child(temp.getIndex_string()).child("profile");
                                mDatabase_.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d(TAG, "go to database for profile: " + dataSnapshot.getKey());
                                        if (dataSnapshot.exists()) {
                                            String profile = new String(dataSnapshot.getValue(String.class));
                                            byte[] decodedString = Base64.decode(profile, Base64.DEFAULT);
                                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                            saveToInternalStorage(decodedByte, temp.getIndex_string());
                                            ProfileImage pi = new ProfileImage(decodedByte, temp.getIndex_string());
                                            profile_array.add(pi);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e(TAG,"exception: " + e.getMessage());

                        }
                       // on_profileImageAdded(temp.getIndex_string());
                    }
                    else
                    {
                        exist = temp;
                    }
                }
                MainActivity.set_title_for_adapter(1,array.size());
                Log.d(TAG,"Done retrieving DeliveryGuy " + array.size());
                adapter = new recycleAdapterDeliveryGuys(array,profile_array, this_fragment);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return fragment_view;
    }

    private String saveToInternalStorage(Bitmap bitmapImage,String index_string){
        ContextWrapper cw = new ContextWrapper(getActivity());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile" + index_string + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String path,String index_string)
    {

        try {
            File f=new File(path, "profile" + index_string + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
          //  ImageView img=(ImageView)findViewById(R.id.imgPicker);
            return b;
           // img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

//    public void on_profileImageAdded(final String index_guy)
//    {
//        Log.d(TAG,"dd") ;
//        final Query mDatabase_ = FirebaseDatabase.getInstance().getReference("InfoAttached").child(index_guy);
//
//        mDatabase_.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildAdded  profile " + dataSnapshot.getValue() );
//                if (!dataSnapshot.getKey().equals("profile"))
//                {
//                    return;
//                }
//                Bitmap loaded = loadImageFromStorage(path);
//                if (loaded != null)
//                {
//                    ProfileImage pi = new ProfileImage(loaded,index_guy);
//                    adapter.set_profileImage(pi);
//                    adapter.notifyDataSetChanged();
//                    Log.d(TAG,"here");
//                }
//                {
//                    Log.d(TAG,"here2");
//
//                }
//
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if (!dataSnapshot.getKey().equals("profile"))
//                {
//                    return;
//                }
//                String profile = new String( dataSnapshot.getValue(String.class) );
//                byte[] decodedString = Base64.decode(profile, Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                ProfileImage pi = new ProfileImage(decodedByte,index_guy);
//                adapter.set_profileImage(pi);
//                adapter.notifyDataSetChanged();
//                Log.d(TAG, "onChildChanged  profile " + dataSnapshot.getValue() );
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
    DeliveryGuys get_delivery_guy(String index)
    {
        for(DeliveryGuys d : array)
        {
            if (d.getIndex_string().equals(index))
            {
                return d;
            }
        }
        return null;
    }
    @Override
    public void itemClicked(DeliveryGuys d) {
        Log.d(TAG,"onTouch");
        Toast.makeText(getActivity(), "Touch delivery guy: " + d.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ActiveDeliveryGuysActivity.class);
        Bundle b = new Bundle();
        b.putString("Delivery_Key","");
        b.putString("Delivery_Guy_Index",d.getIndex_string());
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
