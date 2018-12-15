package Objects;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public  class DataBaseManager {
    private  DatabaseReference mDatabase ;
     static final String TAG = "DataBaseManager";
   public DataBaseManager()
   {
       mDatabase  = FirebaseDatabase.getInstance().getReference();

   }
    public DatabaseReference getmDatabase() {
        return mDatabase;
    }
    public  void setStatusA(Delivery delivery)
    {

        mDatabase.child("Deliveries").child(delivery.getKey()).child("delivery_guy_index_assigned").setValue("");
        mDatabase.child("Deliveries").child(delivery.getKey()).child("status").setValue("A");

    }
  public  void writeDelivery(Delivery delivery)
    {
        Log.d(TAG,"writeDelivery deliveryName: " +delivery.getBusiness_name() + " delivery assign guy: " + delivery.getDeliveryGuyName());
        mDatabase.child("Deliveries").child(delivery.getKey()).setValue(delivery);
    }
    public  void writeDeliveryForGas(Delivery delivery)
    {
        Log.d(TAG,"writeDeliveryForGas deliveryName: " +delivery.getGasStation().getName() + " delivery assign guy: " + delivery.getDeliveryGuyName());
        mDatabase.child("Deliveries").push().setValue(delivery);
    }
    public  void writeDeliveryOld(Delivery delivery)
    {
        Log.d(TAG,"writeDeliveryOld deliveryName: " +delivery.getBusiness_name() + " delivery assign guy: " + delivery.getDeliveryGuyName());
        mDatabase.child("Deliveries_Old").child(delivery.getIndexString()).setValue(delivery);
    }
    public  void writeDeliveryGuy(DeliveryGuys deliveryGuys)
    {
        Log.d(TAG,"writeDeliveryGuy deliveryGuyName: " +deliveryGuys.getName());
        mDatabase.child("Delivery_Guys").child(deliveryGuys.getIndex_string()).setValue(deliveryGuys);
    }
    public  void writeDeliveryDestArray(DeliveryGuys deliveryGuys, ArrayList<Destination> array)
    {
        Log.d(TAG,"writeDeliveryGuy deliveryGuyName: " +deliveryGuys.getName());
        mDatabase.child("Delivery_Guys").child(deliveryGuys.getIndex_string()).child("destinations").setValue(array);
    }
    public  void writeUserAdmin(UserAdmin ua)
    {
        Log.d(TAG,"writeUserAdmin writeUserAdmin: " +ua.getUsername());
        mDatabase.child("User_Admin").child(ua.getUsername()).setValue(ua);
    }
    public  void writeGasStation(GasStation gs)
    {
        Log.d(TAG,"writeGasStation : " +gs.getName());
        mDatabase.child("GasStation").child(gs.getName()).setValue(gs);
    }
    public  void writeDeliveryGuyTime(DeliveryGuyWorkTime deliveryGuyWorkTime,String date)
    {
        Log.d(TAG,"writeDeliveryGuyTime deliveryGuyName: " +deliveryGuyWorkTime.getName());
        mDatabase.child("Delivery_Guys_Time").child(deliveryGuyWorkTime.getIndexString()).push()
                .setValue(deliveryGuyWorkTime);
    }
    public  void writeDeliveryGuyShift(DeliveryGuysShift guy_shift)
    {
        Log.d(TAG,"writeDeliveryGuyShift writeDeliveryGuyShift: " +guy_shift.getName());
        mDatabase.child("Delivery_Guys_Shifts").child(guy_shift.getIndexString())
                .setValue(guy_shift);
    }
    public  void writeRestoraunt(Restoraunt r)

    {
        Log.d(TAG,"writeRestoraunt writeRestoraunt: " +r.getName());
        mDatabase.child("Restoraunt").push()
                .setValue(r);
    }
    public  void writePackage(Packages p)

    {
        Log.d(TAG,"writePackage writePackage: " +p.getName());
        mDatabase.child("Packages").push()
                .setValue(p);
    }
    public void remove_delivery_from_dguy(String index_string_guy, final String index_string_delivery)
    {
        Log.d(TAG,"remove_delivery_from_dguy + guy: " + index_string_guy + " deliv: " + index_string_delivery);
        DatabaseReference q2 =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").child(index_string_guy);
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()) {
                   DeliveryGuys temp = new DeliveryGuys(dataSnapshot.getValue(DeliveryGuys.class));
                   Log.d(TAG, "deliveries array size " + temp.getDeliveries().size());
                   temp.removeDelivery(index_string_delivery);
                   temp.removeDestination(index_string_delivery);
                   writeDeliveryGuy(temp);
                   Log.d(TAG, "remove_delivery_from_dguy DeliveryGuy is :  " + temp.getIndex_string());
               }
               else
               {
                   Log.e(TAG,"cant find delivery guy ERROR");
               }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void writeMessage()
    {
        mDatabase.setValue("Hello, World!");
    }
    public void deleteDelivery(Delivery delivery)
    {
        String index = delivery.getIndexString();
        mDatabase.child("Deliveries").child(delivery.getKey()).removeValue();
        Log.d(TAG,"remove Delivery: " + index);
    }

}
